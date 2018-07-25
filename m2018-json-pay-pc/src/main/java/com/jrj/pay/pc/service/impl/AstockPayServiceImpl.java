package com.jrj.pay.pc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jrj.pay.pc.bean.AStockHeadline;
import com.jrj.pay.pc.bean.AssessmentInfo;
import com.jrj.pay.pc.bean.Ext;
import com.jrj.pay.pc.bean.OrderList;
import com.jrj.pay.pc.bean.RequestMessage;
import com.jrj.pay.pc.bean.root;
import com.jrj.pay.pc.constant.PayConstant;
import com.jrj.pay.pc.dao.AstockHeadlineDao;
import com.jrj.pay.pc.service.AstockPayService;
import com.jrj.pay.pc.service.HttpAPIService;
import com.jrj.pay.pc.util.DateUtil;
import com.jrj.pay.pc.util.MyJsonConverter;
import com.jrj.pay.pc.util.PageUtils;
import com.jrj.pay.pc.util.RedisCache;
import com.jrj.pay.pc.util.Xml2ObjUtil;
import com.jrj.pay.pc.vo.OrderListMessVo;
import com.jrj.pay.pc.vo.PageMessageAndData;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @description pc端A股头条-付费版 -serviceipml
 * @author bin.wang
 * @date 2018.06.20
 *
 */
@Data
@Slf4j
@Service
public class AstockPayServiceImpl implements AstockPayService {

	@Autowired
	private RedisCache redisCache;
	@Autowired
	private RestTemplate restTemplate;
	@Resource
	private HttpAPIService httpAPIService;
	@Autowired
	private AstockHeadlineDao astockHeadlineDao;

	private static final String key = "jrj:pay:com.jrj.pay.service.impl.AstockPayServiceImpl";

	@SuppressWarnings("unchecked")
	@Override
	public PageMessageAndData<AStockHeadline> getHeadlinesService(Integer curPage, String expireDate, String days,
			String validDate) {
		String keys = key + "getHeadlinesService" + curPage + expireDate;
		// redisCache.deleteObj(keys);// 上线时关闭
		PageMessageAndData<AStockHeadline> messageAndData = null;
		if (redisCache.hasKey(keys)) {
			log.info("getHeadlinesService redisCache.hasKey(keys)");
			messageAndData = redisCache.getObj(keys, PageMessageAndData.class);
		} else {
			PageHelper.startPage(curPage, PayConstant.PAGE_SIZE);
			Page<AStockHeadline> headlines = astockHeadlineDao.getHeadlinesDao(expireDate + " 23:59:59");
			messageAndData = PageUtils.setPageMessageAndData(expireDate, validDate, days, headlines);
			redisCache.setObj(keys, messageAndData, 10 * 60, PageMessageAndData.class);
		}
		log.info("curPage:" + curPage + " expireDate:" + expireDate + " messageAndData:" + messageAndData);
		return messageAndData;
	}

	@Override
	public String getHeadlineContentService(String iiid) {
		log.info("getHeadlineContentService");
		String xml = null;
		root roots = null;
		String keys = key + "getHeadlineContentService" + iiid;
		// redisCache.deleteObj(keys);// 上线时关闭
		if (redisCache.hasKey(keys)) {
			log.info("getHeadlineContentService redisCache.hasKey(keys)");
			roots = redisCache.getObj(keys, root.class);
		} else {
			try {
				// 使用httpClient请求
				xml = httpAPIService.doGet(PayConstant.CMS_DATA_URL + "?iiid=" + iiid);
				roots = (root) Xml2ObjUtil.convertXmlStrToObject(root.class, xml);
				if (roots != null) {
					redisCache.setObj(keys, roots, 10 * 60, root.class);
				}
				log.info("httpAPIService roots= " + roots);
			} catch (Exception e) {
				log.info("getHeadlineContentService exception= " + e);
			}
		}
		log.debug("getHeadlineContentService-----" + MyJsonConverter.objectToString(roots));
		return MyJsonConverter.objectToString(roots);
	}

	@Override
	public String hasExperiencedService(String uid) {
		String json = null;
		RequestMessage message = new RequestMessage();
		message.setIsLogined("0"); // 已登录
		try {
			json = restTemplate.getForObject(PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid={uid}",
					String.class, uid);
		} catch (Exception e) {
			log.info("订单接口调用失败！" + e);
			message.setMsg("订单接口调用失败！" + e);
			message.setRetCode("1"); // 调用失败
			return MyJsonConverter.objectToString(message);
		}
		OrderListMessVo orderListMesses = MyJsonConverter.stringToObject(json, OrderListMessVo.class);
		List<OrderList> orderList = orderListMesses.getOrderList();
		message.setRetCode("0"); // 调用成功
		message.setIsFreeUsered("0"); // 未体验
		message.setMsg("未体验过！");
		for (OrderList order : orderList) {
			if (order.getTotalAmount() == 0) {
				message.setIsFreeUsered("1"); // 已体验
				message.setMsg("已经体验过，不可以再次体验！");
				break;
			}
		}
		return MyJsonConverter.objectToString(message);
	}

	@Override
	public String freeExperienceService(String uid) {
		RequestMessage message = new RequestMessage();
		if (StringUtils.isEmpty(uid)) {
			message.setMsg("请输入有效的passportId！");
			message.setRetCode("1");
			return MyJsonConverter.objectToString(message);
		}

		// 判断是否已经体验过
		String json = null;
		try {
			json = restTemplate.getForObject(PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid={uid}",
					String.class, uid);
		} catch (Exception e) {
			log.info("订单接口调用失败！" + e);
			message.setMsg("订单接口调用失败！" + e);
			message.setRetCode("1"); // 调用失败
			return MyJsonConverter.objectToString(message);
		}
		OrderListMessVo orderListMesses = MyJsonConverter.stringToObject(json, OrderListMessVo.class);
		List<OrderList> orderList = orderListMesses.getOrderList();
		message.setRetCode("0"); // 调用成功
		for (OrderList order : orderList) {
			if (order.getTotalAmount() == 0) {
				message.setIsFreeUsered("1"); // 已体验
				message.setMsg("已经体验过，不可以再次体验！");
				log.info(uid + " 用户已经体验过，不可以再次体验！");
				break;
			}
		}

		// 未体验过 进行体验
		if (!"1".equals(message.getIsFreeUsered())) {
			log.info(uid + " 用户未体验过，进行体验！");
			String addZeroJson = null;
			try {
				Long clientOrderId = System.currentTimeMillis();
				log.info("url= " + PayConstant.ADD_ZERO_URL
						+ "bizCode=5&productId=8&productSubId=100080001&type=6&source=wap&reason=免费体验赠送&buyerId=" + uid
						+ "&clientOrderId=" + clientOrderId);
				addZeroJson = restTemplate.getForObject(PayConstant.ADD_ZERO_URL
						+ "?bizCode=5&productId=8&productSubId=100080001&type=6&source=wap&reason=免费体验赠送&buyerId=" + uid
						+ "&clientOrderId=" + clientOrderId, String.class);
			} catch (Exception e) {
				log.info("Exception 调用新增订单接口调用失败！" + e);
				message.setMsg("Exception 调用新增订单接口失败！" + e);
				message.setRetCode("1");
				return MyJsonConverter.objectToString(message);
			}
			log.info("addZeroJson= " + addZeroJson);
			JSONObject obj = JSON.parseObject(addZeroJson);
			message.setMsg(obj.getString("msg"));
			message.setRetCode(obj.getString("retCode"));
		}
		return MyJsonConverter.objectToString(message);
	}

	@Override
	public String headlineAbstractService(String iiid) {
		String keys = key + "headlineAbstractService" + iiid;
		// redisCache.deleteObj(keys);// 上线时关闭
		AStockHeadline detail = null;
		if (redisCache.hasKey(keys)) {
			log.info("headlineAbstractService redisCache.hasKey(keys)");
			detail = redisCache.getObj(keys, AStockHeadline.class);
		} else {
			detail = astockHeadlineDao.getHeadlineAbstractDao(iiid);
			if (detail != null) {
				redisCache.setObj(keys, detail, 5 * 60, AStockHeadline.class);
			}
		}
		log.info("detail= " + detail);
		return detail != null ? MyJsonConverter.objectToString(detail) : "";
	}

	@Override
	public String userPermissionService(String passportId) {
		String json = null;
		RequestMessage resultMes = new RequestMessage();
		// 调用接口获取用户权限信息
		try {
			json = restTemplate.getForObject(
					PayConstant.VERIFY_INTERFACE_URL
							+ "/privilege/getUserPrivilegePeriod?bizCode=5&productId=8&productSubId=100080001&passportId={passportId}",
					String.class, passportId);
		} catch (Exception e) {
			log.info("userPermissionService getUserPrivilegePeriod interface request is failed" + e);
			resultMes.setRetCode("1");
			resultMes.setMsg("调用权限接口失败，请检查您的网络！" + e);
			return MyJsonConverter.objectToString(resultMes);
		}

		JSONObject object = JSON.parseObject(json);
		if ("1".equals(object.getString("code"))) { // 请求失败
			log.info("userPermissionService getUserPrivilegePeriod interface request is failed");
			resultMes.setRetCode("1"); // 请求失败
			resultMes.setMsg("请输入用效的passportId！");
			return MyJsonConverter.objectToString(resultMes);
		}

		// 获取用户权限信息
		resultMes = getUserPerssion(object, passportId, resultMes);
		return MyJsonConverter.objectToString(resultMes);
	}

	@Override
	public String orderResultService(String sellerOrderId) {
		String json = null;
		RequestMessage resultMes = new RequestMessage();
		// 调用接口获取用户权限信息
		try {
			String timeStamp = DateUtil.getDateString(); // 获取日期yyyyMMddHHmmss
			json = restTemplate.getForObject(
					PayConstant.ORDER_RESULT_URL
							+ "?sign=astockpay&signType=2&version=1.0&bizCode=5&timeStamp={timeStamp}&sellerOrderId={sellerOrderId}",
					String.class, timeStamp, sellerOrderId);
		} catch (Exception e) {
			log.info("orderResultService 接口调用失败！" + e);
			resultMes.setRetCode("1");
			resultMes.setMsg("调用权限接口失败，请检查您的网络！" + e);
			return MyJsonConverter.objectToString(resultMes);
		}
		JSONObject object = JSON.parseObject(json);
		if ("1".equals(object.getString("retCode"))) { // 查询的信息不存在
			log.info("orderResultService 查询结果为空！");
			resultMes.setRetCode("1");
			resultMes.setMsg("查询结果为空！");
			return MyJsonConverter.objectToString(resultMes);
		}

		resultMes = getOrderResult(object, resultMes);
		return MyJsonConverter.objectToString(resultMes);
	}

	/**
	 * 获取订单通知的详情
	 * 
	 * @param object
	 * @param resultMes
	 */
	private RequestMessage getOrderResult(JSONObject object, RequestMessage resultMes) {
		JSONObject jsonObject = JSON.parseObject(object.get("data").toString());
		// 获取订单状态 0:待支付 1:支付成功 2:退款中 3:退款成功 4:关闭 5:分单支付中
		String status = jsonObject.getString("status");
		log.info("getOrderResult status= " + status);
		if ("1".equals(status)) { // 支付成功
			resultMes.setRetCode("0"); // 请求成功
			resultMes.setMsg("支付成功！");
			resultMes.setStatus("0");
			; // 支付成功
		} else { // 其他情况
			resultMes.setRetCode("0"); // 请求成功
			resultMes.setMsg("未支付成功！");
			resultMes.setStatus("1");
			; // 其他未支付成功
		}
		return resultMes;
	}

	/**
	 * 获取用的权限信息
	 * 
	 * @param object
	 * @param resultMes
	 * @return
	 */
	private RequestMessage getUserPerssion(JSONObject object, String passportId, RequestMessage resultMes) {
		JSONObject jsonObject = JSON.parseObject(object.get("data").toString());
		String valid = jsonObject.getString("valid"); // 权限标记
		if ("0".equals(valid)) { // 账号有权限
			log.info("getUserPerssion account is valid");
			resultMes.setRetCode("0"); // 请求成功
			resultMes.setHasPermission("0"); // 账号有权限
			resultMes.setMsg("账号有权限");
			resultMes.setIsFreeUsered(""); // 用户体验设置为空
			Integer days = jsonObject.getInteger("days"); // 有效天数 
			resultMes.setDays(days);
		} else { // 账号无权限
			log.info("getUserPerssion account is invalid");
			resultMes.setRetCode("0"); // 请求成功
			resultMes.setHasPermission("1"); // 账号无权限
			String json = null;
			try {
				json = restTemplate.getForObject(PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid={passportId}",
						String.class, passportId);
			} catch (Exception e) {
				log.info("订单接口调用失败！" + e);
				resultMes.setMsg("订单接口调用失败！" + e);
				resultMes.setRetCode("1"); //
				return resultMes;
			}
			OrderListMessVo orderListMesses = MyJsonConverter.stringToObject(json, OrderListMessVo.class);
			List<OrderList> orderList = orderListMesses.getOrderList();
			log.info("orderList=" + orderList);
			log.info("orderList.size= " + orderList.size());
			if (orderList != null && orderList.size() > 0) {
				resultMes.setMsg("账号无权限，且该账号已经免费体验过！");
				resultMes.setIsFreeUsered("1"); // 用户已体验过
			} else {
				resultMes.setMsg("账号无权限，但账号未免费体验过！");
				resultMes.setIsFreeUsered("0"); // 用户未体验过
			}
		}
		return resultMes;
	}

	@Override
	public String getCurrentDataService() {
		String date = DateUtil.getCurrentDayPreSevenDate();
		String keys = key + "getCurrentDataService" + date;
		log.info("getCurrentDataService date= " + date);
		AStockHeadline aStockHeadline = null;
		// redisCache.deleteObj(keys);// 上线时关闭
		if (redisCache.hasKey(keys)) {
			log.info("getCurrentDataService redisCache.hasKey(keys)");
			aStockHeadline = redisCache.getObj(keys, AStockHeadline.class);
		} else {
			aStockHeadline = astockHeadlineDao.getCurrentDataDao(date);
			if (aStockHeadline != null) {
				redisCache.setObj(keys, aStockHeadline, 10 * 60, AStockHeadline.class);
			}
			log.info("getCurrentDataService AStockHeadline= " + aStockHeadline);
		}
		return MyJsonConverter.objectToString(aStockHeadline);
	}

	@Override
	public String getUserInfoState(String passportId) {
		RequestMessage resultMes = new RequestMessage();
		if (StringUtils.isEmpty(passportId)) {
			resultMes.setMsg("请输入有效的passportId！");
			resultMes.setRetCode("1");
			return MyJsonConverter.objectToString(resultMes);
		}
		String keys = key + "getUserInfoState" + passportId;
		String json = null;
		redisCache.deleteObj(keys);// 测试的时候加上
		if (redisCache.hasKey(keys)) {
			log.info("getUserInfoState redisCache.hasKey(keys)");
			json = redisCache.getString(keys);
		} else {
			try {
				json = restTemplate.getForObject(PayConstant.USER_INFO_STATE_URL + "?passportId={passportId}",
						String.class, passportId);
			} catch (Exception e) {
				log.info("用户状态接口调用失败！" + e);
				resultMes.setMsg("用户状态接口调用失败！" + e);
				resultMes.setRetCode("1"); //
				return MyJsonConverter.objectToString(resultMes);
			}
		}
		redisCache.setString(keys, 10 * 60, json);
		return json;
	}

	@Override
	public String getLatestAssessmentInfo(String passportId) {
		RequestMessage resultMes = new RequestMessage();
		String keys = key + "getLatestAssessmentInfo" + passportId;
		AssessmentInfo ass = new AssessmentInfo();
		Ext ext = new Ext();
		String json = null;
		redisCache.deleteObj(keys);// 测试的时候加上
		if (redisCache.hasKey(keys)) {
			log.info("getLatestAssessmentInfo redisCache.hasKey(keys)");
			json = redisCache.getString(keys);
		} else {
			try {
				String json1 = restTemplate.getForObject(
						PayConstant.LATE_ASSESSMENT_INFO_URL + "?passportId={passportId}", String.class, passportId);
				if (json1 != null && !"".equals(json1.trim())) {
					JSONObject parse = JSONObject.parseObject(json1);
					String resultCode = parse.getString("resultCode");
					ass.setRetCode(resultCode);
					String resultMsg = parse.getString("resultMsg");
					ass.setRetMsg(resultMsg);
					if ("0".equals(resultCode.trim())) {
						
						String riskLevel = parse.getString("riskLevel");
						JSONObject parseObject = JSONObject.parseObject(riskLevel);
						String msg = parseObject.getString("msg");
						Double targetScore = parse.getDouble("targetScore");
						Double score = parse.getDouble("score");
						String passport = parse.getString("passportId");
						ext.setPassportId(passport);
						ext.setRiskLevel(msg);
						ext.setScore(score);
						ext.setTargetScore(targetScore);
					} 
					log.debug("getLatestAssessmentInfo---json1"+json1);
					ass.setExt(ext);
					log.debug("getLatestAssessmentInfo---ass"+ass);
					json=MyJsonConverter.objectToString(ass);
				}
			} catch (Exception e) {
				log.info("风测接口调用失败！" + e);
				resultMes.setMsg("风测接口调用失败！" + e);
				resultMes.setRetCode("1"); //
				return MyJsonConverter.objectToString(resultMes);
			}
		}
		redisCache.setString(keys, 10 * 60, json);
		return json;
	}

}
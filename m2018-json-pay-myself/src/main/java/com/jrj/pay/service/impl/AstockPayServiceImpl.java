package com.jrj.pay.service.impl;

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
import com.jrj.pay.bean.AStockHeadline;
import com.jrj.pay.bean.CmsFrag;
import com.jrj.pay.bean.OrderList;
import com.jrj.pay.bean.ReadingDetail;
import com.jrj.pay.bean.RequestMessage;
import com.jrj.pay.bean.ResposeResult;
import com.jrj.pay.bean.root;
import com.jrj.pay.constant.PayConstant;
import com.jrj.pay.dao.AstockHeadlineDao;
import com.jrj.pay.service.AstockPayService;
import com.jrj.pay.service.HttpAPIService;
import com.jrj.pay.util.DateUtil;
import com.jrj.pay.util.MyJsonConverter;
import com.jrj.pay.util.PageUtils;
import com.jrj.pay.util.RedisCache;
import com.jrj.pay.util.Xml2ObjUtil;
import com.jrj.pay.vo.OrderListMessVo;
import com.jrj.pay.vo.PageMessageAndData;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @description M站A股头条-付费版-serviceipml
 * @author qiushun.sun
 * @date 2018.03.24
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
	public PageMessageAndData<AStockHeadline> getHeadlinesService(Integer curPage, String expireDate, String days, String validDate, Integer pageSize) {
		String keys = key + "getHeadlinesService" + curPage+pageSize;
	//	redisCache.deleteObj(keys);// 上线时关闭
		PageMessageAndData<AStockHeadline> messageAndData = null;
		if (redisCache.hasKey(keys)) {
			log.info("getHeadlinesService redisCache.hasKey(keys)");
			messageAndData = redisCache.getObj(keys, PageMessageAndData.class);
		} else {
			if(pageSize==null || pageSize<0){
				PageHelper.startPage(curPage, PayConstant.PAGE_SIZE);
			}else{
				PageHelper.startPage(curPage, pageSize);
			}
			Page<AStockHeadline> headlines = astockHeadlineDao.getHeadlinesDao(expireDate + " 23:59:59");
			messageAndData = PageUtils.setPageMessageAndData(expireDate, validDate, days, headlines);
			redisCache.setObj(keys, messageAndData, 10 * 60, PageMessageAndData.class);
		}
		messageAndData.setDays(days);
		messageAndData.setExpireDate(expireDate+" 23:59:59");
		log.info("curPage:" + curPage + " expireDate:" + expireDate + " messageAndData:" + messageAndData+"pageSize="+pageSize);
		return messageAndData;
	}

	@Override
	public String getHeadlineContentService(String iiid) {
		log.info("getHeadlineContentService");
		String xml = null;
		root roots = null;
		String keys = key + "getHeadlineContentService" + iiid;
	//	redisCache.deleteObj(keys);// 上线时关闭
		if (redisCache.hasKey(keys)) {
			log.info("getHeadlineContentService redisCache.hasKey(keys)");
			roots = redisCache.getObj(keys, root.class);
		} else {
			try {
				long start = System.currentTimeMillis();
				log.info("getHeadlineContentService--请求的接口："+PayConstant.CMS_DATA_URL + "?iiid=" + iiid);
				// 使用httpClient请求
				xml = httpAPIService.doGet(PayConstant.CMS_DATA_URL + "?iiid=" + iiid);
				roots = (root) Xml2ObjUtil.convertXmlStrToObject(root.class, xml);
				if(roots != null){
					redisCache.setObj(keys, roots, 10*60, root.class);
				}
				long end = System.currentTimeMillis();
				log.info("getHeadlineContentService--请求的接口："+PayConstant.CMS_DATA_URL + "?iiid=" + iiid+"用时：{}毫秒",(end-start));
				log.info("httpAPIService roots= " + roots); 
			} catch (Exception e) {
				log.info("getHeadlineContentService exception= " + e);
			}
		}
		return MyJsonConverter.objectToString(roots);
	}

	@Override
	public String hasExperiencedService(String uid) {
		String json = null;
		RequestMessage message = new RequestMessage();
		message.setIsLogined("0"); // 已登录
		try {
			long start = System.currentTimeMillis();
			log.info("hasExperiencedService--请求的接口："+PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid="+uid);
			json = restTemplate.getForObject(PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid={uid}", String.class, uid);
			long end = System.currentTimeMillis();
			log.info("hasExperiencedService--请求的接口："+PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid="+uid+"用时：{}毫秒",(end-start));
			
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
		if(StringUtils.isEmpty(uid)){
			message.setMsg("请输入有效的passportId！");
			message.setRetCode("1");
			return MyJsonConverter.objectToString(message);
		}
		
		// 判断是否已经体验过
		String json = null;
		try {
			long start = System.currentTimeMillis();
			log.info("freeExperienceService--请求的接口："+PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid="+uid);
			json = restTemplate.getForObject(PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid={uid}", String.class, uid);
			long end = System.currentTimeMillis();
			log.info("freeExperienceService--请求的接口："+PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid="+uid+"用时：{}毫秒",(end-start));
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
		if(!"1".equals(message.getIsFreeUsered())){
			log.info(uid + " 用户未体验过，进行体验！");
			String addZeroJson = null;
			try {
				Long clientOrderId = System.currentTimeMillis();
				log.info("url= " + PayConstant.ADD_ZERO_URL + "bizCode=5&productId=8&productSubId=100080001&type=6&source=wap&reason=免费体验赠送&buyerId=" + uid + "&clientOrderId=" + clientOrderId);
				addZeroJson = restTemplate.getForObject(PayConstant.ADD_ZERO_URL + "?bizCode=5&productId=8&productSubId=100080001&type=6&source=wap&reason=免费体验赠送&buyerId=" + uid + "&clientOrderId=" + clientOrderId, String.class);
				long end = System.currentTimeMillis();
				log.info("freeExperienceService--请求的接口："+PayConstant.ADD_ZERO_URL+ "?bizCode=5&productId=8&productSubId=100080001&type=6&source=wap&reason=免费体验赠送&buyerId=" + uid + "&clientOrderId=" + clientOrderId+"用时：{}毫秒",(end-clientOrderId));
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
		//redisCache.deleteObj(keys);// 上线时关闭
		AStockHeadline detail = null;
		if (redisCache.hasKey(keys)) {
			log.info("headlineAbstractService redisCache.hasKey(keys)");
			detail = redisCache.getObj(keys, AStockHeadline.class);
		} else {
			detail = astockHeadlineDao.getHeadlineAbstractDao(iiid);
			if(detail != null){
				redisCache.setObj(keys, detail, 5 * 60, AStockHeadline.class);
			}
		}
		log.info("detail= " + detail);
		return detail!=null?MyJsonConverter.objectToString(detail):"";
	}

	@Override
	public String userPermissionService(String passportId) {
		String json = null;
		RequestMessage resultMes = new RequestMessage();
		// 调用接口获取用户权限信息
		try{
			long start = System.currentTimeMillis();
			log.info("userPermissionService--请求的接口："+PayConstant.VERIFY_INTERFACE_URL + "/privilege/getUserPrivilegePeriod?bizCode=5&productId=8&productSubId=100080001&passportId="+passportId);
			json = restTemplate.getForObject(PayConstant.VERIFY_INTERFACE_URL + "/privilege/getUserPrivilegePeriod?bizCode=5&productId=8&productSubId=100080001&passportId={passportId}", String.class, passportId);
			long end = System.currentTimeMillis();
			log.info("userPermissionService--请求的接口："+PayConstant.VERIFY_INTERFACE_URL + "/privilege/getUserPrivilegePeriod?bizCode=5&productId=8&productSubId=100080001&passportId="+passportId+"用时：{}毫秒",(end-start));
		}catch(Exception e){
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
		try{
			long start = System.currentTimeMillis();
			String timeStamp = DateUtil.getDateString(); // 获取日期yyyyMMddHHmmss
			log.info("orderResultService--请求的接口："+PayConstant.ORDER_RESULT_URL + "?sign=astockpay&signType=2&version=1.0&bizCode=5&timeStamp={timeStamp}&sellerOrderId={sellerOrderId}",timeStamp,sellerOrderId);
			json = restTemplate.getForObject(PayConstant.ORDER_RESULT_URL + "?sign=astockpay&signType=2&version=1.0&bizCode=5&timeStamp={timeStamp}&sellerOrderId={sellerOrderId}", String.class, timeStamp, sellerOrderId);
			long end = System.currentTimeMillis();
			log.info("orderResultService--请求的接口："+PayConstant.ORDER_RESULT_URL + "?sign=astockpay&signType=2&version=1.0&bizCode=5&timeStamp={timeStamp}&sellerOrderId={sellerOrderId}"+"用时：{}毫秒",timeStamp,sellerOrderId,(end-start));
		}catch(Exception e){
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
		// 获取订单状态   0:待支付  1:支付成功  2:退款中  3:退款成功  4:关闭  5:分单支付中
		String status = jsonObject.getString("status");
		log.info("getOrderResult status= " + status);
		if("1".equals(status)){ // 支付成功
			resultMes.setRetCode("0"); // 请求成功
			resultMes.setMsg("支付成功！");
			resultMes.setStatus("0");; // 支付成功
		}else{ // 其他情况
			resultMes.setRetCode("0"); // 请求成功
			resultMes.setMsg("未支付成功！");
			resultMes.setStatus("1");; // 其他未支付成功
		}
		return resultMes;
	}

	/**
	 * 获取用的权限信息
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
		} else { // 账号无权限
			log.info("getUserPerssion account is invalid");
			resultMes.setRetCode("0"); // 请求成功
			resultMes.setHasPermission("1"); // 账号无权限
			String json = null;
			try{
				long start = System.currentTimeMillis();
				log.info("getUserPerssion--请求的接口："+PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid={passportId}",passportId);
				json = restTemplate.getForObject(PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid={passportId}", String.class, passportId);
				long end = System.currentTimeMillis();
				log.info("getUserPerssion--请求的接口："+PayConstant.BUY_LIST_URL + "?productSubId=100080001&uid={passportId}"+"用时：{}毫秒",passportId,(end-start));
			
			}catch(Exception e){
				log.info("订单接口调用失败！" + e);
				resultMes.setMsg("订单接口调用失败！" + e);
				resultMes.setRetCode("1"); // 
				return resultMes;
			}
			OrderListMessVo orderListMesses = MyJsonConverter.stringToObject(json, OrderListMessVo.class);
			List<OrderList> orderList = orderListMesses.getOrderList();
			log.info("orderList=" + orderList);
			log.info("orderList.size= " + orderList.size());
			if(orderList!= null && orderList.size()>0){
				resultMes.setMsg("账号无权限，且该账号已经免费体验过！");
				resultMes.setIsFreeUsered("1"); // 用户已体验过
			}else{
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
		AStockHeadline aStockHeadline  = null;
		//redisCache.deleteObj(keys);// 上线时关闭
		if(redisCache.hasKey(keys)){
			log.info("getCurrentDataService redisCache.hasKey(keys)");
			aStockHeadline=redisCache.getObj(keys, AStockHeadline.class);
		}else{
			aStockHeadline = astockHeadlineDao.getCurrentDataDao(date);
			if(aStockHeadline != null){
				redisCache.setObj(keys, aStockHeadline , 10*60, AStockHeadline.class);
			}
			log.info("getCurrentDataService AStockHeadline= " + aStockHeadline );
		}
		return MyJsonConverter.objectToString(aStockHeadline);
	}

	@Override
	public String getReading(String chnmb, String clsId) {
		log.debug("传入的参数--chnmb：{}，clsId：{}",chnmb,clsId);
		
		ResposeResult respose=new ResposeResult();
		respose.setCode(200);
		if(chnmb==null || clsId==null){
			respose.setMessage("至少有参数一个没有值，那么久使用使用默认的值，chnmb=611,clsId=002120");
			chnmb="611";
			clsId="002120";
		}else{
			respose.setMessage("ok");
		}
		
		String keys = key + "getReading"+chnmb+clsId;
		CmsFrag cmsFrag  = null;
	//	redisCache.deleteObj(keys);// 上线时关闭
		if(redisCache.hasKey(keys)){
			log.info("getCurrentDataService redisCache.hasKey(keys)");
			cmsFrag=redisCache.getObj(keys, CmsFrag.class);
		}else{
			cmsFrag = astockHeadlineDao.getReading(chnmb,clsId);
			if(cmsFrag != null){
				redisCache.setObj(keys, cmsFrag , 10*60, CmsFrag.class);
			}
			log.info("getReading CmsFrag= " + cmsFrag );
		}
		respose.setData(cmsFrag==null?"没有查到对应的值":cmsFrag);
		return  MyJsonConverter.objectToString(respose);
	}

	@Override
	public String getDetails(String iiid) {
		String keys = key + "getDetails" + iiid;
		log.debug("入参的值iiid:{}",iiid);
		//redisCache.deleteObj(keys);// 上线时关闭
		ReadingDetail detail = null;
		if (redisCache.hasKey(keys)) {
			log.info("getDetails redisCache.hasKey(keys)");
			detail = redisCache.getObj(keys, ReadingDetail.class);
		} else {
			detail = astockHeadlineDao.getReadingDetailDao(iiid);
			if(detail != null){
				redisCache.setObj(keys, detail, 5 * 60, ReadingDetail.class);
			}
		}
		log.info("detail= " + detail);
		ResposeResult respose=new ResposeResult();
		respose.setCode(200);
		respose.setMessage("ok");
		respose.setData(detail==null?"没有查到对应文章":detail);
		return  MyJsonConverter.objectToString(respose);
	}

}
package com.jrj.pay.pc.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrj.pay.pc.bean.AStockHeadline;
import com.jrj.pay.pc.bean.RequestMessage;
import com.jrj.pay.pc.service.AstockPayService;
import com.jrj.pay.pc.util.MyJsonConverter;
import com.jrj.pay.pc.vo.PageMessageAndData;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @description pc端A股头条-付费版 -controller
 * @author bin.wang
 * @date 2018.06.20
 *
 */
@Slf4j
@RestController
@RequestMapping("/json/pcpay/headline")
public class AStockPayController {

	@Autowired
	private AstockPayService astockPayservice;

	/**
	 * 获取数据列表
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/getHeadlines")
	public String getHeadlines(HttpServletRequest req, @RequestParam(value = "curPage", required = true) Integer curPage
			, @RequestParam(value = "vname", required = false) String vname) {
		String date = new Date().toLocaleString().split(" ")[0]; // 获取当前日期
		String days = req.getAttribute("days")!=null?req.getAttribute("days").toString():"";
		String validDate = req.getAttribute("validDate")!=null?req.getAttribute("validDate").toString():"";
		String expireDate = req.getAttribute("expireDate")!=null?req.getAttribute("expireDate").toString():date;
		log.info("getHeadlines curPage=" + curPage + " days=" + days + " validDate=" + validDate + " expireDate=" + expireDate);
		PageMessageAndData<AStockHeadline> headlines = astockPayservice.getHeadlinesService(curPage, expireDate, days, validDate);
		if(vname==null ||"".equals(vname.trim())){
			return MyJsonConverter.objectToString(headlines);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(MyJsonConverter.objectToString(headlines));
		return buffer.toString();
	}

	/**
	 * 获取列表的具体信息
	 * 
	 * @return
	 */
	@RequestMapping(value="/getHeadlineContent")
	public String getHeadlineContent(@RequestParam(value = "iiid", required = true) String iiid
			, @RequestParam(value = "vname", required = false) String vname) {
		log.info("getHeadlineContent iiid= " + iiid);
		if(vname==null ||"".equals(vname.trim())){
			return astockPayservice.getHeadlineContentService(iiid);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(astockPayservice.getHeadlineContentService(iiid));
		return buffer.toString();
		//return astockPayservice.getHeadlineContentService(iiid);
	}

	/**
	 * 用户是否体验过
	 * 
	 * @return
	 */
	@RequestMapping("/hasExperienced")
	public String hasExperienced(HttpServletRequest request, HttpServletResponse response
			, @RequestParam(value = "vname", required = false) String vname) {
		String myjrj_userid = request.getAttribute("myjrj_userid").toString();
		log.info("hasExperienced uid= " + myjrj_userid);
		if(vname==null ||"".equals(vname.trim())){
			return astockPayservice.hasExperiencedService(myjrj_userid);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(astockPayservice.hasExperiencedService(myjrj_userid));
		return buffer.toString();	
	}

	/**
	 * 免费体验
	 * 
	 * @return
	 */
	@RequestMapping("/freeExperience")
	public String freeExperience(HttpServletRequest request, HttpServletResponse response
			, @RequestParam(value = "vname", required = false) String vname) {
		String myjrj_userid = request.getAttribute("myjrj_userid").toString();
		log.info("freeExperience uid= " + myjrj_userid);
		if(vname==null ||"".equals(vname.trim())){
			return astockPayservice.freeExperienceService(myjrj_userid);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(astockPayservice.freeExperienceService(myjrj_userid));
		return buffer.toString();	
	}

	/**
	 * 获取头条的摘要
	 * 
	 * @return
	 */
	@RequestMapping("/headlineAbstract")
	public String headlineAbstract(@RequestParam(value = "iiid", required = true) String iiid
			, @RequestParam(value = "vname", required = false) String vname) {
		log.info("headlineAbstract iiid= " + iiid);
		if(vname==null ||"".equals(vname.trim())){
			return astockPayservice.headlineAbstractService(iiid);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(astockPayservice.headlineAbstractService(iiid));
		return buffer.toString();	
		
	}
	
	/**
	 * 用户权限判断
	 * 
	 * @return
	 */
	@RequestMapping("/userPermission")
	public String userPermission(HttpServletRequest request
			, @RequestParam(value = "vname", required = false) String vname) {
		String myjrj_userid = request.getAttribute("myjrj_userid").toString();
		log.info("userPermission myjrj_userid= " + myjrj_userid);
		if(vname==null ||"".equals(vname.trim())){
			return  astockPayservice.userPermissionService(myjrj_userid);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		if("test".equals(vname.trim())){
			RequestMessage resultMes=new RequestMessage();
			resultMes.setRetCode("0"); // 请求成功
			resultMes.setHasPermission("0"); // 账号有权限
			resultMes.setMsg("账号有权限");
			resultMes.setIsFreeUsered(""); // 用户体验设置为空
			buffer.append(MyJsonConverter.objectToString(resultMes));
		}else
			buffer.append(astockPayservice.userPermissionService(myjrj_userid));
		return buffer.toString();	
	}
	
	/**
	 * 查询订单结果
	 * 
	 * @return
	 */
	@RequestMapping("/orderResult")
	public String orderResult(@RequestParam(value = "sellerOrderId", required = true) String sellerOrderId
			, @RequestParam(value = "vname", required = false) String vname) {
		// String sellerOrderId="13085510416088202035268500000532";
		log.info("orderResult sellerOrderId= " + sellerOrderId);
		if(vname==null ||"".equals(vname.trim())){
			return  astockPayservice.orderResultService(sellerOrderId);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(astockPayservice.orderResultService(sellerOrderId));
		return buffer.toString();	
	}
	
	/**
	 * 获取当前的第一条数据
	 * 
	 * @return
	 */
	@RequestMapping(value="/getCurrentData")
	public String getCurrentData(@RequestParam(value = "vname", required = false) String vname) {
		log.info("getCurrentData Controller");
		if(vname==null ||"".equals(vname.trim())){
			return  astockPayservice.getCurrentDataService();
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(astockPayservice.getCurrentDataService());
		return buffer.toString();	
	//	return astockPayservice.getCurrentDataService();
	}
	
	
	/**
	 * 是获取用户的状态
	 * @param passportId 这个是通行证
	 * @param vname 只是一个标识
	 * @return
	 * 
	 */
	@RequestMapping(value="/getUserInfoState")
	public String getUserInfoState(@RequestParam(value = "vname", required = false) String vname,
			HttpServletRequest request) {
		log.info("getUserInfoState Controller");
		String  passportId= request.getAttribute("myjrj_userid")!=null?request.getAttribute("myjrj_userid").toString().trim():"";
		if(vname==null ||"".equals(vname.trim())){
			return  astockPayservice.getUserInfoState(passportId);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(astockPayservice.getUserInfoState(passportId));
		return buffer.toString();	
	}
	
	
	/**
	 * 风险测评
	 * @param passportId 这个是通行证
	 * @param vname 只是一个标识
	 * @return
	 * 
	 */
	@RequestMapping(value="/getLatestAssessmentInfo")
	public String getLatestAssessmentInfo(@RequestParam(value = "vname", required = false) String vname,
			HttpServletRequest request,
			@RequestParam(value = "test", required = false) String test) {
		log.info("getLatestAssessmentInfo Controller");	
		
		String  passportId= request.getAttribute("myjrj_userid")!=null?request.getAttribute("myjrj_userid").toString().trim():"";
		
		if("".equals(passportId)){//测试用的
			passportId=test;
		}
		if(vname==null ||"".equals(vname.trim())){
			return  astockPayservice.getLatestAssessmentInfo(passportId);
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("var ");
		buffer.append(vname+"=");
		buffer.append(astockPayservice.getLatestAssessmentInfo(passportId));
		return buffer.toString();	
	}

}
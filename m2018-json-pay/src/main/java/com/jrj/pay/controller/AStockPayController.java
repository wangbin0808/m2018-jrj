package com.jrj.pay.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrj.pay.bean.AStockHeadline;
import com.jrj.pay.service.AstockPayService;
import com.jrj.pay.util.MyJsonConverter;
import com.jrj.pay.vo.PageMessageAndData;

/**
 * 
 * @description M站A股头条-付费版-controller
 * @author qiushun.sun
 * @date 2018.03.24
 *
 */
@Slf4j
@RestController
@RequestMapping("/json/pay/headline")
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
	public String getHeadlines(HttpServletRequest req, @RequestParam(value = "curPage", required = true) Integer curPage) {
		String date = new Date().toLocaleString().split(" ")[0]; // 获取当前日期
		String days = req.getAttribute("days")!=null?req.getAttribute("days").toString():"";
		String validDate = req.getAttribute("validDate")!=null?req.getAttribute("validDate").toString():"";
		String expireDate = req.getAttribute("expireDate")!=null?req.getAttribute("expireDate").toString():date;
		log.info("getHeadlines curPage=" + curPage + " days=" + days + " validDate=" + validDate + " expireDate=" + expireDate);
		PageMessageAndData<AStockHeadline> headlines = astockPayservice.getHeadlinesService(curPage, expireDate, days, validDate);
		return MyJsonConverter.objectToString(headlines);
	}

	/**
	 * 获取列表的具体信息
	 * 
	 * @return
	 */
	@RequestMapping(value="/getHeadlineContent", produces={"application/json;charset=UTF-8"})
	public String getHeadlineContent(@RequestParam(value = "iiid", required = true) String iiid) {
		log.info("getHeadlineContent iiid= " + iiid);
		return astockPayservice.getHeadlineContentService(iiid);
	}

	/**
	 * 用户是否体验过
	 * 
	 * @return
	 */
	@RequestMapping("/hasExperienced")
	public String hasExperienced(HttpServletRequest request, HttpServletResponse response) {
		String myjrj_userid = request.getAttribute("myjrj_userid").toString();
		log.info("hasExperienced uid= " + myjrj_userid);
		return astockPayservice.hasExperiencedService(myjrj_userid);
	}

	/**
	 * 免费体验
	 * 
	 * @return
	 */
	@RequestMapping("/freeExperience")
	public String freeExperience(HttpServletRequest request, HttpServletResponse response) {
		String myjrj_userid = request.getAttribute("myjrj_userid").toString();
		log.info("freeExperience uid= " + myjrj_userid);
		return astockPayservice.freeExperienceService(myjrj_userid);
	}

	/**
	 * 获取头条的摘要
	 * 
	 * @return
	 */
	@RequestMapping("/headlineAbstract")
	public String headlineAbstract(@RequestParam(value = "iiid", required = true) String iiid) {
		log.info("headlineAbstract iiid= " + iiid);
		return astockPayservice.headlineAbstractService(iiid);
	}
	
	/**
	 * 用户权限判断
	 * 
	 * @return
	 */
	@RequestMapping("/userPermission")
	public String userPermission(HttpServletRequest request) {
		String myjrj_userid = request.getAttribute("myjrj_userid").toString();
		log.info("userPermission myjrj_userid= " + myjrj_userid);
		return astockPayservice.userPermissionService(myjrj_userid);
	}
	
	/**
	 * 查询订单结果
	 * 
	 * @return
	 */
	@RequestMapping("/orderResult")
	public String orderResult(@RequestParam(value = "sellerOrderId", required = true) String sellerOrderId) {
		// String sellerOrderId="13085510416088202035268500000532";
		log.info("orderResult sellerOrderId= " + sellerOrderId);
		return astockPayservice.orderResultService(sellerOrderId);
	}
	
	/**
	 * 获取当前的第一条数据
	 * 
	 * @return
	 */
	@RequestMapping(value="/getCurrentData", produces={"application/json;charset=UTF-8"})
	public String getCurrentData() {
		log.info("getCurrentData Controller");
		return astockPayservice.getCurrentDataService();
	}

}
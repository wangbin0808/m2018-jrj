package com.jrj.pay.pc.service;

import com.jrj.pay.pc.bean.AStockHeadline;
import com.jrj.pay.pc.vo.PageMessageAndData;

/**
 * 
 * @description pc端A股头条-付费版-service
 * @author bin.wang
 * @date 2018.06.20
 *
 */
public interface AstockPayService {

	/**
	 * 免费体验
	 * 
	 * @return
	 */
	String freeExperienceService(String uid);

	/**
	 * 获取列表的具体信息
	 * 
	 * @return
	 */
	String getHeadlineContentService(String iiid);

	/**
	 * 获取数据列表
	 * 
	 * @return
	 */
	PageMessageAndData<AStockHeadline> getHeadlinesService(Integer curPage, String expireDate, String days, String validDate);

	/**
	 * 用户是否体验过
	 * 
	 * @return
	 */
	String hasExperiencedService(String uid);

	/**
	 * 获取指定的头条
	 * @param iiid
	 * @return
	 */
	String headlineAbstractService(String iiid);

	/**
	 * 用户权限判断
	 * @param passportId
	 * @return
	 */
	String userPermissionService(String passportId);

	/**
	 * 订单通知查询
	 * @param sellerOrderId
	 * @return
	 */
	String orderResultService(String sellerOrderId);

	/**
	 * 获取当前的第一条数据
	 * @return
	 */
	String getCurrentDataService();

	/**
	 * 是获取用户的状态（手机认证、实名认证、风测）
	 * @return
	 */
	String getUserInfoState(String passportId);

	/**
	 * 风测
	 * @param passportId 通行证
	 * @return
	 */
	String getLatestAssessmentInfo(String passportId);

}
package com.jrj.pay.service;

import com.jrj.pay.bean.AStockHeadline;
import com.jrj.pay.vo.PageMessageAndData;

/**
 * 
 * @description M站A股头条-付费版-service
 * @author qiushun.sun
 * @date 2018.03.24
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
	 * @param pageSize 
	 * 
	 * @return
	 */
	PageMessageAndData<AStockHeadline> getHeadlinesService(Integer curPage, String expireDate, String days, String validDate, Integer pageSize);

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
	 * 获得碎片信息
	 * @param chnmb 频道号
	 * @param clsId 栏目号数组
	 * @return
	 */
	String getReading(String chnmb, String clsId);

	/**
	 * 获得试读文章的详情
	 * @param iiid 文章的id
	 * @return
	 */
	String getDetails(String iiid);

}
package com.jrj.pay.pc.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.jrj.pay.pc.bean.AStockHeadline;

/**
 * 
 * @description pc端A股头条-付费版-Dao
 * @author bin.wang
 * @date 2018.06.20
 *
 */
public interface AstockHeadlineDao {

	/**
	 * 获取头条的列表
	 */
	@Select("SELECT iiid,title,detail,makeDate,paperMediaSource,infoUrl,keyWord,(SELECT imgUrl FROM INFO_IMG img WHERE img.iiid=item.iiid) AS imgUrl " +
			"from info_item item WHERE item.makeDate<=#{expireDate} AND ChanNum='010' AND InfoCls='001179' ORDER BY item.makeDate DESC")
	Page<AStockHeadline> getHeadlinesDao(@Param("expireDate") String expireDate);

	/**
	 * 获取头条的摘要
	 * @param iiid
	 * @return
	 */
	@Select("SELECT iiid,detail,title,makeDate,paperMediaSource,infoUrl,(SELECT imgUrl FROM INFO_IMG img WHERE img.iiid=#{iiid}) AS imgUrl " +
			"from info_item item WHERE iiid=#{iiid}")
	AStockHeadline getHeadlineAbstractDao(String iiid);
	
	
	/**
	 * 获取当前的第一条数据
	 */
	@Select("SELECT top 1 iiid,detail,title,makeDate,paperMediaSource,infoUrl,keyWord,(SELECT imgUrl FROM INFO_IMG img WHERE img.iiid=item.iiid) AS imgUrl " +
			"from info_item item WHERE item.makeDate>=#{date} AND ChanNum='010' AND InfoCls='001179' ORDER BY item.makeDate DESC")
	AStockHeadline getCurrentDataDao(String date);
	
}
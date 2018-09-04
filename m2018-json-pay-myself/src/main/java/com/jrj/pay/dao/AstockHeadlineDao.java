package com.jrj.pay.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.jrj.pay.bean.AStockHeadline;
import com.jrj.pay.bean.CmsFrag;
import com.jrj.pay.bean.ReadingDetail;

/**
 * 
 * @description M站A股头条-付费版-Dao
 * @author qiushun.sun
 * @date 2018.03.24
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

	/**
	 * @Description: 取某频道下对应的碎片
	 * @param chnmb 频道号
	 * @param clsId 栏目号数组
	 * @return
	 */
	@Select("select SpecId,Intor,PubDate,Audik,Chnmb,ClsId,Sorts from SPEC_ITEM Where Chnmb=#{chnmb} and ClsId=#{clsId}")
	CmsFrag getReading(@Param("chnmb")String chnmb,@Param("clsId")String clsId);

	/**
	 * 获得为试读文章的详情
	 * @param iiid 文章id
	 * @return
	 */
	@Select("SELECT iiid,title,detail,makeDate,paperMediaSource,infoUrl,keyWord,(SELECT imgUrl FROM INFO_IMG img WHERE img.iiid=item.iiid) AS imgUrl " +
			",(SELECT Context from info_item_content con where item.iiid=con.iiid) AS content from info_item item WHERE item.iiid=#{iiid} and "
			+ "item.iiid not in(select iiid from info_item where ChanNum ='010' and InfoCls='001179')")
	ReadingDetail getReadingDetailDao(@Param("iiid")String iiid);
	
}
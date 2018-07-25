package cn.com.jrj.m.dao.cms;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.com.jrj.m.domain.CmsNews;
import cn.com.jrj.m.domain.InfoStock;

/**
 * @Description: 新闻库接口
 * @author yuhai.li  
 * @date 2018年1月19日 下午2:31:50
 */
@Mapper
public interface CmsDao {
	/**
	 * @Description: 获取前n条新闻
	 * @return
	 * @return List<CmsNews>
	 * @author yuhai.li
	 * @date 2018年1月25日 上午11:07:38
	 */
	List<CmsNews> topNList(@Param("n") int n);
	/**
	 * @Description: 根据iiid获取资讯详情
	 * @param iiid
	 * @return List<CmsNews>
	 * @author yuhai.li
	 * @date 2018年1月25日 上午11:07:21
	 */
	List<CmsNews> getNewsByIiid(@Param("iiid") int iiid);
	/**
	 * @Description: 根据资讯iiid获取资讯相关股票
	 * @param iiid
	 * @return List<InfoStock>
	 * @author yuhai.li
	 * @date 2018年1月25日 上午11:06:58
	 */
	List<InfoStock> getInfoStockByIiid(@Param("iiid") int iiid);
	/**
	 * @Description: 前n条7*24小时新闻
	 * @param num
	 * @return
	 * @return List<CmsNews>
	 * @author yuhai.li
	 * @date 2018年2月2日 上午10:25:36
	 */
	List<CmsNews> topNIndexList(@Param("n") int n);
	/**
	 * @Description: 获取前n条新闻iiid列表
	 * @param num
	 * @return
	 * @return List<Integer>
	 * @author yuhai.li
	 * @date 2018年2月2日 上午10:31:14
	 */
	List<Integer> topNListId(@Param("n") int n);
	
	/**
	 * @Description: 获取一段日期区间的资讯iiid
	 * @param lastDate
	 * @param nextDate
	 * @return
	 * @return List<Integer>
	 * @author yuhai.li
	 * @date 2018年2月7日 上午10:44:06
	 */
	List<Integer> getIiids(@Param("lastDate") Date lastDate, @Param("nextDate") Date nextDate);
	
	/**
	 * @Description: makedate晚于某日期的资讯iiid
	 * @param makeDate
	 * @return List<Integer>
	 * @author yuhai.li
	 * @date 2018年2月7日 下午1:29:14
	 */
	List<Integer> getNewsIiidLate(@Param("makeDate")Date makeDate, @Param("iiid")int iiid);
}

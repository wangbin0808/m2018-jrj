package cn.com.jrj.m.service.cms;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;
import cn.com.jrj.m.dao.cms.CmsDao;
import cn.com.jrj.m.domain.CmsNews;
import cn.com.jrj.m.domain.InfoStock;

/**
 * @Description: 新闻库数据处理Service
 * @author yuhai.li  
 * @date 2018年1月19日 下午2:34:53
 */
@Service
public class CmsService {

	@Autowired
	private CmsDao cmsDao;
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * @Description: 测试用，查询前十条新闻
	 * @return List<CmsNews>
	 * @author yuhai.li
	 * @date 2018年1月19日 下午2:28:49
	 */
	public List<CmsNews> getTopList(int num){
		return cmsDao.topNList(num);
	}
	
	/**
	 * @Description: 测试用，前n条7*24小时滚动新闻
	 * @param num
	 * @return List<CmsNews>
	 * @author yuhai.li
	 * @date 2018年2月2日 上午10:24:49
	 */
	public List<CmsNews> getIndexList(int num){
		return cmsDao.topNIndexList(num);
	}
	
	/**
	 * @Description: 根据iiid查询新闻，查到多条取第一条
	 * @param iiid 新闻iiid
	 * @return CmsNews
	 * @author yuhai.li
	 * @date 2018年1月19日 下午2:28:18
	 */
	public CmsNews getNewsByIiid(int iiid){
		List<CmsNews> list = cmsDao.getNewsByIiid(iiid);
		if(list==null || list.size()==0){
			return null;
		}else {
			return list.get(0);
		}
	}

	/**
	 * @Description: 根据资讯iiid获取资讯相关股票
	 * @param iiid
	 * @return List<InfoStock>
	 * @author yuhai.li
	 * @date 2018年1月25日 上午11:05:33
	 */
	public List<InfoStock> getInfoStockByIiid(int iiid) {
		if(iiid<=0){
			return null;
		}
		return cmsDao.getInfoStockByIiid(iiid);
	}

	/**
	 * @Description: 获取前n条新闻的id
	 * @param i
	 * @return
	 * @return List<Integer>
	 * @author yuhai.li
	 * @date 2018年2月2日 上午10:30:24
	 */
	public List<Integer> getTopListId(int num) {
		return cmsDao.topNListId(num);
	}
	
	/**
	 * @Description: 获取时间段内的资讯iiid
	 * @param lastDate
	 * @param nextDate
	 * @return
	 * @return List<Integer>
	 * @author yuhai.li
	 * @date 2018年2月7日 上午10:41:13
	 */
	public List<Integer> getIiids(Date lastDate, Date nextDate) {
		return cmsDao.getIiids(lastDate,nextDate);
	}

	/**
	 * @Description: makedate晚于某日期发布的新闻
	 * @param makeDate
	 * @return List<Integer>
	 * @author yuhai.li
	 * @date 2018年2月7日 下午1:28:19
	 */
	public List<Integer> getNewsIiidLate(Date makeDate, int iiid) {
		return cmsDao.getNewsIiidLate(makeDate, iiid);
	}
}

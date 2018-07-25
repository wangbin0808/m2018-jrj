package cn.com.jrj.m.service.mongo;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.jrj.m.domain.CmsNews;
import cn.com.jrj.m.domain.InfoStock;
import cn.com.jrj.m.domain.MongoNews;
import cn.com.jrj.m.domain.MongoNewsTask;
import cn.com.jrj.m.utils.Constant;
import cn.com.jrj.m.utils.HttpUtil;

@Service
@Slf4j
public class MongoNewsService {

	@Autowired
	@Qualifier(value="newswrMongo")
	private MongoTemplate newswrMongo;
	@Autowired
	@Qualifier(value="newsrMongo")
	private MongoTemplate newsrMongo;

	/**
	 * @Description: 根据iiid删除mongodb的资讯
	 * @param iiid
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月24日 下午6:43:06
	 */
	public long deteleByIiid(int iiid) {
		Query query = new Query(Criteria.where("iiid").is(iiid));
		return newswrMongo.remove(query, MongoNews.class).getN();
	}

	/**
	 * @Description: 导库
	 * @param news	资讯基本数据
	 * @param stockList	资讯相关股票
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月25日 上午11:17:53
	 */
	public MongoNews saveNews(MongoNewsTask task, CmsNews news, List<InfoStock> stockList) {
		MongoNews mongoNews = new MongoNews(news);
		mongoNews.setInfoStocks(stockList);
		
		//	默认字段
		mongoNews.setCtime(new Date());
		mongoNews.setClick(1);
//		mongoNews.setComment(0);
		//	初始评论数改为从接口中获取
		mongoNews.setComment(getComment(news.getPlId()));
		newswrMongo.save(mongoNews);
		return mongoNews;
	}

	/**
	 * @Description: 根据iiid获取mongo中的资讯
	 * @param iiid
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月25日 下午3:16:00
	 */
	public MongoNews getNewsByIiid(int iiid) {
		Query query = new Query(Criteria.where("iiid").is(iiid));
		return newsrMongo.findOne(query, MongoNews.class);
	}

	/**
	 * @Description: 获取mongo中最新一条新闻数据
	 * @return MongoNews
	 * @author yuhai.li
	 * @date 2018年2月7日 下午1:20:05
	 */
	public MongoNews getTopOneByMakeDate() {
		Query query = new Query().limit(1);
		query.with(new Sort(Direction.DESC, "makeDate"));
		return newsrMongo.findOne(query, MongoNews.class);
	}

	/**
	 * @Description: 点击数和推荐状态更新
	 * @param iiid
	 * @param click 点击数
	 * @return int
	 * @author yuhai.li
	 * @date 2018年2月24日 上午10:16:36
	 */
	public int updateClickAndRecommend(int iiid, int click) {
		Query query = new Query(Criteria.where("iiid").is(iiid));
		int recommend = 0;	//	是否推荐，1-是 0-否
        if(click>1000){
        	recommend=1;
        }
		Update update = new Update().set("click", click).set("recommend", recommend);
		return newswrMongo.updateFirst(query, update, MongoNews.class).getN();
	}

	/**
	 * @Description: 更新评论数
	 * @param plId 评论id
	 * @param comment 评论数
	 * @return
	 * @return int
	 * @author yuhai.li
	 * @date 2018年2月24日 上午10:07:18
	 */
	public int updateComment(int plId, int comment) {
		Query query = new Query(Criteria.where("plId").is(plId));
		Update update = new Update().set("comment", comment);
		return newswrMongo.updateFirst(query, update, MongoNews.class).getN();
	}
	
	/**
	 * @Description: 导库
	 * @param news	资讯基本数据
	 * @param stockList	资讯相关股票
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月25日 上午11:17:53
	 */
	public MongoNews saveNews(MongoNews mongoNews) {
		newswrMongo.save(mongoNews);
		return mongoNews;
	}
	
	/**
	 * @Description: 获取评论id对应的评论数
	 * @return Map<String,Integer>
	 * @author yuhai.li
	 * @date 2018年3月5日 上午10:32:27
	 */
	public int getComment(Integer plId){
		if(null==plId){
			log.error("评论id为空");
			return 0;
		}
		log.info("获取评论id：{} 的评论数",plId);
		String content = null;
		JSONObject object;
		try {
			content = HttpUtil.get(Constant.COMMENT_MUL_URL+"&appItemid="+plId);
		} catch (Exception e) {
			log.error("请求评论接口错误：{}，ERROR：{}",plId,e);
		}
		if(null!=content){
			try {
				object = JSON.parseObject(content);
				Integer cnt = object.getInteger(""+plId);
				if(cnt!=null){
					log.info("获取评论id：{} 的评论数为{}",plId, cnt);
					return cnt;
				}
			} catch (Exception e) {
				log.error("评论json数据反序列化失败，content：{}，ERROR：{}",content,e);
			}
		}
		return 0;
	}
	
//	/**
//	 * @Description: 更新新闻
//	 * @param news
//	 * @return void
//	 * @author yuhai.li
//	 * @date 2018年2月24日 上午10:39:37
//	 */
//	public void updateNew(MongoNews news){
//		Query query = new Query(Criteria.where("id").is(news.getId()));
////		DBObject dbObject = DBObjectUtils.dbList(news);
////		Update update = Update.fromDBObject(dbObject, "");
//		
//		Update update = new Update()
//				.set("appInfoUrl", news.getAppInfoUrl())
//				.set("appTop", news.getAppTop())
//				.set("author", news.getAuthor())
//				.set("chanCls", news.getChanCls())
//				.set("chanNum", news.getChanNum())
//				.set("click", news.getClick())
//				.set("comment", news.getComment())
//				.set("ctime", news.getCtime())
//				.set("detail", news.getDetail())
//				.set("iiid", news.getIiid())
//				.set("imgUrl", news.getImgUrl())
//				.set("importentNew", news.getImportentNew())
//				.set("infoCls", news.getInfoCls())
//				.set("infoStocks", news.getInfoStocks())
//				.set("infoUrl", news.getInfoUrl())
//				.set("isBlob", news.getIsBlob())
//				.set("isRed", news.getIsRed())
//				.set("keyword", news.getKeyword())
//				.set("leaderEtte", news.getLeaderEtte())
//				.set("level", news.getLevel())
//				.set("listDate", news.getListDate())
//				.set("makeDate", news.getMakeDate())
//				.set("makeMan", news.getMakeMan())
//				.set("mInfoUrl", news.getMInfoUrl())
//				.set("newsType", news.getNewsType())
//				.set("op", news.getOp())
//				.set("paperMediaSource", news.getPaperMediaSource())
//				.set("path", news.getPath())
//				.set("picThumb", news.getPicThumb())
//				.set("plId", news.getPlId())
//				.set("recommend", news.getRecommend())
//				.set("rollImportentNew", news.getRollImportentNew())
//				.set("title", news.getTitle());
//		
//		newswrMongo.updateFirst(query, update, MongoNews.class);
//	}
	
	
}

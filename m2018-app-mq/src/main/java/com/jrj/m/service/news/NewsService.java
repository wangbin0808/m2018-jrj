/**
 * 
 */
package com.jrj.m.service.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.jrj.m.model.Click;

/**
 * @author tao.wang@jrj.com.cn
 *
 */
@Service
public class NewsService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("newsMongoTemplate")
	private MongoTemplate newsMongoTemplate;

	/**
	 * 禁止使用
	 * @param news
	 */
	public void saveNews(Click news) {
		newsMongoTemplate.save(news);
	}
	/**
	 * 更新iiid对应的文章的点击数
	 * @param iiid
	 * @param total
	 */
	public int updateNewsClick(int iiid,int click){
		log.info("iiid:"+iiid+",click:"+click);
		Query query=new Query(Criteria.where("iiid").is(iiid));
		Update update=new Update().set("click", click);
		return newsMongoTemplate.updateFirst(query, update, "news").getN();
	}
	
	/**
	 * @Description: 更新iiid对应文章的点击数和推荐状态
	 * @param iiid
	 * @param click
	 * @param recommend
	 * @return void
	 */
	public int updateNewsClick(int iiid,int click,int recommend){
		log.info("iiid:"+iiid+",click:"+click);
		Query query=new Query(Criteria.where("iiid").is(iiid));
		Update update=new Update().set("click", click).set("recommend", recommend);
		return newsMongoTemplate.updateFirst(query, update, "news").getN();
	}
	/**
	 * 评论数更新
	 * @param plid
	 * @param total
	 */
	public int updateNewsComment(int plid,int comment){
		log.info("plid:"+plid+",comment:"+comment);
		Query query=new Query(Criteria.where("plId").is(plid));
		Update update=new Update().set("comment", comment);
		return newsMongoTemplate.updateMulti(query, update, "news").getN();
	}
}

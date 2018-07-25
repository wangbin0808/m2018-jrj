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
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;
import cn.com.jrj.m.domain.MongoNews;
import cn.com.jrj.m.utils.Constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

@Service
@Slf4j
public class MongoNewsService {

	@Autowired
	@Qualifier("newsrMongo")
	private MongoTemplate newsrMongo;
	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * @Description: 首页主编推荐新闻
	 * @return MongoNews
	 * @author yuhai.li
	 * @date 2018年2月5日 上午9:14:25
	 */
	public List<MongoNews> getIndexTop() {
		String key = Constant.M_REDIS_KEY_BASE+"index_top";
		List<MongoNews> list = null;
		String value = jedisCluster.get(key);
		if(null!=value){
			log.info("---- key '{}' exists in redis ----",key);
			list = JSON.parseArray(jedisCluster.get(key), MongoNews.class);
		}else{
			log.info("---- key '{}' not exists in redis, get data from mongodb ----",key);
			Query query = new Query(Criteria.where("appTop").is(1)).with(new Sort(Direction.DESC,"listDate"));
			list = newsrMongo.find(query, MongoNews.class);
			jedisCluster.setex(key, Constant.M_REDIS_KEY_CACHE, JSON.toJSONString(list));
		}
		return list;
	}

	/**
	 * @Description: 获取首页推荐新闻
	 * @param date 最后一条新闻的日期参数
	 * @param n 数量
	 * @param d 方向  0-时间往小的方向查 1-时间往大的方向查，默认为0
	 * @return
	 * @return Object
	 * @author yuhai.li
	 * @date 2018年2月6日 上午11:32:53
	 */
	public Object getIndexNews(Date listDate, String id, Integer n, Integer d) {
		List<MongoNews> list;
		String key;
		if(listDate==null){
			key = Constant.M_REDIS_KEY_BASE+"index_null_"+n+"_"+d;
		}else{
			key = Constant.M_REDIS_KEY_BASE+"index_"+listDate.getTime()+"_"+id+"_"+n+"_"+d;
		}
		
		String value = jedisCluster.get(key);
		if(null!=value){
			log.info("---- key '{}' exists in redis ----",key);
			list=JSONArray.parseArray(value, MongoNews.class);
		}else{
			log.info("---- key '{}' not exists in redis, get data from mongodb ----",key);
			
			Criteria criteria = new Criteria();
			criteria.andOperator(
					Criteria.where("recommend").is(1), 
					Criteria.where("appTop").is(0)); // 推荐新闻，除去主编推荐新闻
			if(listDate!=null){	//	日期限制
				if(d==1){
					
					criteria.orOperator(
							Criteria.where("listDate").gt(listDate),
							new Criteria().andOperator(
									Criteria.where("listDate").is(listDate),
									Criteria.where("id").is(id)));
				}else{
					//	db.news.find({"$and":[{"recommend":1},{"appTop":0}]"$or":[{"listDate":{$lt:new Date("2018-02-25T16:00:00.000Z")}},{"$and":[{"listDate":new Date("2018-02-25T16:00:00.000Z")},{"id":{"$lt":"5a9671e9ea3c2b6a51db9972"}}]}]})
					criteria.orOperator(
							Criteria.where("listDate").lt(listDate),
							new Criteria().andOperator(
									Criteria.where("listDate").is(listDate),
									Criteria.where("id").is(id)));
				}
			}
			Query query = new Query(criteria);
			query.with(new Sort(Direction.DESC, "listDate"))
				.with(new Sort(Direction.DESC, "id"))
				.limit(n);	//	排序和limit
			list = newsrMongo.find(query, MongoNews.class);
			jedisCluster.setex(key, Constant.M_REDIS_KEY_CACHE, JSON.toJSONString(list));	//	60s cache
		}
		return list;
	}
	
	
}

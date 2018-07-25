package cn.com.jrj.m.service.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.com.jrj.m.domain.MongoNewsTask;

/**
 * @Description: mongodb-task库操作service
 * @author yuhai.li  
 * @date 2018年1月25日 上午10:55:49
 */
@Service
public class MongoTaskService {

	@Autowired
	@Qualifier("taskwrMongo")
	private MongoTemplate taskwrMongo;

	/**
	 * @Description: 添加新闻触发数据
	 * @param entity
	 * @return MongoNewsTask
	 * @author yuhai.li
	 * @date 2018年1月25日 上午10:03:26
	 */
	public MongoNewsTask save(MongoNewsTask entity) throws Exception {
		taskwrMongo.save(entity);
		return entity;
	}
}

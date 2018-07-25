package cn.com.jrj.m.service.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	@Qualifier(value="taskwrMongo")
	private MongoTemplate taskwrMongo;
	@Autowired
	@Qualifier(value="taskrMongo")
	private MongoTemplate taskrMongo;
	
	/**
	 * @Description: 按iiid倒排取导库任务池数据前num条
	 * @param num
	 * @return List<MongoNewsTask>
	 * @author yuhai.li
	 * @date 2018年1月25日 上午10:55:11
	 */
	public List<MongoNewsTask> getTasks(int num){
		Query query = new Query().with(new Sort(Direction.ASC, "ctime")).limit(num);
		return taskrMongo.find(query, MongoNewsTask.class);
	}

	/**
	 * @Description: 批量添加新闻触发数据
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月25日 上午9:59:28
	 */
	public void insertAll(List<MongoNewsTask> list) {
		taskwrMongo.insertAll(list);
	}

	/**
	 * @Description: 添加新闻触发数据
	 * @param entity
	 * @return MongoNewsTask
	 * @author yuhai.li
	 * @date 2018年1月25日 上午10:03:26
	 */
	public void save(MongoNewsTask entity) {
		taskwrMongo.save(entity);
	}

	/**
	 * @Description: 任务池中任务总数量
	 * @return long
	 * @author yuhai.li
	 * @date 2018年1月25日 上午10:26:20
	 */
	public long count() {
		return taskrMongo.count(new Query(), MongoNewsTask.class);
	}

	/**
	 * @Description: 删除任务池任务
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月25日 上午10:58:27
	 */
	public void deleteById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		taskwrMongo.remove(query, MongoNewsTask.class);
	}
	
}

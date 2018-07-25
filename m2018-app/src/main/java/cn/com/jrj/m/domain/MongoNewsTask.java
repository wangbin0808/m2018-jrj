package cn.com.jrj.m.domain;

import java.util.Date;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description: mongo任务池实体类
 * @author yuhai.li  
 * @date 2018年1月24日 下午5:54:10
 */
@Data
@Document(collection="newstask")
public class MongoNewsTask {	
	@Id
	private String id;
	/**
	 * 操作类型，1-删除 0-新增和更新
	 */
	private int op;
	/**
	 * 资讯iiid
	 */
	private int iiid;
	/**
	 * 操作时间
	 */
	private Date ctime;
}

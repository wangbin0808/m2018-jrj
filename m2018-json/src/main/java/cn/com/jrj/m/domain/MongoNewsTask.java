package cn.com.jrj.m.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @Description: mongo任务池实体类
 * @author yuhai.li  
 * @date 2018年1月24日 下午5:54:10
 */
@Data
@Document(collection="newstask")
public class MongoNewsTask implements Serializable {
	private static final long serialVersionUID = 4167858821204462420L;
	
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

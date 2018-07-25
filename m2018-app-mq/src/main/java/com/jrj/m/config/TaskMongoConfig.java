/**
 * 
 */
package com.jrj.m.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author tao.wang@jrj.com.cn
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.jrj.m.service.task"}, mongoTemplateRef = "taskMongoTemplate")
public class TaskMongoConfig extends AbstractMongoConfig {
	
	@Value("${spring.data.mongodb.task.uri}")
	private String uri;

	@Override
	@Bean(name = "taskMongoTemplate")
	public MongoTemplate getMongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory(),MappingMongoConverter());
	}
	
	@Override
	public String getUri() {
		return uri;
	}	

}

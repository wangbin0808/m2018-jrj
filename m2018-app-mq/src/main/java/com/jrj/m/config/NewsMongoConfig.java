/**
 * 
 */
package com.jrj.m.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author tao.wang@jrj.com.cn
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.jrj.m.service.news"}, mongoTemplateRef = "newsMongoTemplate")
public class NewsMongoConfig extends AbstractMongoConfig {
 
	@Value("${spring.data.mongodb.news.uri}")
	private String uri;
	
	@Override
	@Primary
	@Bean(name = "newsMongoTemplate")
	public MongoTemplate getMongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory(),MappingMongoConverter());
	}

	@Override
	public String getUri() {
		return uri;
	}

}

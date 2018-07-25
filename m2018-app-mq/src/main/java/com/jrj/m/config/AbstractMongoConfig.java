/**
 * 
 */
package com.jrj.m.config;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClientURI;


/**
 * @author tao.wang@jrj.com.cn
 *
 */
public abstract class AbstractMongoConfig {
	 
	public abstract String getUri();
	
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClientURI(getUri()));
	}
	
	public abstract MongoTemplate getMongoTemplate() throws Exception;
	
	public MappingMongoConverter MappingMongoConverter() throws Exception {
		DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(
				this.mongoDbFactory());
		MappingMongoConverter converter = new MappingMongoConverter(
				dbRefResolver, this.mongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return converter;
	}
	public MongoMappingContext mongoMappingContext() {
		MongoMappingContext mappingContext = new MongoMappingContext();
		return mappingContext;
	}
	 
}

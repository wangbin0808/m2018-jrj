package cn.com.jrj.m.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClientURI;

@Configuration
public class MongoConfig {
	
	@Value("${spring.data.mongodb.newsr.uri}")
	private String newsrUri;
	@Value("${spring.data.mongodb.newswr.uri}")
	private String newswrUri;
	@Value("${spring.data.mongodb.taskwr.uri}")
	private String taskwrUri;
	@Value("${spring.data.mongodb.taskr.uri}")
	private String taskrUri;
	
	@Bean  
	public MongoMappingContext mongoMappingContext() {  
		MongoMappingContext mappingContext = new MongoMappingContext();  
		return mappingContext;  
	}
	
	//	读写news库
    @Primary
    public @Bean(name="newswrMongo") MongoTemplate newswrMongo() throws Exception {
    	MongoDbFactory factory = getNewswrFactory();
    	//	设置不序列化 _class 属性
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        
        return new MongoTemplate(factory, converter);
    }
    
    @Primary
    @Bean
    public MongoDbFactory getNewswrFactory() throws Exception {
    	return new SimpleMongoDbFactory(new MongoClientURI(newswrUri));
    }
    
    //	news库读
    public @Bean(name="newsrMongo") MongoTemplate newswrMongoTemplate() throws Exception {
    	
    	MongoDbFactory factory = getNewsrFactory();
    	//	设置不序列化 _class 属性
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        
        return new MongoTemplate(factory, converter);
    }
    
    @Bean
    public MongoDbFactory getNewsrFactory() throws Exception {
    	return new SimpleMongoDbFactory(new MongoClientURI(newsrUri));
    }
    
    //	task库读写
	public @Bean(name = "taskwrMongo") MongoTemplate taskwrMongo() throws Exception {
    	MongoDbFactory factory = getTaskwrFactory();
    	//	设置不序列化 _class 属性
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        
        return new MongoTemplate(factory, converter);
	}
	
    @Bean
    public MongoDbFactory getTaskwrFactory() throws Exception {
    	return new SimpleMongoDbFactory(new MongoClientURI(taskwrUri));
    }
	
    //	task库只读
	public @Bean(name = "taskrMongo") MongoTemplate taskrMongo() throws Exception {
    	MongoDbFactory factory = getTaskrFactory();
    	//	设置不序列化 _class 属性
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        
        return new MongoTemplate(factory, converter);
	}
	
    @Bean
    public MongoDbFactory getTaskrFactory() throws Exception {
    	return new SimpleMongoDbFactory(new MongoClientURI(taskrUri));
    }
    
}

package com.jrj.wx.json.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author bin.wang
 * @date 2018.5.30
 * 这个类是主要配置一些数据库连接的一些信息
 *
 */
@Slf4j
@Configuration
public class DataSourceConfig {
	
	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	//初始化大小
	@Value("${spring.datasource.initialSize}")
	private int initialSize;
	//最小闲置时间
	@Value("${spring.datasource.minIdle}")
	private int minIdle;
	//最大连接数
	@Value("${spring.datasource.maxActive}")
	private int maxActive;
	//最大等待时间
	@Value("${spring.datasource.maxWait}")
	private int maxWait;
	
	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	
	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;
	
	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;
	
	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;
	
	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	
	@Value("${spring.datasource.filters}")
	private String filters;

	@Bean
	@Primary
	public DataSource druidDataSource() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(this.dbUrl);
		datasource.setUsername(this.username);
		datasource.setPassword(this.password);
		datasource.setDriverClassName(this.driverClassName);
		datasource.setInitialSize(this.initialSize);
		datasource.setMinIdle(this.minIdle);
		datasource.setMaxActive(this.maxActive);
		datasource.setMaxWait(this.maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
		datasource.setValidationQuery(this.validationQuery);
		datasource.setTestWhileIdle(this.testWhileIdle);
		datasource.setTestOnBorrow(this.testOnBorrow);
		datasource.setTestOnReturn(this.testOnReturn);
		datasource.setPoolPreparedStatements(this.poolPreparedStatements);
		try {
			datasource.setFilters(this.filters);
		} catch (SQLException e) {
			log.debug("------数据库连接失败" + e.getMessage());
			System.out.println("------数据库连接失败" + e.getMessage());
		}
		log.debug("------数据源信息是MaxActive:" + datasource.getMaxActive() + "initialSize:" + datasource.getInitialSize()
		+ "minIdle:" + datasource.getMinIdle() + ":maxWait" + datasource.getMaxWait());
		System.out.println(
				"------数据源信息是MaxActive:" + datasource.getMaxActive() + "initialSize:" + datasource.getInitialSize()
						+ "minIdle:" + datasource.getMinIdle() + ":maxWait" + datasource.getMaxWait());
		return datasource;
	}
}

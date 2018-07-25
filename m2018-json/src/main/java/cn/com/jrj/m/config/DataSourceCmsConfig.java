package cn.com.jrj.m.config;

import cn.com.jrj.m.config.DruidConfigBean;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.sql.SQLException;

/**
 * @Description: 新闻库数据源，主数据源
 * @author yuhai.li  
 * @date 2018年1月19日 下午2:32:45
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(DruidConfigBean.class)
@MapperScan(basePackages = DataSourceCmsConfig.PACKAGE, sqlSessionFactoryRef  = "cmsSqlSessionFactory")
public class DataSourceCmsConfig {

    //精确到cms目录 ，以便跟其他数据源隔离
    static final String PACKAGE = "cn.com.jrj.m.dao.cms";
    static final String MAPPER_LOCATION = "classpath*:cn/com/jrj/m/mapper/cms/*.xml";
	
    @Autowired
    private DruidConfigBean druidConfigBean;
    
    @Value("${spring.datasource.cms.url}")
    private String url;

    @Value("${spring.datasource.cms.username}")
    private String user;

    @Value("${spring.datasource.cms.password}")
    private String password;

    @Value("${spring.datasource.cms.driver-class-name}")
    private String driverClass;
    
    /**
     * cms数据源
     * @return
     */
    @Bean(name = "cmsDataSource")
    @Primary
    public DataSource cmsDataSource(){
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setDriverClassName(driverClass);
        datasource.setUsername(user);
        datasource.setPassword(password);

        //配置初始化大小、最小、最大
        datasource.setInitialSize(druidConfigBean.getInitialSize());
        datasource.setMinIdle(druidConfigBean.getMinIdle());
        datasource.setMaxActive(druidConfigBean.getMaxActive());

//  		//配置获取连接等待超时的时间
//  		datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("maxWait")));
//  
//  		//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
//  		datasource.setTimeBetweenEvictionRunsMillis(
//  				Long.valueOf(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
//  		
//  		//配置一个连接在池中最小生存的时间，单位是毫秒
//  		datasource.setMinEvictableIdleTimeMillis(
//  				Long.valueOf(propertyResolver.getProperty("minEvictableIdleTimeMillis")));

        datasource.setValidationQuery(druidConfigBean.getValidationQuery());
        datasource.setTestWhileIdle(druidConfigBean.getTestWhileIdle());
        datasource.setTestOnBorrow(druidConfigBean.getTestOnBorrow());
        datasource.setTestOnReturn(druidConfigBean.getTestOnReturn());
        
//  		//打开PSCache，并且指定每个连接上PSCache的大小
//  		datasource
//  				.setPoolPreparedStatements(Boolean.getBoolean(propertyResolver.getProperty("poolPreparedStatements")));
//  		datasource.setMaxPoolPreparedStatementPerConnectionSize(
//  				Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));

        //配置监控统计拦截的filters
        try {
            datasource.setFilters(druidConfigBean.getFilters());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        datasource.setConnectionProperties(druidConfigBean.getConnectionProperties());
        return datasource;
    }
    
    @Bean(name = "cmsTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(){
        return new DataSourceTransactionManager(cmsDataSource());
    }

    @Bean(name = "cmsSqlSessionFactory")
    @Primary
    public SqlSessionFactory cmsSqlSessionFactory(@Qualifier("cmsDataSource")DataSource cmsDataSource)throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(cmsDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION)); 
        return sqlSessionFactoryBean.getObject();
    }
    
//    @Bean
//    public DataSource dataSource() {
//        DruidDataSource datasource = new DruidDataSource();
//        // 基本属性 url、user、password
//        datasource.setUrl(druidConfigBean.getUrl());
//        datasource.setDriverClassName(druidConfigBean.getDriver_class_name());
//        datasource.setUsername(druidConfigBean.getUsername());
//        datasource.setPassword(druidConfigBean.getPassword());
//
//        //配置初始化大小、最小、最大
//        datasource.setInitialSize(druidConfigBean.getInitialSize());
//        datasource.setMinIdle(druidConfigBean.getMinIdle());
//        datasource.setMaxActive(druidConfigBean.getMaxActive());
//
////		//配置获取连接等待超时的时间
////		datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("maxWait")));
////
////		//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
////		datasource.setTimeBetweenEvictionRunsMillis(
////				Long.valueOf(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
////		
////		//配置一个连接在池中最小生存的时间，单位是毫秒
////		datasource.setMinEvictableIdleTimeMillis(
////				Long.valueOf(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
////
//        datasource.setValidationQuery(druidConfigBean.getValidationQuery());
//        datasource.setTestWhileIdle(druidConfigBean.getTestWhileIdle());
//        datasource.setTestOnBorrow(druidConfigBean.getTestOnBorrow());
//        datasource.setTestOnReturn(druidConfigBean.getTestOnReturn());
////
////		//打开PSCache，并且指定每个连接上PSCache的大小
////		datasource
////				.setPoolPreparedStatements(Boolean.getBoolean(propertyResolver.getProperty("poolPreparedStatements")));
////		datasource.setMaxPoolPreparedStatementPerConnectionSize(
////				Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));
//
//        //配置监控统计拦截的filters
//        try {
//            datasource.setFilters(druidConfigBean.getFilters());
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//        datasource.setConnectionProperties(druidConfigBean.getConnectionProperties());
//
//        return datasource;
//    }
    
}
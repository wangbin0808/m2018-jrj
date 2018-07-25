package cn.com.jrj.m.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: druid连接池相关配置
 * @author yuhai.li  
 * @date 2018年1月19日 下午2:33:53
 */
@ConfigurationProperties(prefix="druid.datasource")
@Data
@Component
public class DruidConfigBean {
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private String validationQuery;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private String filters;
    private String connectionProperties;
}

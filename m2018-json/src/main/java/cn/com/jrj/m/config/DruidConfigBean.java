package cn.com.jrj.m.config;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @Description: druid连接池相关配置
 * @author yuhai.li  
 * @date 2018年1月19日 下午2:33:53
 */
@ConfigurationProperties(prefix="druid.datasource")
@Validated
@Data
@Component
public class DruidConfigBean {
    @NotNull
    private Integer initialSize;
    @NotNull
    private Integer minIdle;
    @NotNull
    private Integer maxActive;
    @NotNull
    private String validationQuery;
    @NotNull
    private Boolean testWhileIdle;
    @NotNull
    private Boolean testOnBorrow;
    @NotNull
    private Boolean testOnReturn;
    @NotNull
    private String filters;
    @NotNull
    private String connectionProperties;
}

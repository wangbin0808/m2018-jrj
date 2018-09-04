package com.jrj.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 
 * @description M站A股头条-付费版-Application
 * @author qiushun.sun 
 * @date 2018.03.24
 *
 */
@MapperScan("com.jrj.pay.dao")
@SpringBootApplication
public class M2018JsonPayApplication {
	
	// 设置为允许ajax全局跨域请求
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(M2018JsonPayApplication.class, args);
	}
}

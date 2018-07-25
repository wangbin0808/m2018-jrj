package com.jrj.wx.json;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jrj.wx.json.config.ApplicationStartup;
/**
 * 
 * @author bin.wang
 * @date 2018.5.29
 * 蒙牛微信公众号的接口启动类
 *
 */
@MapperScan({"com.jrj.wx.json.dao"})
@SpringBootApplication
@EnableScheduling
public class Wx2018JsonApplication {

	public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Wx2018JsonApplication.class);
        springApplication.addListeners(new ApplicationStartup());//添加监听器
        springApplication.run(args);
	}
}

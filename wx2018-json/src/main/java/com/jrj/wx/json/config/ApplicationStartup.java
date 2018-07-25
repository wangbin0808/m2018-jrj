package com.jrj.wx.json.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.jrj.wx.json.Scheduling.PrizePoolScheduling;
import com.jrj.wx.json.util.RSAUtil;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author bin.wang
 * @date 2018.6.1
 * 这个类主要是在程序启动的时候就初始化
 *
 */
@Slf4j
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent>{

	@SuppressWarnings("static-access")
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		RSAUtil rsaUtil = event.getApplicationContext().getBean(RSAUtil.class);
		try {
			log.debug("----------------初始化生成私钥和公钥-----------");
			System.out.println("----------------生成私钥和公钥-----------");
			rsaUtil.generateKeyPair();
			System.out.println("----------------生成私钥和公钥-----------");
			log.debug("----------------完成生成私钥和公钥-----------");
		} catch (Exception e) {
			log.error("生成私钥和公钥失败"+e.getMessage());
			System.out.println("生成私钥和公钥失败");
			e.printStackTrace();
		}
		PrizePoolScheduling poolScheduling = event.getApplicationContext().getBean(PrizePoolScheduling.class);
		log.debug("----------------初始化奖品池-----------");
		poolScheduling.initPrizePool();
	}

}

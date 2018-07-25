package com.jrj.wx.json.Scheduling;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jrj.wx.json.bean.PrizePool;
import com.jrj.wx.json.dao.LuckDrawDao;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author bin.wang
 * 这个类主要是往奖品池里面存放奖品的
 *
 */
@Slf4j
@Service
public class PrizePoolScheduling {
	//key 1、2、3分别表示一二三等奖，value表示奖品的数量
	private Map<Integer, Integer> prizePool = new ConcurrentHashMap<Integer, Integer>(16);

	@Autowired
	private LuckDrawDao luckDrawDao;
	/**
	 * 项目启动的时候调动这个方法初始化奖品池
	 */
	public void initPrizePool(){
		List<PrizePool> prize=luckDrawDao.getPrizePool();
		if(prize==null || prize.size()==0){//当项目第一次启动的时候数据库还没有数据
			for (int i = 0; i < 3; i++) {
				luckDrawDao.initPrize(i+1,0);
			}
			this.prizePool.put(1, 0);
			this.prizePool.put(2, 0);
			this.prizePool.put(3, 0);
		}
		for (PrizePool prizeP : prize) {
			if(prizeP.getPrizelevel()==1){
				prizePool.put(1, prizeP.getCurrentnum());
			}else if(prizeP.getPrizelevel()==2){
				prizePool.put(2, prizeP.getCurrentnum());
			}else if(prizeP.getPrizelevel()==3){
				prizePool.put(3, prizeP.getCurrentnum());
			}
		}
	}

	/**
	 * 0 01 0 17,21,25,29 JUN ? 这个表示6月的17,21,25,29号的00:01都会触发这个
	 */
	@Scheduled(cron = "0 01 0 0/1 JUN ?")
	public void setFristPrizePool() {
		System.out.println(new Date());
		log.debug("---setFristPrizePool---updatePrizePool--key:{},num:{}",1,1);
		luckDrawDao.updatePrizePool(1, 1);
		this.prizePool.put(1,  getPrizePool(1)  + 1);
	}

	/**
	 * 0 01 0 3,7,11,15 JUL ? 这个表示7月的3,7,11,15号的00:01都会触发这个
	 */
	@Scheduled(cron = "0 01 12 0/1 JUN ?")
	public void setFristPrizePoolJUL() {
		System.out.println(new Date());
		log.debug("----setFristPrizePoolJUL---updatePrizePool--key:{},num:{}",1,1);
		luckDrawDao.updatePrizePool(1, 1);
		this.prizePool.put(1, getPrizePool(1) + 1);
	}

	/**
	 * 0 01 0 0/1 * ? 表示 每月的每一天的00:01分钟都会触发
	 */
	@Scheduled(cron = "0 01 0,12 0/1 * ?")
	public void setSecondPrizePool() {
		log.debug("----setSecondPrizePool--updatePrizePool--key:{},num:{}",2,2);
		luckDrawDao.updatePrizePool(2, 2);
		this.prizePool.put(Integer.valueOf(2),  getPrizePool(2)  + 2);
	}

	/**
	 * 0 01 0 0/1 * ? 表示 每月的每一天的00:01分钟都会触发
	 */
	@Scheduled(cron = "0 01 0,12 0/1 * ?")
	public void setThreePrizePool() {
		log.debug("----setThreePrizePool--updatePrizePool--key:{},num:{}",3,4);
		luckDrawDao.updatePrizePool(3, 4);
		this.prizePool.put(3,  getPrizePool(3) + 4);
	}

	/**
	 * 这个表示6月的6-13号的23:30都会触发这个
	 */
	@Scheduled(cron = "0 30 23 6-13 JUN ?")
	public void setPrizePool() {
		log.debug("---setPrizePool---"+new Date());
		this.prizePool.put(1, 0);
		this.prizePool.put(2, 0);
		this.prizePool.put(3, 0);
		luckDrawDao.updatePrizePool(1, 0);
		luckDrawDao.updatePrizePool(2, 0);
		luckDrawDao.updatePrizePool(3, 0);
	}

	public Integer getPrizePool(Integer key) {
		log.debug("---getPrizePool---"+new Date());
		return (Integer) this.prizePool.get(key);
	}

	public void setPrizePool(Integer key, Integer prize) {
		log.debug("----setPrizePool--updatePrizePool--key:{},num:{}",key,prize);
		luckDrawDao.updatePrizePool(key, prize);
		this.prizePool.put(key, prize);
	}
}

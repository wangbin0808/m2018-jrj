package com.jrj.wx.json.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrj.wx.json.Scheduling.PrizePoolScheduling;
import com.jrj.wx.json.bean.User;
import com.jrj.wx.json.dao.LuckDrawDao;
import com.jrj.wx.json.service.LuckDrawService;
import com.jrj.wx.json.util.HexUtil;
import com.jrj.wx.json.util.RSAUtil;
/**
 * 
 * @author bin.wang
 * @date 2018.6.1
 *
 */
@Service
public class LuckDrawServiceImpl implements LuckDrawService {
	
	private static final Logger log = LoggerFactory.getLogger(LuckDrawServiceImpl.class);

	@Autowired
	private PrizePoolScheduling prizePoolScheduling;
	
	@Autowired
	private LuckDrawDao luckDrawDao;

	/**
	 * 返回是否中奖,抽奖的规则
	 */
	public String drawRules() {
		Integer in1 = this.prizePoolScheduling.getPrizePool(Integer.valueOf(1));
		Integer in2 = this.prizePoolScheduling.getPrizePool(Integer.valueOf(2));
		Integer in3 = this.prizePoolScheduling.getPrizePool(Integer.valueOf(3));
		log.debug("一等奖：" + in1 + "二等奖：" + in2 + "三等奖：" + in3);
		if ((in1.intValue() == 0) && (in2.intValue() == 0) && (in3.intValue() == 0)) {
			return "false";
		}
		log.debug("----------------------------开始抽奖--------------------------");
		int num = (int) Math.floor(Math.random() * 1000.0D);
		System.out.println("----------------num:" + num);
		log.debug("----------------num:" + num);
		if (num == 1) {
			if (in1.intValue() <= 0) {
				this.prizePoolScheduling.setPrizePool(Integer.valueOf(1), Integer.valueOf(0));
				luckDrawDao.updatePrizePool(1, 0);
				return "false";
			}
			this.prizePoolScheduling.setPrizePool(Integer.valueOf(1),
					Integer.valueOf(this.prizePoolScheduling.getPrizePool(Integer.valueOf(1)).intValue() - 1));
			luckDrawDao.updatePrizePool(1, prizePoolScheduling.getPrizePool(1)-1);
			return "frist";
		}
		if (num % 250 == 0) {
			if (in2.intValue() <= 0) {
				this.prizePoolScheduling.setPrizePool(Integer.valueOf(2), Integer.valueOf(0));
				luckDrawDao.updatePrizePool(2, 0);
				return "false";
			}
			this.prizePoolScheduling.setPrizePool(Integer.valueOf(1),
					Integer.valueOf(this.prizePoolScheduling.getPrizePool(Integer.valueOf(2)).intValue() - 1));
			luckDrawDao.updatePrizePool(2, prizePoolScheduling.getPrizePool(2)-1);
			return "second";
		}
		if (num % 200 == 0) {
			if (in3.intValue() <= 0) {
				this.prizePoolScheduling.setPrizePool(Integer.valueOf(3), Integer.valueOf(0));
				luckDrawDao.updatePrizePool(3,0);
				return "false";
			}
			this.prizePoolScheduling.setPrizePool(Integer.valueOf(1),
					Integer.valueOf(this.prizePoolScheduling.getPrizePool(Integer.valueOf(3)).intValue() - 1));
			luckDrawDao.updatePrizePool(3, prizePoolScheduling.getPrizePool(3)-1);
			return "three";
		}
		return "false";
	}

	/**
	 * code 901表示一等奖 ，902二等奖，903三等奖
	 * phoneNum 电话号码
	 */
	public String addUser(String code, String phoneNum) {
		User user = this.luckDrawDao.getUserByPhoneNum(HexUtil.encode(phoneNum.getBytes()));
		System.out.println("手机号：phoneNum" + phoneNum + "加密后的:" + HexUtil.encode(phoneNum.getBytes()));
		log.debug("手机号：phoneNum" + phoneNum + "加密后的:" + HexUtil.encode(phoneNum.getBytes()));
		String cod = RSAUtil.decryptPrivate(code);
		log.debug("解密之前的--code:{},解密之后的值cod:{}",code,cod);
		if (user == null) {
			User use = new User();
			use.setPhonenum(HexUtil.encode(phoneNum.getBytes()));
			if ("901".equals(cod)) {
				use.setGrandprize(Integer.valueOf(1));
			} else if ("902".equals(cod)) {
				use.setGrandprize(Integer.valueOf(2));
			} else if ("903".equals(cod)) {
				use.setGrandprize(Integer.valueOf(3));
			}else{
				log.debug("解密和加密有问题吗？");
				return "no";
			}
			use.setCreatetime(new Date());
			log.debug("添加中奖的人use:"+use);
			this.luckDrawDao.addUser(use);
			return "ok";
		}
		return "no";
	}

	/**
	 * code 901表示查找一等奖 ，902查找二等奖，903查找三等奖，906表示查找所有中奖
	 */
	public List<User> getUserDraw(String code) {
		int grandprize = 0;
		if ("901".equals(code)) {
			grandprize = 1;
		} else if ("902".equals(code)) {
			grandprize = 2;
		} else if ("903".equals(code)) {
			grandprize = 3;
		} else if ("906".equals(code)) {
			grandprize = 0;
		}
		List<User> user = null;
		if (grandprize == 0) {
			log.debug("----查询所有中奖用户");
			user = this.luckDrawDao.getAllUser();
		} else {
			log.debug("----查询中奖用户" + grandprize + ",code:" + code);
			user = this.luckDrawDao.getUserDraw(grandprize);
		}
		List<User> us = new ArrayList<User>();
		for (User user2 : user) {
			user2.setPhonenum(new String(HexUtil.decode(user2.getPhonenum())));
			us.add(user2);
			log.debug("--" + user2);
			System.out.println(user2);
		}
		return user;
	}
}

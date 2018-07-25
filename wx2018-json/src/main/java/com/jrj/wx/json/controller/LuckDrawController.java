package com.jrj.wx.json.controller;

import com.jrj.wx.json.bean.Result;
import com.jrj.wx.json.bean.User;
import com.jrj.wx.json.service.LuckDrawService;
import com.jrj.wx.json.util.RSAUtil;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author bin.wang
 * @date 2018.5.31
 * 这个类主要提供抽奖接口、保存用户、查找中奖的用户
 *
 */
@RestController
@RequestMapping("/mnwx")
public class LuckDrawController {
	
	private static final Logger log = LoggerFactory.getLogger(LuckDrawController.class);
	
	@Autowired
	private LuckDrawService luckDrawService;
	/**
	 * 不需要传参
	 * @return 返回是否中奖--code 900-没有中奖 901表示一等奖 ，902二等奖，903三等奖
	 */
	@RequestMapping("/draw")
	public Result drawPrize() {
		String reult = this.luckDrawService.drawRules();
		Result result = RSAUtil.getPublicKeyExponentAndModulus();
		log.debug("------抽奖结束------抽奖的结果reult:{}", reult);
		if ("false".equals(reult)) {
			return new Result("900", "没有中抽奖");
		}else  if ("frist".equals(reult)) {
			return new Result("901", "抽到了一等奖",result.getExponent(),result.getModulus());
		}else if ("second".equals(reult)) {
			return new Result("902", "抽到了二等奖",result.getExponent(),result.getModulus());
		}else if ("three".equals(reult)) {
			return new Result("903", "抽到了三等奖",result.getExponent(),result.getModulus());
		}
		return new Result("900", "没有中抽奖");
	}
	/**
	 * 这个接口是调试用的
	 * @param code
	 * @return
	 */
	@RequestMapping("/test")
	public Result testDrawPrize(String code) {
		if("901".equals(code)){
			return new Result("901", "抽到了一等奖");
		}else if("902".equals(code)){
			return new Result("902", "抽到了二等奖");
		}
		return new Result("903", "抽到了三等奖");
	}
	
	/**
	 * 
	 * @param code 901表示一等奖 ，902二等奖，903三等奖
	 * @param phoneNum 电话号码
	 * @return
	 */
	@RequestMapping("/drawPhoneNum")
	public Result addUser(String code, String phoneNum) {
		log.debug("code:" + code + ",phoneNum:" + phoneNum);
		if(code.trim().getBytes().length>8){
			code=code.substring(5, 8);
		}else{
			return new Result("907", "code参数传的有问题");
		}
		log.debug("code:" + code + ",phoneNum:" + phoneNum);
		String reult = this.luckDrawService.addUser(code, phoneNum);
		if ("no".equals(reult)) {
			return new Result("904", "这个手机号已经中过奖了，不能重复中奖");
		}
		return new Result("905", "ok");
	}
	
	/**
	 * 
	 * @param code 901表示查找一等奖 ，902查找二等奖，903查找三等奖，906表示查找所有中奖
	 * @return
	 */
	@RequestMapping({ "/user" })
	public Object getUserDraw(String code) {
		List<User> user = this.luckDrawService.getUserDraw(code);
		return user;
	}
}

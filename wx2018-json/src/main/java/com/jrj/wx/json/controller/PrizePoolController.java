package com.jrj.wx.json.controller;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrj.wx.json.Scheduling.PrizePoolScheduling;
import com.jrj.wx.json.util.RSAUtil;
/**
 * 
 * @author bin.wang
 * @date 2018.6.1
 * 这个类主要提供一些对奖池的一些操作接口
 *
 */
@RestController
@RequestMapping("/mnwx/prize")
public class PrizePoolController {
	
	@Autowired
	private PrizePoolScheduling prizePoolScheduling;

	/**
	 * 这个接口是查询出奖品池里面的数量
	 * @return
	 */
	@RequestMapping("/prize")
	public Object prize() {
		Integer in1 = this.prizePoolScheduling.getPrizePool(Integer.valueOf(1));
		Integer in2 = this.prizePoolScheduling.getPrizePool(Integer.valueOf(2));
		Integer in3 = this.prizePoolScheduling.getPrizePool(Integer.valueOf(3));
		return "一等奖:" + in1 + "二等奖:" + in2 + "三等奖:" + in3 + "------奖品总和为："
				+ (in1.intValue() + in2.intValue() + in3.intValue());
	}

	/**
	 * 
	 * @param key 1,2,3表示一、二、三等奖,这个key只能传1,2,3这几个值
	 * @return
	 */
	@RequestMapping("/addPrize")
	public Object addPrize(Integer key) {
		if (key.intValue() == 1) {
			this.prizePoolScheduling.setFristPrizePool();
		} else if (key.intValue() == 2) {
			this.prizePoolScheduling.setSecondPrizePool();
		} else if (key.intValue() == 3) {
			this.prizePoolScheduling.setThreePrizePool();
		}
		return "添加成功" + key;
	}
	
	/**
	 * 
	 * @param key 1,2,3表示一、二、三等奖
	 * @param prize 这个表示要修改成的奖品数
	 * @return
	 */
	@RequestMapping("/updatePrize")
	public Object updatePrize(Integer key, Integer prize) {
		this.prizePoolScheduling.setPrizePool(key, prize);
		return "修改成功" + key;
	}
	
	/**
	 * 获得公钥的模数和指数，这个需要发给前端
	 * @return
	 */
	@RequestMapping("/key")
	public Object key() {
		PublicKey publicKey =null;
		try {
			publicKey = RSAUtil.getKeyPair().getPublic();
		} catch (Exception e) {
			
		}
		BigInteger modulus = ((RSAKey) publicKey).getModulus();
		BigInteger exponent = ((RSAPublicKey) publicKey).getPublicExponent();
		System.out.println(publicKey+"-----------------");
		String str = new BigInteger(""+modulus, 10).toString(16);
		String str1 = new BigInteger(""+exponent, 10).toString(16);
		System.out.println("exponent:"+str1);
		System.out.println("modulus:"+str);
		return "exponent:"+str1+",modulus:"+str;
	}
}

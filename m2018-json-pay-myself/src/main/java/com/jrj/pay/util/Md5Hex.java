package com.jrj.pay.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Hex {
	/**
	 * 对字符进行MD5加密
	 * @param sStr
	 * @return
	 */
	public static String getDigestMD5(String sStr){
		String md5 = DigestUtils.md5Hex(sStr);
		return md5;
	}
}

package com.jrj.wx.json.bean;
/**
 * 
 * @author bin.wang
 * @date 2018.5.30
 * 这个类只是一个返回前端状态的一个辅助类
 *
 */
public class Result {
	private String code;//返回的状态码
	private String message;//返回的信息
	
	private String exponent;//非对称加密的指数
	
	private String modulus;//非对称加密的模数

	public Result(String code, String message, String exponent, String modulus) {
		super();
		this.code = code;
		this.message = message;
		this.exponent = exponent;
		this.modulus = modulus;
	}

	public String getExponent() {
		return exponent;
	}

	public void setExponent(String exponent) {
		this.exponent = exponent;
	}

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return "Result [code=" + this.code + ", message=" + this.message + "]";
	}

	public Result(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result() {
	}
}

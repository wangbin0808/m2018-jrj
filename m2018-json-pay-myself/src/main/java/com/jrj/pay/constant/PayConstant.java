package com.jrj.pay.constant;

/**
 * @description 登录的常量类
 * @author qiushun.sun
 * @date 2018.03.24
 */
public abstract class PayConstant {

	public static final int PAGE_SIZE = 20;
	public static final String VERIFY_INTERFACE_URL = "http://level2user.jrjc.local";
	public static final String CMS_DATA_URL = "http://cmsdata.jrjc.local/outdata/getcontentxml.jsp";
	public static final String BUY_LIST_URL = "http://cashier.jrjc.local/order/listByUidAndProductId";
	public static final String ADD_ZERO_URL = "http://cashier.jrjc.local/order/addZero";
	public static final String ORDER_RESULT_URL = "http://cashier.jrjc.local/order/resultquery";
	
	public final static String LOGIN_COOKIE_KEY = "login";
	public final static String LOGIN_COOKIE_OK = "ok"; 
	public final static String LOGIN_COOKIE_USERID = "myjrj_userid";
	
	public final static String LOGIN_COOKIE_JRJ_SSOPASSPORT="JRJ.SSOPASSPORT";
	public final static String LOGIN_COOKIE_JRJ_MYJRJ_MD5="myjrj_md5";
	
}

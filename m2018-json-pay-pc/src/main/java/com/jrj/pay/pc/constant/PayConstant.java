package com.jrj.pay.pc.constant;

/**
 * @description 登录的常量类
 * @author bin.wang
 * @date 2018.06.20
 */
public abstract class PayConstant {

	public static final int PAGE_SIZE = 20;
	public static final String VERIFY_INTERFACE_URL = "http://level2user.jrjc.local";
	public static final String CMS_DATA_URL = "http://cmsdata.jrjc.local/outdata/getcontentxml.jsp";
	public static final String BUY_LIST_URL = "http://cashier.jrjc.local/order/listByUidAndProductId";
	public static final String ADD_ZERO_URL = "http://cashier.jrjc.local/order/addZero";
	public static final String ORDER_RESULT_URL = "http://cashier.jrjc.local/order/resultquery";
	//获得用户状态
	public static final String USER_INFO_STATE_URL="http://sso.jrjc.local/sso/passport/userInfoState";
	//风测接口
	public static final String LATE_ASSESSMENT_INFO_URL="http://sso.jrjc.local/sso/riskAssessment/getLatestAssessmentInfo";
	public final static String LOGIN_COOKIE_KEY = "login";
	public final static String LOGIN_COOKIE_OK = "ok"; 
	public final static String LOGIN_COOKIE_USERID = "myjrj_userid";
	
}

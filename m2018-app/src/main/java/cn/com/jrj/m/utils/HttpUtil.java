package cn.com.jrj.m.utils;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Slf4j
public class HttpUtil {
	
	/**
	 * @Description: get获取请求内容
	 * @param url
	 * @return String
	 * @author yuhai.li
	 * @date 2018年1月26日 下午3:49:50
	 */
	public static String get(String url){
		String content = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
		    System.out.println(response.getStatusLine());
		    if(response.getStatusLine().getStatusCode()==200){	//	成功请求
		    	log.debug("请求成功，url："+url);
			    HttpEntity entity = response.getEntity();
			    content = EntityUtils.toString(entity);
			    log.debug("返回数据："+content);
			    response.close();
		    }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return content;
	}
	
}

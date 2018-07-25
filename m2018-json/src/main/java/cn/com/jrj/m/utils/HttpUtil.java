package cn.com.jrj.m.utils;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
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
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
		    System.out.println(response.getStatusLine());
		    if(response.getStatusLine().getStatusCode()==200){	//	成功请求
		    	log.debug("请求成功，url："+url);
			    HttpEntity entity = response.getEntity();
			    content = EntityUtils.toString(entity);
			    log.debug("返回数据："+content);
//			    response.close();
//			    EntityUtils.consume(entity);
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
	
	/**
	 * @Description: 获取内容
	 * @param url url
	 * @param encode 编码
	 * @param timeout 超时时间
	 * @return String
	 * @author yuhai.li
	 * @date 2018年1月26日 下午3:51:17
	 */
	public static String get(String url, String encode, int timeout){
		String content = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
//		RequestConfig.custom().setConnectTimeout(timeout).build();
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
	
	
	public static void main(String[] args){
		get("http://cmsdata.jrj.com.cn/outdata/getspecitemxml.jsp?chnmbclsid=104002004");
	}
	
	
//	
//	public void test(){
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpGet httpGet = new HttpGet("http://targethost/homepage");
//		CloseableHttpResponse response1 = httpclient.execute(httpGet);
//		// The underlying HTTP connection is still held by the response object
//		// to allow the response content to be streamed directly from the network socket.
//		// In order to ensure correct deallocation of system resources
//		// the user MUST call CloseableHttpResponse#close() from a finally clause.
//		// Please note that if response content is not fully consumed the underlying
//		// connection cannot be safely re-used and will be shut down and discarded
//		// by the connection manager. 
//		try {
//		    System.out.println(response1.getStatusLine());
//		    HttpEntity entity1 = response1.getEntity();
//		    // do something useful with the response body
//		    // and ensure it is fully consumed
//		    EntityUtils.consume(entity1);
//		} finally {
//		    response1.close();
//		}
//
//		HttpPost httpPost = new HttpPost("http://targethost/login");
//		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//		nvps.add(new BasicNameValuePair("username", "vip"));
//		nvps.add(new BasicNameValuePair("password", "secret"));
//		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//		CloseableHttpResponse response2 = httpclient.execute(httpPost);
//
//		try {
//		    System.out.println(response2.getStatusLine());
//		    HttpEntity entity2 = response2.getEntity();
//		    // do something useful with the response body
//		    // and ensure it is fully consumed
//		    EntityUtils.consume(entity2);
//		} finally {
//		    response2.close();
//		}
//	}
}

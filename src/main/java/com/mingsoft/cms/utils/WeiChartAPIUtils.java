package com.mingsoft.cms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author YueBin
 * @create 2016年7月7日
 * @version 
 * 说明:<br />
 * 微信工具类
 * 
 */
public class WeiChartAPIUtils {
	
	
	private static Logger logger=LoggerFactory.getLogger("com.am.frame.weichart.util.WeiChartAPIUtil");
	
	/** 调试日志输出，true输出，false不输出，在开发期间设置为true **/
	private static String tag = "Utils";

	/**
	 * 微信获取时间戳，微信时间戳需要除以1000，微信坑爹的地方在这儿
	 * @return
	 */
	public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
	
	public static String SHA1(String decript) {
		return DigestUtils.sha1Hex(decript);
	}

		/**
		  * 通过静默方式获取openid等信息，格式为<br>
		  * {
		  * "access_token":"ACCESS_TOKEN",网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同<br>
		  * "expires_in":7200,access_token接口调用凭证超时时间，单位（秒）<br>
		  * "refresh_token":"REFRESH_TOKEN",用户刷新access_token<br>
		  * "openid":"OPENID",用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID<br>
		  * "scope":"SCOPE",用户授权的作用域，使用逗号（,）分隔<br>
		  * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段.<br>
		  * }
		  * @param code
		  * @return  
		  */
		public static JSONObject getSnsapiBaseInfoByCode(String code){
			JSONObject result=new JSONObject();
			String url="https://api.weixin.qq.com/sns/oauth2/access_token?"+
					"appid="+"wxa49ce6d918db2be8"+
					"&secret="+"4582fca49524a2ba46daa985605ed52f"+
					"&code="+code+
					"&grant_type=authorization_code";
			result=getHttpsURL(url);
			
			return result;
		}

	public static JSONObject getUserInfoByOpenId(String access_token, String openid){
		JSONObject result=new JSONObject();
		String url="https://api.weixin.qq.com/sns/userinfo?"+
				"access_token="+access_token+
				"&openid="+openid+
				"&lang=zh_CN";
		result=getHttpsURL(url);

		return result;
	}

		/**
		 * 获取HTTPGET请求结果
		 * 
		 * @param url
		 * @return
		 */
		public static JSONObject getHttpsURL(String url) {
			JSONObject result =new JSONObject();
			try {
				CloseableHttpClient httpClient =SSLClient.createSSLClientDefault();
				RequestConfig reqConfig = RequestConfig.custom()
						.setSocketTimeout(1000)
						.setConnectTimeout(1000)
						.build();
				HttpGet httpGet = new HttpGet(url);
				httpGet.setConfig(reqConfig);
				CloseableHttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					long len = entity.getContentLength();
					if (len != -1) {
						result = new JSONObject(EntityUtils.toString(entity,
								Charset.forName("UTF-8")));
					}
				}
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info(tag+result);
			
			return result;
		}
		
}

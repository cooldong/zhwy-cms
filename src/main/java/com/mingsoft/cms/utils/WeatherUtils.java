package com.mingsoft.cms.utils;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <dl>
 * <dt>ProjectName : MCMS </dt>
 * <dt>PakageName : com.mingsoft.cms.utils </dt>
 * <dt>ClassName: WeatherUtils </dt>
 * <dd>CreateDate: 2017-12-04 11:00 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
public class WeatherUtils {

    public static Map<String, Object> weatherInfo;
    public static Long timeStrap;
    public static Long timeInterval;

    static {
        weatherInfo = new HashMap<>();
        timeStrap = null;
        timeInterval = 3600L * 1000;
    }

    public static void getWeather(){

//        String url="http://www.sojson.com/open/api/weather/json.shtml?city=" + URLEncoder.encode("西安");
        String url= "http://www.sojson.com/open/api/weather/json.shtml?city=%E8%A5%BF%E5%AE%89";
        StringBuilder result1= new StringBuilder();
        BufferedReader in=null;
        try {
            URL realUrl;
            realUrl = new URL(url);
            URLConnection conn=realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE)");
            conn.connect();

            in=new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while((line=in.readLine())!=null)
            {
                result1.append("\n").append(line);
            }


            weatherInfo.put("result", JSON.parseObject(result1.toString()));

            timeStrap = System.currentTimeMillis();
        }
        catch (Exception e) {
            System.out.println("发送GET请求出现异常！"+e);
            e.printStackTrace();
        }
        finally
        {
            if(in!=null)
            {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

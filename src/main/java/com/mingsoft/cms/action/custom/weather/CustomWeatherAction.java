package com.mingsoft.cms.action.custom.weather;

import com.mingsoft.basic.action.BaseAction;
import com.mingsoft.cms.utils.WeatherUtils;
import net.mingsoft.base.util.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <dl>
 * <dt>ProjectName : MCMS </dt>
 * <dt>PakageName : com.mingsoft.cms.action.custom.weather </dt>
 * <dt>ClassName: CustomWeatherAction </dt>
 * <dd>CreateDate: 2017-12-04 11:39 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
@Controller
@RequestMapping("/${managerPath}/custom/weather")
public class CustomWeatherAction extends BaseAction {

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping("/getWheatherInfo")
    public void getWheatherInfo(HttpServletRequest request, HttpServletResponse response){

        Long thisTime = System.currentTimeMillis();
        if(WeatherUtils.timeStrap == null || WeatherUtils.weatherInfo == null
                || (thisTime - WeatherUtils.timeStrap > WeatherUtils.timeInterval )){
            WeatherUtils.getWeather();
        }

       this.outJson(response, JSONObject.toJSON(WeatherUtils.weatherInfo));
    }

}

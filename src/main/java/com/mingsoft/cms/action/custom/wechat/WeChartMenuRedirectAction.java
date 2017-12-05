package com.mingsoft.cms.action.custom.wechat;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingsoft.cms.utils.WeiChartAPIUtils;
import com.mingsoft.people.biz.IPeopleBiz;
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.people.entity.PeopleUserEntity;
import com.mingsoft.util.StringUtil;
import net.mingsoft.basic.util.BasicUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author YueBin
 * @create 2016年7月15日
 * @version
 * 说明:<br />
 * 微信公众账号 菜单接入转发地址
 */
@Controller
@RequestMapping("/${managerPath}/custom/wechat")
public class WeChartMenuRedirectAction  {

	private Logger logger=LoggerFactory.getLogger(getClass());
	private String openId;

	/**
	 * 注入用户基础信息表业务层
	 */
	@Autowired
	private IPeopleUserBiz peopleUserBiz;

	/**
	 * 注入用户基础信息控制层
	 */
	@Autowired
	private IPeopleBiz peopleBiz;


	@RequestMapping("/webOAth")
	public void process(HttpServletRequest request,HttpServletResponse response){

		String returnResult="";

		//微信返回code
		String code=request.getParameter("code");
		//微信返回 state 
		String state=request.getParameter("state");

		JSONObject result= WeiChartAPIUtils.getSnsapiBaseInfoByCode(code);

		try{

			logger.info("微信OAth2.0验证返回信息 result："+result);

			if(result!=null&&result.has("openid")){
				openId=result.getString("openid");
			}else {
				throw new Exception("获取openid失败");
			}


			if(result.has("openid")){
				openId=result.getString("openid");
				String accessToken =result.getString("access_token");

				//查询微信用户详情
				JSONObject userInfos = WeiChartAPIUtils.getUserInfoByOpenId(accessToken, openId);

				//注册用户
				PeopleUserEntity peopleUser = new PeopleUserEntity();
				peopleUser.setPeopleName(openId);
				peopleUser.setPuNickname(userInfos.getString("nickname"));
				peopleUser.setPeoplePassword(StringUtil.Md5("111111"));
				peopleUser.setPuSex(Integer.valueOf(userInfos.getString("sex")));
				peopleUser.setPuIcon(userInfos.getString("headimgurl"));
				peopleUser.setPeopleState(0);
				peopleUser.setPuAddress(userInfos.getString("country") + userInfos.getString("province") + userInfos.getString("city"));
				peopleUser.setPeopleDateTime(new Date());
				if(userInfos.getString("unionid") != null){
					peopleUser.setPeopleCode(userInfos.getString("unionid"));
				}

				PeopleEntity peopleCheck = this.peopleBiz.getEntityByUserName(openId, 1);
				if(peopleCheck == null){
					peopleUser.setPeopleAppId(1);
					peopleUserBiz.savePeople(peopleUser);
				}

				//拼接前台访问地址
				//http://yuebin616.iask.in/wap/index.jsp/?v=0.0.1&openid=$am{openid}/#/mall/weChartIn/
				String url ="https://www.baidu.com/";//跳转地址

				url=url+URLEncoder.encode(openId, "UTF-8");

				logger.info("微信公众账号转发地址："+url);

				returnResult=url;
			}else {
				throw new Exception("获取openid失败");
			}


			response.sendRedirect(returnResult);

		}catch(Exception e){
			e.printStackTrace();
		}
	}



}

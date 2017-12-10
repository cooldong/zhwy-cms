package com.mingsoft.cms.action.custom.former;

import com.mingsoft.base.constant.Const;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import com.mingsoft.basic.biz.IRoleBiz;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.basic.entity.RoleEntity;
import com.mingsoft.mdiy.biz.IDictBiz;
import com.mingsoft.mdiy.entity.DictEntity;
import com.mingsoft.people.bean.PeopleBean;
import com.mingsoft.people.bean.PeopleLoginBean;
import com.mingsoft.people.biz.IPeopleBiz;
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.people.constant.ModelCode;
import com.mingsoft.people.constant.e.PeopleEnum;
import com.mingsoft.people.constant.e.SessionConstEnum;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.people.entity.PeopleUserEntity;
import com.mingsoft.util.MD5Util;
import com.mingsoft.util.StringUtil;
import net.mingsoft.base.util.JSONObject;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 用户基础信息表管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/custom/people/peopleUser")
public class CustomPeopleUserAction extends com.mingsoft.people.action.BaseAction{

	/**
	 * 注入角色业务层
	 */
	@Autowired
	private IRoleBiz roleBiz;

	/**
	 * 注入字典表业务层
	 */
	@Autowired
	private IDictBiz dictBiz;

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

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("/getDict")
	@ResponseBody
	public void get(@ModelAttribute DictEntity dict, HttpServletResponse response, HttpServletRequest request, ModelMap model){
//		dict.setAppId(1);
		DictEntity _dict = new DictEntity();
		_dict.setDictType("11");
		_dict.setAppId(1);
		List<DictEntity> _dicts11 = (List<DictEntity>) dictBiz.query(_dict);
		_dict.setDictType("12");
		List<DictEntity> _dicts12 = (List<DictEntity>) dictBiz.query(_dict);
		_dicts11.addAll(_dicts12);
		this.outJson(response, _dicts11);
	}

	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/cms/customFormer/user/index");
	}
	
	/**
	 * 查询用户基础信息表列表
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId 用户ID关联people表的（people_id）<br/>
	 * puRealName 用户真实名称<br/>
	 * puAddress 用户地址<br/>
	 * puIcon 用户头像图标地址<br/>
	 * puNickname 用户昵称<br/>
	 * puSex 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday 用户出生年月日<br/>
	 * puCard 身份证<br/>
	 * puAppId 用户所属应用ID<br/>
	 * puProvince 省<br/>
	 * puCity 城市<br/>
	 * puDistrict 区<br/>
	 * puStreet 街道<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute PeopleBean peopleUser,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		if(peopleUser == null){
			peopleUser = new PeopleBean();
		}
		peopleUser.setPeopleAppId(BasicUtil.getAppId());
		BasicUtil.startPage();
		List peopleUserList = peopleUserBiz.query(peopleUser);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(peopleUserList,(int)BasicUtil.endPage(peopleUserList).getTotal()),new DoubleValueFilter(),new DateValueFilter("yyyy-MM-dd")));
	}
	
	/**
	 * 返回编辑界面peopleUser_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute PeopleUserEntity peopleUser,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(peopleUser.getPuPeopleId() != null){
			BaseEntity peopleUserEntity = peopleUserBiz.getEntity(peopleUser.getPuPeopleId());			
			model.addAttribute("peopleUserEntity",peopleUserEntity);
		}
		return view ("/cms/customFormer/user/form");
	}
	
	/**
	 * 获取用户基础信息表
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId 用户ID关联people表的（people_id）<br/>
	 * puRealName 用户真实名称<br/>
	 * puAddress 用户地址<br/>
	 * puIcon 用户头像图标地址<br/>
	 * puNickname 用户昵称<br/>
	 * puSex 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday 用户出生年月日<br/>
	 * puCard 身份证<br/>
	 * puAppId 用户所属应用ID<br/>
	 * puProvince 省<br/>
	 * puCity 城市<br/>
	 * puDistrict 区<br/>
	 * puStreet 街道<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute PeopleUserEntity peopleUser,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(peopleUser.getPuPeopleId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("pu.people.id")));
			return;
		}
		PeopleUserEntity _peopleUser = (PeopleUserEntity)peopleUserBiz.getEntity(peopleUser.getPuPeopleId());
		this.outJson(response, _peopleUser,"peopleOldPassword","peoplePassword");
	}
	
	/**
	 * 保存用户基础信息表实体
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId 用户ID关联people表的（people_id）<br/>
	 * puRealName 用户真实名称<br/>
	 * puAddress 用户地址<br/>
	 * puIcon 用户头像图标地址<br/>
	 * puNickname 用户昵称<br/>
	 * puSex 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday 用户出生年月日<br/>
	 * puCard 身份证<br/>
	 * puAppId 用户所属应用ID<br/>
	 * puProvince 省<br/>
	 * puCity 城市<br/>
	 * puDistrict 区<br/>
	 * puStreet 街道<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute PeopleUserEntity peopleUser, HttpServletResponse response, HttpServletRequest request) {
		//验证用户真实名称的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuRealName()+"", 0, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.real.name"), "0", "50"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuAddress()+"", 0, 200)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.address"), "0", "200"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuIcon()+"", 0, 200)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.icon"), "0", "200"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuNickname()+"", 0, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.nickname"), "0", "50"));
			return;			
		}
		//验证用户性别(0.未知、1.男、2.女)的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuSex()+"", 0, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.sex"), "0", "10"));
			return;			
		}
		//验证身份证的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuCard()+"", 0, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.card"), "0", "255"));
			return;			
		}
		if(!StringUtil.isBlank(StringUtil.Md5(peopleUser.getPeoplePassword()))){
			//设置用户密码
			peopleUser.setPeoplePassword(StringUtil.Md5(peopleUser.getPeoplePassword()));
		}
		//验证用户输入的信息是否合法
		if(!this.checkPeople(peopleUser, request, response)){
			return;
		}
		peopleUser.setPeopleDateTime(new Date());
		peopleUser.setPeopleAppId(BasicUtil.getAppId());
		peopleUserBiz.savePeople(peopleUser);
		this.outJson(response, peopleUser,"peopleOldPassword","peoplePassword");
	}
	
	/**
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId:多个puPeopleId直接用逗号隔开,例如puPeopleId=1,2,3,4
	 * 批量删除用户基础信息表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@RequestBody List<PeopleUserEntity> peopleUsers,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[peopleUsers.size()];
		for(int i = 0;i<peopleUsers.size();i++){
			ids[i] = peopleUsers.get(i).getPuPeopleId();
		}
		peopleUserBiz.deletePeople(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新用户基础信息表信息用户基础信息表
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId 用户ID关联people表的（people_id）<br/>
	 * puRealName 用户真实名称<br/>
	 * puAddress 用户地址<br/>
	 * puIcon 用户头像图标地址<br/>
	 * puNickname 用户昵称<br/>
	 * puSex 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday 用户出生年月日<br/>
	 * puCard 身份证<br/>
	 * puAppId 用户所属应用ID<br/>
	 * puProvince 省<br/>
	 * puCity 城市<br/>
	 * puDistrict 区<br/>
	 * puStreet 街道<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute PeopleUserEntity peopleUser, HttpServletResponse response,
			HttpServletRequest request) {
		if(!StringUtil.checkLength(peopleUser.getPuRealName()+"", 0, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.real.name"), "0", "50"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuAddress()+"", 0, 200)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.address"), "0", "200"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuIcon()+"", 0, 200)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.icon"), "0", "200"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuNickname()+"", 0, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.nickname"), "0", "50"));
			return;			
		}
		//验证用户性别(0.未知、1.男、2.女)的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuSex()+"", 0, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.sex"), "0", "10"));
			return;			
		}
		//验证身份证的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuCard()+"", 0, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.card"), "0", "255"));
			return;			
		}
		if(!this.checkUpdatePeople(peopleUser, request, response)){
			return;
		}
		//判断用户密码是否为空，如果不为空则进行密码的更新
		if(!StringUtil.isBlank(peopleUser.getPeoplePassword())){
			//设置用户密码
			peopleUser.setPeoplePassword(StringUtil.Md5(peopleUser.getPeoplePassword()));
		}
		peopleUser.setPeopleId(peopleUser.getPuPeopleId());
		peopleUserBiz.updatePeople(peopleUser);
		this.outJson(response, peopleUser,"peopleOldPassword","peoplePassword");
	}
	
	/**
	 * 验证更新用户信息是判断用户输入的信息是否合法
	 * @param peopleUser  用户实体
	 * @param request http请求对象
	 * @param response http响应对象
	 */
	public boolean checkUpdatePeople(PeopleUserEntity peopleUser,HttpServletRequest request,HttpServletResponse response){
		
		//获取更改前的用户
		PeopleUserEntity oldPeopleUser = (PeopleUserEntity) peopleUserBiz.getEntity(peopleUser.getPuPeopleId());
		//获取应用id
		int appId = this.getAppId(request);
		//如果填写了邮箱，则验证邮箱格式是否正确
		if (!StringUtil.isBlank(peopleUser.getPeopleMail()) && !StringUtil.isEmail(peopleUser.getPeopleMail())) {
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.mail.format.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
		//验证用户名不能为空
		if(StringUtil.isBlank(peopleUser.getPeopleName())){
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.name.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
				
		//如果填写了手机号码，则验证手机号码填写是否正确
		if (!StringUtil.isBlank(peopleUser.getPeoplePhone()) && !StringUtil.isMobile(peopleUser.getPeoplePhone())) {
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.phone.format.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
				
				
		//当用户名进行修改时验证用户名是否是唯一的
		if (!StringUtil.isBlank(peopleUser.getPeopleName())) {
			// 验证手机号是否唯一
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleName(), appId);
			//判断之前是否已经存在用户名，如果不存在，则判断是否存在重名，如果存在,判断用户是否更改用户名如果更改则判断新更改的用户名是否已经存在
			//判断填写的用户名和之前用户名是否相同，如果不相同
			if(StringUtil.isBlank(oldPeopleUser.getPeopleName())){
				if (peoplePhone != null) {
					this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
					return false;
				}
			}else{
				if(!oldPeopleUser.getPeopleName().equals(peopleUser.getPeopleName())){
					if (peoplePhone != null) {
							this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
							return false;
					}
				}
			}
					
		}
		if(!StringUtil.isBlank(peopleUser.getPeoplePhone())){
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeoplePhone(), appId);
			//判断之前是否已经存在手机号，如果不存在，则判断是否存在重名，如果存在,判断用户是否更改手机号如果更改则判断新更改的手机号是否已经存在
			//判断填写的手机号和之前手机号是否相同，如果不相同
			if(StringUtil.isBlank(oldPeopleUser.getPeoplePhone())){
				if (peoplePhone != null) {
					this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
					return false;
				}
			}else{
				if(!oldPeopleUser.getPeoplePhone().equals(peopleUser.getPeoplePhone())){
					if (peoplePhone != null) {
						this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
						return false;
					}
				}
			}
		}
		//验证邮箱的唯一性
		if(!StringUtil.isBlank(peopleUser.getPeopleMail())){
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleMail(), appId);
			//判断之前是否已经存在手机号，如果不存在，则判断是否存在重名，如果存在,判断用户是否更改手机号如果更改则判断新更改的手机号是否已经存在
			//判断填写的手机号和之前手机号是否相同，如果不相同
			if(StringUtil.isBlank(oldPeopleUser.getPeopleMail())){
				if (peoplePhone != null) {
					this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
					return false;
				}
			}else{
				if(!oldPeopleUser.getPeopleMail().equals(peopleUser.getPeopleMail())){
					if (peoplePhone != null) {
						this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
						return false;
					}
				}
			}
		}
		//验证用户身份证号码
		return true;
	}
	
	/**
	 * 验证保存用户时输入的信息是否合法
	 * @param peopleUser  用户实体
	 * @param request http请求对象
	 * @param response http响应对象
	 */
	public boolean checkPeople(PeopleUserEntity peopleUser,HttpServletRequest request,HttpServletResponse response){
		//获取应用id
		int appId = this.getAppId(request);
		//如果填写了邮箱，则验证邮箱格式是否正确
		if (!StringUtil.isBlank(peopleUser.getPeopleMail()) && !StringUtil.isEmail(peopleUser.getPeopleMail())) {
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.mail.format.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
		//验证用户名不能为空
		if(StringUtil.isBlank(peopleUser.getPeopleName())){
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.name.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
		//如果填写了手机号码，则验证手机号码填写是否正确
		if (!StringUtil.isBlank(peopleUser.getPeoplePhone()) && !StringUtil.isMobile(peopleUser.getPeoplePhone())) {
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.phone.format.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
		
		
		//验证用户名是否是唯一的
		if (!StringUtil.isBlank(peopleUser.getPeopleName())) {
			// 验证手机号是否唯一
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleName(), appId);
			if (peoplePhone != null) {
				this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
				return false;
			}
		}
		if (!StringUtil.isBlank(peopleUser.getPeoplePhone())) {
			// 验证手机号是否唯一
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeoplePhone(), appId);
			if (peoplePhone != null) {
				this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.phone.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
				return false;
			}
		}
		if (!StringUtil.isBlank(peopleUser.getPeopleMail())) {
			// 验证邮箱是否唯一
			PeopleEntity peopleMail = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleMail(), appId);
			if (peopleMail != null) {
				this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.mail.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 获取用户详细信息
	 * @param peopleId 用户ID
	 * @param request
	 * @param response
	 */
	@Deprecated
	@RequestMapping("/getEntity")
	public void getEntity(String peopleId,HttpServletRequest request,HttpServletResponse response){
		if(StringUtil.isBlank(peopleId) || !StringUtil.isInteger(peopleId)){
			this.outJson(response, ModelCode.PEOPLE_USER,false);
			return ;
		}
		PeopleUserEntity peopleUser = (PeopleUserEntity) this.peopleUserBiz.getEntity(Integer.parseInt(peopleId));
		if(peopleUser == null){
			this.outJson(response, ModelCode.PEOPLE_USER,false);
			return ;
		}
		this.outJson(response, ModelCode.PEOPLE_USER,true,null,JSONObject.toJSONString(peopleUser));
	}

	/**
	 * 获取用户基础信息表
	 * @param  用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * peoplePhone 用户真实名称<br/>
	 * peoplePassword 用户真实密码<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/userLogin")
	@ResponseBody
	public void checkLogin(@ModelAttribute PeopleEntity people, HttpServletRequest request,
						   HttpServletResponse response) {

		// 用户名和密码不能为空
		if (StringUtil.isBlank(people.getPeopleName()) || StringUtil.isBlank(people.getPeoplePassword())) {
			this.outJson(response, ModelCode.PEOPLE_LOGIN, false,
					this.getResString("err.error", this.getResString("people")));
			return;
		}

		// 根据应用ID和用户名查询用户密码
		int appId = this.getAppId(request);
		PeopleEntity peopleEntity = this.peopleBiz.getEntityByUserName(people.getPeopleName(), appId);
		if (peopleEntity == null) {
			// 用户名或密码错误
			this.outJson(response, ModelCode.PEOPLE_LOGIN, false,
					this.getResString("err.error", this.getResString("pepple.no.exist")));
			return;
		}

		// 将用户输入的密码用MD5加密再和数据库中的进行比对
		String peoplePassWord = MD5Util.MD5Encode(people.getPeoplePassword(), Const.UTF8);
		if (peoplePassWord.equals(peopleEntity.getPeoplePassword())) {
			// 登录成功,压入用户session
			this.setPeopleBySession(request, peopleEntity);
			PeopleLoginBean tempPeople = new PeopleLoginBean();
			tempPeople.setPeopleId(peopleEntity.getPeopleId());
			tempPeople.setPeopleName(peopleEntity.getPeopleName());
			tempPeople.setPeopleMail(peopleEntity.getPeopleMail());
			tempPeople.setPeopleState(peopleEntity.getPeopleState());
			tempPeople.setCookie(request.getHeader("cookie"));
			// 判断用户是否点击了自动登录
//			if (people.getPeopleAutoLogin() > 0) {
//				tempPeople.setPeoplePassword(people.getPeoplePassword());
//				this.setCookie(request, response, CookieConstEnum.PEOPLE_COOKIE, JSONObject.toJSONString(tempPeople),
//						60 * 60 * 24 * people.getPeopleAutoLogin());
//			}

			this.outJson(response, ModelCode.PEOPLE_LOGIN, true, JSONObject.toJSONString(tempPeople));

		} else {
			// 用户名或密码错误
			this.outJson(response, ModelCode.PEOPLE_LOGIN, false,
					this.getResString("err.error", this.getResString("pepple.no.exist")));
		}

	}

	/**
	 * 修改密码
	 *
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peoplePassword 用户新密码<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public void resetPassword(@RequestBody List<PeopleUserEntity> peopleUsers, HttpServletRequest request,
							  HttpServletResponse response) {
		for (PeopleUserEntity people : peopleUsers){
			PeopleEntity _people = (PeopleEntity)peopleBiz.getEntity(people.getPeopleId());
			// 将用户输入的原始密码用MD5加密再和数据库中的进行比对
			String peoplePassWord = StringUtil.Md5("111111", Const.UTF8);
			// 执行修改
			_people.setPeoplePassword(peoplePassWord);
			this.peopleBiz.updateEntity(_people);
		}
		this.outJson(response, ModelCode.PEOPLE, true);
	}

	/**
	 * 用户注册<br/>
	 * 用户可以更具用名称、手机号、邮箱进行注册<br/>
	 * 几种注册流程的形式：<br/>
	 * 1、普通用户名称、登录密码，优先用户名注册,登录密码最长度范围6～12个字符；<br/>
	 * 2、邮箱、邮箱验证码、登录密码；<br/>
	 * 3、手机号、短信验证码、登录密码；<br/>
	 * 注意： 1、注册页面必须存在验证码<br/>
	 * 2、如果需要接收验证码操作，需要使用发送验证码配合使用才能完成注册流程<br/>
	 *
	 * @param rand_code
	 *            验证码
	 *
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peoplePhone 手机号<br/>
	 *            peopleName 用名称 用户名长度在3～12个字符之间，只能是字母数字混合<br/>
	 *            peopleMail 邮箱<br/>
	 *            peoplePassword 注册密码 <br/>
	 *            peopleCode 如果注册流程存在用户验证码，那么必须传递该参数 <br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",result:"true成功、false失败",resultMsg: "提示信息"}
	 * @see PeopleEntity
	 * @see PeopleEnum
	 *
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public void register(@ModelAttribute PeopleUserEntity people, HttpServletRequest request,
						 HttpServletResponse response) {
		LOG.debug("people register");
		// 验证码验证 验证码不为null 或 验证码不相等
//		if (!checkRandCode(request)) {
//			this.outJson(response, null, false, this.getResString("err.error", this.getResString("rand.code")));
//			return;
//		}

		// 判断用户信息是否为空
		if (people == null) {
			this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
					this.getResString("err.empty", this.getResString("people")));
			return;
		}

		int appId = this.getAppId(request);
		// 如果用户名不为空表示使用的是账号注册方式
		if (!StringUtil.isBlank(people.getPeopleName())) {
			// 验证用户名
			if (StringUtil.isBlank(people.getPeopleName())) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.empty", this.getResString("people.name")));
				return;
			}

			if (people.getPeopleName().contains(" ")) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("people.name") + this.getResString("people.space"));
				return;
			}

			if (!StringUtil.checkLength(people.getPeopleName(), 3, 30)) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.length", this.getResString("people.name"), "3", "30"));
				return;
			}

			// 判断用户名是否已经被注册
			PeopleEntity peopleName = this.peopleBiz.getEntityByUserName(people.getPeopleName(), appId);
			if (peopleName != null) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.exist", this.getResString("people.name") + peopleName.getPeopleName()));
				return;
			}
		}

		if (!StringUtil.isBlank(people.getPeoplePhone())) {// 验证手机号
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(people.getPeoplePhone(), appId);
			if (peoplePhone != null && peoplePhone.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()) { // 已存在
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.exist", this.getResString("people.phone")));
				return;
			} else {
				Object obj = this.getSession(request, SessionConstEnum.SEND_CODE_SESSION);
				if (obj != null) {
					PeopleEntity _people = (PeopleEntity) obj;
					if (_people.getPeoplePhone().equals(people.getPeoplePhone())) {
						if (_people.getPeopleCode().equals(people.getPeopleCode())) {
							people.setPeoplePhoneCheck(PeopleEnum.PHONE_CHECK);
						} else {
							this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
									this.getResString("err.error", this.getResString("people.code")));
							return;
						}
					}
				}
			}
		}

		if (!StringUtil.isBlank(people.getPeopleMail())) {// 验证邮箱
			// 检查邮箱格式是否含有空格
			if (people.getPeopleMail().contains(" ")) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("people.mail") + this.getResString("people.space"));
				return;
			}
			PeopleEntity peopleMail = this.peopleBiz.getEntityByUserName(people.getPeopleMail(), appId);
			if (peopleMail != null && peopleMail.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.exist", this.getResString("people.mail")));
				return;
			} else {
				Object obj = this.getSession(request, SessionConstEnum.SEND_CODE_SESSION);
				if (obj != null) {
					PeopleEntity _people = (PeopleEntity) obj;
					if (_people.getPeopleMail().equals(people.getPeopleMail())) {
						if (_people.getPeopleCode().equals(people.getPeopleCode())) {
							people.setPeopleMailCheck(PeopleEnum.MAIL_CHECK);
						} else {
							this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
									this.getResString("err.error", this.getResString("people.mail")));
							return;
						}
					}
				}
			}
		}

		// 密码
		if (StringUtil.isBlank(people.getPeoplePassword())) {
			this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
					this.getResString("err.empty", this.getResString("people.password")));
			return;
		}

		if (people.getPeoplePassword().contains(" ")) {
			this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
					this.getResString("people.password") + this.getResString("people.space"));
			return;
		}

		if (people.getPeoplePassword().length() < 6 || people.getPeoplePassword().length() > 30) {
			this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
					this.getResString("err.length", this.getResString("people.password"), "6", "30"));
			return;
		}

		// 将密码使用MD5加密
		people.setPeoplePassword(MD5Util.MD5Encode(people.getPeoplePassword(), Const.UTF8));
		people.setPeopleAppId(appId);
		people.setPeopleDateTime(new Date());
		peopleUserBiz.savePeople(people);
		this.outJson(response, ModelCode.PEOPLE_REGISTER, true,
				this.getResString("success", this.getResString("people.register")));
		LOG.debug("people register ok");
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("/userInfoDetail")
	public void userInfoDetail(@ModelAttribute PeopleEntity people, HttpServletRequest request,
						   HttpServletResponse response) {

		// 用户名不能为空
		if (StringUtil.isBlank(people.getPeopleName()) ) {
			this.outJson(response, ModelCode.PEOPLE_LOGIN, false,
					this.getResString("err.error", this.getResString("people")));
			return;
		}

		// 根据应用ID和用户名查询用户密码
		int appId = 1;
		PeopleEntity peopleEntity = this.peopleBiz.getEntityByUserName(people.getPeopleName(), appId);
		if (peopleEntity == null) {
			// 用户名或密码错误
			this.outJson(response, ModelCode.PEOPLE_LOGIN, false,
					this.getResString("err.error", this.getResString("pepple.no.exist")));
			return;
		}
		this.outJson(response, ModelCode.PEOPLE_LOGIN, true, JSONObject.toJSONString(peopleEntity));

	}


	@RequestMapping("/checkRole")
	@ResponseBody
	public void list(@ModelAttribute RoleEntity role, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		role.setRoleManagerId(managerSession.getManagerId());
		role.setRoleName("后台用户");
		BasicUtil.startPage();
		List roleList = roleBiz.query(role);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(roleList,(int)BasicUtil.endPage(roleList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
}
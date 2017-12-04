package com.mingsoft.cms.action.custom.former;

import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import com.mingsoft.mdiy.biz.IDictBiz;
import com.mingsoft.mdiy.entity.DictEntity;
import net.mingsoft.base.util.JSONObject;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 字典表管理控制层
 * @author 蓝精灵
 * @version 
 * 版本号：1<br/>
 * 创建日期：2017-8-12 14:22:36<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/custom/mdiy/dict")
public class CustomDictAction extends com.mingsoft.mdiy.action.BaseAction{
	
	/**
	 * 注入字典表业务层
	 */	
	@Autowired
	private IDictBiz dictBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/cms/customFormer/dict/index");
	}
	
	/**
	 * 查询字典表列表
	 * @param dict 字典表实体
	 * <i>dict参数包含字段信息参考：</i><br/>
	 * dictId 编号<br/>
	 * dictAppId 应用编号<br/>
	 * dictValue 数据值<br/>
	 * dictLabel 标签名<br/>
	 * dictType 类型<br/>
	 * dictDescription 描述<br/>
	 * dictSort 排序（升序）<br/>
	 * dictParentId 父级编号<br/>
	 * createBy 创建者<br/>
	 * createDate 创建时间<br/>
	 * updateBy 更新者<br/>
	 * updateDate 更新时间<br/>
	 * dictRemarks 备注信息<br/>
	 * del 删除标记<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * dictId: 编号<br/>
	 * dictAppId: 应用编号<br/>
	 * dictValue: 数据值<br/>
	 * dictLabel: 标签名<br/>
	 * dictType: 类型<br/>
	 * dictDescription: 描述<br/>
	 * dictSort: 排序（升序）<br/>
	 * dictParentId: 父级编号<br/>
	 * createBy: 创建者<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新者<br/>
	 * updateDate: 更新时间<br/>
	 * dictRemarks: 备注信息<br/>
	 * del: 删除标记<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute DictEntity dict,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		dict.setAppId(BasicUtil.getAppId());
		BasicUtil.startPage();
		List dictList = dictBiz.query(dict);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(dictList,(int)BasicUtil.endPage(dictList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面dict_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute DictEntity dict,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(dict.getDictId() != null){
			BaseEntity dictEntity = dictBiz.getEntity(dict.getDictId());			
			model.addAttribute("dictEntity",dictEntity);
		}
		
		return view ("/cms/customFormer/dict/form");
	}
	
	/**
	 * 获取字典表
	 * @param dict 字典表实体
	 * <i>dict参数包含字段信息参考：</i><br/>
	 * dictId 编号<br/>
	 * dictAppId 应用编号<br/>
	 * dictValue 数据值<br/>
	 * dictLabel 标签名<br/>
	 * dictType 类型<br/>
	 * dictDescription 描述<br/>
	 * dictSort 排序（升序）<br/>
	 * dictParentId 父级编号<br/>
	 * createBy 创建者<br/>
	 * createDate 创建时间<br/>
	 * updateBy 更新者<br/>
	 * updateDate 更新时间<br/>
	 * dictRemarks 备注信息<br/>
	 * del 删除标记<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * dictId: 编号<br/>
	 * dictAppId: 应用编号<br/>
	 * dictValue: 数据值<br/>
	 * dictLabel: 标签名<br/>
	 * dictType: 类型<br/>
	 * dictDescription: 描述<br/>
	 * dictSort: 排序（升序）<br/>
	 * dictParentId: 父级编号<br/>
	 * createBy: 创建者<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新者<br/>
	 * updateDate: 更新时间<br/>
	 * dictRemarks: 备注信息<br/>
	 * del: 删除标记<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute DictEntity dict,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(dict.getDictId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("dict.id")));
			return;
		}
		DictEntity _dict = (DictEntity)dictBiz.getEntity(dict.getDictId());
		this.outJson(response, _dict);
	}
	
	/**
	 * 保存字典表实体
	 * @param dict 字典表实体
	 * <i>dict参数包含字段信息参考：</i><br/>
	 * dictId 编号<br/>
	 * dictAppId 应用编号<br/>
	 * dictValue 数据值<br/>
	 * dictLabel 标签名<br/>
	 * dictType 类型<br/>
	 * dictDescription 描述<br/>
	 * dictSort 排序（升序）<br/>
	 * dictParentId 父级编号<br/>
	 * createBy 创建者<br/>
	 * createDate 创建时间<br/>
	 * updateBy 更新者<br/>
	 * updateDate 更新时间<br/>
	 * dictRemarks 备注信息<br/>
	 * del 删除标记<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * dictId: 编号<br/>
	 * dictAppId: 应用编号<br/>
	 * dictValue: 数据值<br/>
	 * dictLabel: 标签名<br/>
	 * dictType: 类型<br/>
	 * dictDescription: 描述<br/>
	 * dictSort: 排序（升序）<br/>
	 * dictParentId: 父级编号<br/>
	 * createBy: 创建者<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新者<br/>
	 * updateDate: 更新时间<br/>
	 * dictRemarks: 备注信息<br/>
	 * del: 删除标记<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute DictEntity dict, HttpServletResponse response, HttpServletRequest request) {
		dict.setAppId(BasicUtil.getAppId());

		DictEntity dictEntity = new DictEntity();
		dictEntity.setAppId(BasicUtil.getAppId());
		dictEntity.setDictLabel(dict.getDictLabel());
		dictEntity =(DictEntity) dictBiz.getEntity(dictEntity);

		if(dictEntity == null || dictEntity.getDictId() == null){
			dictBiz.saveEntity(dict);
			this.outJson(response, JSONObject.toJSONString(dict));
		}
		else {
			this.outJson(response, JSONObject.toJSONString(null));
		}
	}
	
	/**
	 * @param dict 字典表实体
	 * <i>dict参数包含字段信息参考：</i><br/>
	 * dictId:多个dictId直接用逗号隔开,例如dictId=1,2,3,4
	 * 批量删除字典表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@RequestBody List<DictEntity> dicts,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[dicts.size()];
		for(int i = 0;i<dicts.size();i++){
			ids[i] = dicts.get(i).getDictId();
		}
		dictBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新字典表信息字典表
	 * @param dict 字典表实体
	 * <i>dict参数包含字段信息参考：</i><br/>
	 * dictId 编号<br/>
	 * dictAppId 应用编号<br/>
	 * dictValue 数据值<br/>
	 * dictLabel 标签名<br/>
	 * dictType 类型<br/>
	 * dictDescription 描述<br/>
	 * dictSort 排序（升序）<br/>
	 * dictParentId 父级编号<br/>
	 * createBy 创建者<br/>
	 * createDate 创建时间<br/>
	 * updateBy 更新者<br/>
	 * updateDate 更新时间<br/>
	 * dictRemarks 备注信息<br/>
	 * del 删除标记<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * dictId: 编号<br/>
	 * dictAppId: 应用编号<br/>
	 * dictValue: 数据值<br/>
	 * dictLabel: 标签名<br/>
	 * dictType: 类型<br/>
	 * dictDescription: 描述<br/>
	 * dictSort: 排序（升序）<br/>
	 * dictParentId: 父级编号<br/>
	 * createBy: 创建者<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新者<br/>
	 * updateDate: 更新时间<br/>
	 * dictRemarks: 备注信息<br/>
	 * del: 删除标记<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute DictEntity dict, HttpServletResponse response,
			HttpServletRequest request) {

		DictEntity dictEntity = new DictEntity();
		dictEntity.setAppId(BasicUtil.getAppId());
		dictEntity.setDictLabel(dict.getDictLabel());
		dictEntity =(DictEntity) dictBiz.getEntity(dictEntity);

		if(dictEntity == null || dictEntity.getDictId() == null || dictEntity.getDictId().equals(dict.getDictId())){
			dictBiz.updateEntity(dict);
			this.outJson(response, JSONObject.toJSONString(dict));
		}
		else {
			this.outJson(response, JSONObject.toJSONString(null));
		}
	}


/*=============================================================*/

	/**
	 * 获取字典表
	 * @param dict 字典表实体
	 * <i>dict参数包含字段信息参考：</i><br/>
	 * dictId 编号<br/>
	 * dictAppId 应用编号<br/>
	 * dictValue 数据值<br/>
	 * dictLabel 标签名<br/>
	 * dictType 类型<br/>
	 * dictDescription 描述<br/>
	 * dictSort 排序（升序）<br/>
	 * dictParentId 父级编号<br/>
	 * createBy 创建者<br/>
	 * createDate 创建时间<br/>
	 * updateBy 更新者<br/>
	 * updateDate 更新时间<br/>
	 * dictRemarks 备注信息<br/>
	 * del 删除标记<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * dictId: 编号<br/>
	 * dictAppId: 应用编号<br/>
	 * dictValue: 数据值<br/>
	 * dictLabel: 标签名<br/>
	 * dictType: 类型<br/>
	 * dictDescription: 描述<br/>
	 * dictSort: 排序（升序）<br/>
	 * dictParentId: 父级编号<br/>
	 * createBy: 创建者<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新者<br/>
	 * updateDate: 更新时间<br/>
	 * dictRemarks: 备注信息<br/>
	 * del: 删除标记<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/web/get")
	@ResponseBody
	public void webget(@ModelAttribute DictEntity dict,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		List _dicts = dictBiz.query(dict);
//		DictEntity _dict = (DictEntity)dictBiz.getEntity(dict);
		this.outJson(response, _dicts);
	}
}
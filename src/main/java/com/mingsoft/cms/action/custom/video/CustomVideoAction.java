package com.mingsoft.cms.action.custom.video;

import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import com.mingsoft.basic.action.BaseAction;
import com.mingsoft.cms.biz.CustomVideoBiz;
import com.mingsoft.cms.biz.CustomVideoMessageBiz;
import com.mingsoft.cms.entity.CustomVideoEntity;
import com.mingsoft.cms.entity.CustomVideoMessageEntity;
import com.mingsoft.cms.utils.CustomStringUtils;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <dl>
 * <dt>ProjectName : MCMS </dt>
 * <dt>PakageName : com.mingsoft.cms.action </dt>
 * <dt>ClassName: CustomVideoAction </dt>
 * <dd>CreateDate: 2017-11-30 16:24 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
@Controller
@RequestMapping("/${managerPath}/custom/video")
public class CustomVideoAction extends BaseAction {

    @Autowired
    CustomVideoBiz customVideoBizImpl;

    @Autowired
    CustomVideoMessageBiz customVideoMessageBizImpl;

    /**
     * 加载页面显示所有文章信息
     *
     * @param request
     * @return 返回文章页面显示地址
     */
    @SuppressWarnings("static-access")
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        // 获取站点id
        int appId = this.getAppId(request);
        // 返回路径
        return view("/cms/customvideo/index");
    }

    @RequestMapping("/list")
    @ResponseBody
    public void list(@ModelAttribute CustomVideoEntity entity,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
        if(entity == null){
            entity = new CustomVideoEntity();
        }
        BasicUtil.startPage();
        List<CustomVideoEntity> b = customVideoBizImpl.getVideo(entity);
        this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(b,(int)BasicUtil.endPage(b).getTotal()),new DoubleValueFilter(),new DateValueFilter("yyyy-MM-dd")));
    }

    @RequestMapping("/form")
    public String form(@ModelAttribute CustomVideoEntity entity, HttpServletResponse response, HttpServletRequest request, ModelMap model){
        if(entity.getId() != null){
            List<CustomVideoEntity> customvideos = customVideoBizImpl.getVideo(entity);
            model.addAttribute("customVideoEntity",customvideos.get(0));
        }
        return view ("/cms/customvideo/form");
    }

    @RequestMapping("/mesindex")
    public String form(@ModelAttribute CustomVideoMessageEntity entity,HttpServletResponse response,HttpServletRequest request,ModelMap model){
        if(entity.getVideoid() != null){
            CustomVideoEntity customVideoEntity = new CustomVideoEntity();
            customVideoEntity.setVideoId(entity.getVideoid());
            List<CustomVideoEntity> customVideoEntitys = customVideoBizImpl.getVideo(customVideoEntity);
            customVideoEntity = customVideoEntitys.get(0);
            model.addAttribute("customVideoEntity",customVideoEntity);
        }
        return view ("/cms/customvideo/message/index");
    }


    @RequestMapping("/meslist")
    @ResponseBody
    public void meslist(@ModelAttribute CustomVideoMessageEntity entity,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
        if(entity == null){
            entity = new CustomVideoMessageEntity();
        }
        BasicUtil.startPage();
        List<CustomVideoEntity> b = customVideoMessageBizImpl.getVideoMessage(entity);
        this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(b,(int)BasicUtil.endPage(b).getTotal()),new DoubleValueFilter(),new DateValueFilter("yyyy-MM-dd")));
    }

    @RequestMapping("/mesform")
    public String mesform(@ModelAttribute CustomVideoMessageEntity entity, HttpServletResponse response, HttpServletRequest request, ModelMap model){
        if(entity.getId() != null){
            List<CustomVideoEntity> customvideos = customVideoMessageBizImpl.getVideoMessage(entity);
            model.addAttribute("customVideoEntity",customvideos.get(0));
        }else {
            model.addAttribute("customVideoEntity",entity);
        }
        return view ("/cms/customvideo/message/form");
    }


    @RequestMapping(value = "/delVideoByIds", method = RequestMethod.POST)
    @ResponseBody
    public void delVideoByIds(@RequestBody List<CustomVideoEntity> entities, HttpServletResponse response, HttpServletRequest request){
        List<Long> rids = new ArrayList<>();
        for(int i = 0;i<entities.size();i++){
            rids.add(entities.get(i).getVideoId());
        }
        Map<String,Object> resmap = new HashMap<>();
        resmap.put("idlist", rids);
        customVideoBizImpl.deleteByIds(resmap);
        this.outJson(response, true);
    }

    @RequestMapping(value = "/getVideo", method = RequestMethod.POST)
    @ResponseBody
    public void getVideo(HttpServletResponse response, HttpServletRequest request, CustomVideoEntity entity){
        List<CustomVideoEntity> b = customVideoBizImpl.getVideo(entity);
        this.outJson(response, b);
    }


    @RequestMapping(value = "/addVideo", method = RequestMethod.POST)
    @ResponseBody
    public void addVideo(HttpServletResponse response, HttpServletRequest request, CustomVideoEntity entity){
        boolean b = customVideoBizImpl.insertVideo(entity);
        this.outJson(response, b);
    }

    @RequestMapping(value = "/delVideo", method = RequestMethod.POST)
    @ResponseBody
    public void delVideo(HttpServletResponse response, HttpServletRequest request,@RequestBody List<CustomVideoEntity> entity){
        for (CustomVideoEntity entity1 : entity){
            customVideoBizImpl.deleteVideo(entity1);
        }
        this.outJson(response, true);
    }

    @RequestMapping(value = "/updateVideo", method = RequestMethod.POST)
    @ResponseBody
    public void updateVideo(HttpServletResponse response, HttpServletRequest request, CustomVideoEntity entity){
        int b = customVideoBizImpl.updateVideo(entity);
        this.outJson(response, b);
    }

    @RequestMapping(value = "/getVideoMes", method = RequestMethod.POST)
    @ResponseBody
    public void getVideoMes(HttpServletResponse response, HttpServletRequest request, CustomVideoMessageEntity entity){
        List<CustomVideoEntity> b = customVideoMessageBizImpl.getVideoMessage(entity);
        this.outJson(response, b);
    }


    @RequestMapping(value = "/addVideoMes", method = RequestMethod.POST)
    @ResponseBody
    public void addVideoMes(HttpServletResponse response, HttpServletRequest request, CustomVideoMessageEntity entity){
        boolean b = customVideoMessageBizImpl.insertVideoMessage(entity);
        this.outJson(response, b);
    }

    @RequestMapping(value = "/delVideoMes", method = RequestMethod.POST)
    @ResponseBody
    public void delVideoMes(HttpServletResponse response, HttpServletRequest request, CustomVideoMessageEntity entity){
        int b = customVideoMessageBizImpl.deleteVideoMessage(entity);
        this.outJson(response, b);
    }

    @RequestMapping(value = "/updateVideoMes", method = RequestMethod.POST)
    @ResponseBody
    public void updateVideoMes(HttpServletResponse response, HttpServletRequest request, CustomVideoMessageEntity entity){
        int b = customVideoMessageBizImpl.updateVideoMessage(entity);
        this.outJson(response, b);
    }

    @RequestMapping(value = "/delVideoMesByIds", method = RequestMethod.POST)
    @ResponseBody
    public void delVideoMesByIds(@RequestBody List<CustomVideoMessageEntity> entities, HttpServletResponse response, HttpServletRequest request){
        List<Integer> rids = new ArrayList<>();
        for(int i = 0;i<entities.size();i++){
            rids.add(entities.get(i).getMessageId());
        }
        Map<String,Object> resmap = new HashMap<>();
        resmap.put("idlist", rids);
        customVideoMessageBizImpl.deleteByIds(resmap);
        this.outJson(response, true);
    }
}

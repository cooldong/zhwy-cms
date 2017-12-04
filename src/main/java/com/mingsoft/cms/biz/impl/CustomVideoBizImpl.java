package com.mingsoft.cms.biz.impl;

import com.mingsoft.cms.biz.CustomVideoBiz;
import com.mingsoft.cms.dao.CustomVideoDao;
import com.mingsoft.cms.dao.CustomVideoMessageDao;
import com.mingsoft.cms.entity.CustomVideoEntity;
import com.mingsoft.cms.entity.CustomVideoMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dt>ProjectName : MCMS </dt>
 * <dt>PakageName : com.mingsoft.cms.biz.impl </dt>
 * <dt>ClassName: CustomVideoBizImpl </dt>
 * <dd>CreateDate: 2017-11-30 16:12 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
@Service
public class CustomVideoBizImpl implements CustomVideoBiz{

    @Autowired
    private CustomVideoDao customVideoDao;

    @Autowired
    private CustomVideoMessageDao customVideoMessageDao;

    @Override
    public boolean insertVideo(CustomVideoEntity entity) {
        int i = customVideoDao.insertSelective(entity);
        return i>0;
    }

    @Override
    public int deleteVideo(CustomVideoEntity entity) {
        return customVideoDao.deleteSelective(entity);
    }

    @Override
    public List getVideo(CustomVideoEntity entity) {
        return customVideoDao.selectSelective(entity);
    }

    @Override
    public int updateVideo(CustomVideoEntity entity) {
        return customVideoDao.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteByIds(Map<String, Object> map) {
        List<Long> vids = (List<Long>) map.get("idlist");
        for (Long vid : vids){
            CustomVideoMessageEntity customVideoMessageEntity = new CustomVideoMessageEntity();
            customVideoMessageEntity.setVideoid(vid);
            customVideoMessageDao.deleteSelective(customVideoMessageEntity);
        }

        return customVideoDao.deleteByIds(map);
    }
}

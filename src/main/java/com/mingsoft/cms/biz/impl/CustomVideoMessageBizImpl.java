package com.mingsoft.cms.biz.impl;

import com.mingsoft.cms.biz.CustomVideoMessageBiz;
import com.mingsoft.cms.dao.CustomVideoMessageDao;
import com.mingsoft.cms.entity.CustomVideoMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dt>ProjectName : MCMS </dt>
 * <dt>PakageName : com.mingsoft.cms.biz.impl </dt>
 * <dt>ClassName: CustomVideoMessageBizImpl </dt>
 * <dd>CreateDate: 2017-11-30 16:22 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
@Service
public class CustomVideoMessageBizImpl implements CustomVideoMessageBiz {

    @Autowired
    CustomVideoMessageDao customVideoMessageDao;

    @Override
    public boolean insertVideoMessage(CustomVideoMessageEntity entity) {
        int i = customVideoMessageDao.insertSelective(entity);
        return i>0;
    }

    @Override
    public int deleteVideoMessage(CustomVideoMessageEntity entity) {
        return customVideoMessageDao.deleteSelective(entity);
    }

    @Override
    public List getVideoMessage(CustomVideoMessageEntity entity) {
        return customVideoMessageDao.selectSelective(entity);
    }

    @Override
    public int updateVideoMessage(CustomVideoMessageEntity entity) {
        return customVideoMessageDao.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteByIds(Map<String, Object> map) {
        return customVideoMessageDao.deleteByIds(map);
    }
}

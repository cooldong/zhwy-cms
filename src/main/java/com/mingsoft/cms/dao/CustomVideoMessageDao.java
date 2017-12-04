package com.mingsoft.cms.dao;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.cms.entity.CustomVideoMessageEntity;

import java.util.List;
import java.util.Map;

public interface CustomVideoMessageDao extends IBaseDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomVideoMessageEntity record);

    int insertSelective(CustomVideoMessageEntity record);

    CustomVideoMessageEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomVideoMessageEntity record);

    int updateByPrimaryKey(CustomVideoMessageEntity record);

    List<CustomVideoMessageEntity> selectSelective(CustomVideoMessageEntity record);

    int deleteSelective(CustomVideoMessageEntity record);

    int deleteByIds(Map<String, Object> map);
}
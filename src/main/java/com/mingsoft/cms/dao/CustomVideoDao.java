package com.mingsoft.cms.dao;


import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.cms.entity.CustomVideoEntity;

import java.util.List;
import java.util.Map;

public interface CustomVideoDao extends IBaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(CustomVideoEntity record);

    int insertSelective(CustomVideoEntity record);

    CustomVideoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomVideoEntity record);

    int updateByPrimaryKey(CustomVideoEntity record);

    List<CustomVideoEntity> selectSelective(CustomVideoEntity record);

    int deleteSelective(CustomVideoEntity record);

    int deleteByIds(Map<String, Object> map);
}
package com.mingsoft.cms.biz;

import com.mingsoft.cms.entity.CustomVideoMessageEntity;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dt>ProjectName : MCMS </dt>
 * <dt>PakageName : com.mingsoft.cms.biz </dt>
 * <dt>ClassName: CustomVideoMessageBiz </dt>
 * <dd>CreateDate: 2017-11-30 15:54 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
public interface CustomVideoMessageBiz {

    boolean insertVideoMessage(CustomVideoMessageEntity entity);

    int deleteVideoMessage(CustomVideoMessageEntity entity);

    List getVideoMessage(CustomVideoMessageEntity entity);

    int updateVideoMessage(CustomVideoMessageEntity entity);

    int deleteByIds(Map<String, Object> map);
}

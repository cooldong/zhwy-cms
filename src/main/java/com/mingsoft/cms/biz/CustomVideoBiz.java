package com.mingsoft.cms.biz;

import com.mingsoft.cms.entity.CustomVideoEntity;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dt>ProjectName : MCMS </dt>
 * <dt>PakageName : com.mingsoft.cms.biz </dt>
 * <dt>ClassName: CustomVideoBiz </dt>
 * <dd>CreateDate: 2017-11-30 15:53 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
public interface CustomVideoBiz {

    boolean insertVideo(CustomVideoEntity entity);

    int deleteVideo(CustomVideoEntity entity);

    List getVideo(CustomVideoEntity entity);

    int updateVideo(CustomVideoEntity entity);

    int deleteByIds(Map<String, Object> map);
}

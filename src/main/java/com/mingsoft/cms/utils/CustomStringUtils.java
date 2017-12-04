package com.mingsoft.cms.utils;

import java.util.Arrays;
import java.util.List;

/**
 * <dl>
 * <dt>ProjectName : MCMS </dt>
 * <dt>PakageName : com.mingsoft.cms.utils </dt>
 * <dt>ClassName: CustomStringUtils </dt>
 * <dd>CreateDate: 2017-12-01 10:46 </dd>
 * <dd>Copyright: Copyright (C) 2015 </dd>
 * <dd>Company: 陕西优百信息技术有限公司 </dd>
 * <dd>Description:            </dd>
 * </dl>
 *
 * @author yanghd
 */
public class CustomStringUtils {

    public static List<Long> convertionToLong(String[] strs){// 将String数组转换为Long类型数组

        Long[] longs = new Long[strs.length]; //声明long类型的数组
        for(int i = 0;i<strs.length;i++) {
            String str = strs[i]; //将strs字符串数组中的第i个值赋值给str
            long thelong = Long.valueOf(str);//将str转换为long类型，并赋值给thelong
            longs[i] = thelong;//将thelong赋值给 longs数组中对应的地方
        }
        return Arrays.asList(longs); //返回long数组
    }

}

package com.aeye.common.utils;

import cn.hsa.hsaf.core.framework.context.HsafContextHolder;
import cn.hsa.hsaf.core.framework.util.CurrentUser;

public class SpringSecurityUtils {

    public static CurrentUser getUser(){
        return HsafContextHolder.getContext().getCurrentUser();
    }

}

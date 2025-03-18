package com.aeye.modules.ht.service;


import com.aeye.modules.ht.entity.HtAppDO;
import com.aeye.modules.ht.entity.HtAppDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统应用
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * @author 沈兴平
 * @date 2024/09/27
 */
public interface HtAppService extends IService<HtAppDO>  {

    List<HtAppDO> getAppVerInfo(String projectId);

}


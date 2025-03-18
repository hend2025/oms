package com.aeye.modules.ht.service;


import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtDictDTO;
import com.aeye.modules.ht.entity.HtDictDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典表
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * @author 沈兴平
 * @date 2024/09/27
 */
public interface HtDictService extends IService<HtDictDO>  {

    public List<HtDictDTO> getAllDic();

}


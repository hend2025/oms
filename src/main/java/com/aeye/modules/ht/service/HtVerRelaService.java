package com.aeye.modules.ht.service;


import com.aeye.modules.ht.dto.HtVerRelaDTO;
import com.aeye.modules.ht.entity.HtVerRelaDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 版本关系表
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * @author 沈兴平
 * @date 2024/09/27
 */
public interface HtVerRelaService extends IService<HtVerRelaDO>  {

    IPage<HtVerRelaDTO> queryVerRela(IPage<HtVerRelaDTO> page, HtVerRelaDTO param);

}


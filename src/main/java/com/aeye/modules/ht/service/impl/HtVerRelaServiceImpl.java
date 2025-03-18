package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.dto.HtVerRelaDTO;
import com.aeye.modules.ht.service.HtVerRelaService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtVerRelaDAO;
import com.aeye.modules.ht.entity.HtVerRelaDO;

import java.util.List;
import java.util.Map;


@Service("htVerRelaService")
public class HtVerRelaServiceImpl extends ServiceImpl<HtVerRelaDAO, HtVerRelaDO> implements HtVerRelaService {

    @Override
    public IPage<HtVerRelaDTO> queryVerRela(IPage<HtVerRelaDTO> page, HtVerRelaDTO param) {
        return baseMapper.queryVerRela(page,param);
    }

}

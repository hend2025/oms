package com.aeye.modules.ht.dao;

import com.aeye.modules.ht.dto.HtVerRelaDTO;
import com.aeye.modules.ht.entity.HtVerRelaDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HtVerRelaDAO extends BaseMapper<HtVerRelaDO> {

    IPage<HtVerRelaDTO> queryVerRela(IPage<HtVerRelaDTO> page, HtVerRelaDTO param);

}

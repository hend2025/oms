package com.aeye.modules.ht.dao;

import com.aeye.modules.ht.dto.HtVerTreeDTO;
import com.aeye.modules.ht.entity.HtVerInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 版本信息
 * @author 沈兴平
 * @date 2024/09/27
 */
@Mapper
public interface HtVerInfoDAO extends BaseMapper<HtVerInfoDO> {

}

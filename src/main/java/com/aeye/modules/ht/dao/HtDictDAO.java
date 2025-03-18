package com.aeye.modules.ht.dao;

import com.aeye.modules.ht.entity.HtDictDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典表
 * @author 沈兴平
 * @date 2024/09/27
 */
@Mapper
public interface HtDictDAO extends BaseMapper<HtDictDO> {
	
}

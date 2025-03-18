package com.aeye.modules.ht.dao;

import com.aeye.modules.ht.entity.HtJobsDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务
 * @author 沈兴平
 * @date 2024/09/27
 */
@Mapper
public interface HtJobsDAO extends BaseMapper<HtJobsDO> {
	
}

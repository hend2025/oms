package com.aeye.modules.ht.dao;

import com.aeye.modules.ht.entity.HtAppDO;
import com.aeye.modules.ht.entity.HtAppDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统应用
 * @author 沈兴平
 * @date 2024/09/27
 */
@Mapper
public interface HtAppDAO extends BaseMapper<HtAppDO> {

    List<HtAppDO> getAppVerInfo(@Param("projectId") String projectId);

}

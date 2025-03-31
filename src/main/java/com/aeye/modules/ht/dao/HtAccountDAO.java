package com.aeye.modules.ht.dao;

import com.aeye.modules.ht.dto.HtAccountSumDTO;
import com.aeye.modules.ht.entity.HtAccountDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface HtAccountDAO extends BaseMapper<HtAccountDO> {

    List<HtAccountSumDTO> accountSum(@Param("beginDate") Date beginDate,
                                     @Param("endDate")   Date endDate,
                                     @Param("searchKey") String searchKey);

}
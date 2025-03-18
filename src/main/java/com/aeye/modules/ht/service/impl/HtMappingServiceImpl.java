package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.dao.HtMappingDAO;
import com.aeye.modules.ht.entity.HtMappingDO;
import com.aeye.modules.ht.service.HtMappingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("htMappingService")
public class HtMappingServiceImpl extends ServiceImpl<HtMappingDAO, HtMappingDO> implements HtMappingService {

}

package com.aeye.modules.ht.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtConfigDAO;
import com.aeye.modules.ht.entity.HtConfigDO;
import com.aeye.modules.ht.service.HtConfigService;


@Service("htConfigService")
public class HtConfigServiceImpl extends ServiceImpl<HtConfigDAO, HtConfigDO> implements HtConfigService {

}

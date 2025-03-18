package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.dao.HtProjectDAO;
import com.aeye.modules.ht.entity.HtProjectDO;
import com.aeye.modules.ht.service.HtProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("htProjectyService")
public class HtProjectServiceImpl extends ServiceImpl<HtProjectDAO, HtProjectDO> implements HtProjectService {

}

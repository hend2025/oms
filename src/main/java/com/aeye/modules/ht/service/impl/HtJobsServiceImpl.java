package com.aeye.modules.ht.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtJobsDAO;
import com.aeye.modules.ht.entity.HtJobsDO;
import com.aeye.modules.ht.service.HtJobsService;


@Service("htJobsService")
public class HtJobsServiceImpl extends ServiceImpl<HtJobsDAO, HtJobsDO> implements HtJobsService {

}

package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.service.HtVerContService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtVerContDAO;
import com.aeye.modules.ht.entity.HtVerContDO;


@Service("htVerContService")
public class HtVerContServiceImpl extends ServiceImpl<HtVerContDAO, HtVerContDO> implements HtVerContService {

}

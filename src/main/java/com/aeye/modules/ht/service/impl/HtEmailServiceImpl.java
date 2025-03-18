package com.aeye.modules.ht.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtEmailDAO;
import com.aeye.modules.ht.entity.HtEmailDO;
import com.aeye.modules.ht.service.HtEmailService;


@Service("htEmailService")
public class HtEmailServiceImpl extends ServiceImpl<HtEmailDAO, HtEmailDO> implements HtEmailService {

}

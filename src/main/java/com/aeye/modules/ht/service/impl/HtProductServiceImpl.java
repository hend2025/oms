package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.dao.HtProductDAO;
import com.aeye.modules.ht.entity.HtProductDO;
import com.aeye.modules.ht.service.HtProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("htProductService")
public class HtProductServiceImpl extends ServiceImpl<HtProductDAO, HtProductDO> implements HtProductService {

}

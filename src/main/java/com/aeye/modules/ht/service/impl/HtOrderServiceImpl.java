package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.dao.HtOrderDAO;
import com.aeye.modules.ht.entity.HtOrderDO;
import com.aeye.modules.ht.service.HtOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HtOrderServiceImpl extends ServiceImpl<HtOrderDAO, HtOrderDO> implements HtOrderService {
}
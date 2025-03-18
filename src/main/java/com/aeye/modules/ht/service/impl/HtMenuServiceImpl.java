package com.aeye.modules.ht.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtMenuDAO;
import com.aeye.modules.ht.entity.HtMenuDO;
import com.aeye.modules.ht.service.HtMenuService;


@Service("htMenuService")
public class HtMenuServiceImpl extends ServiceImpl<HtMenuDAO, HtMenuDO> implements HtMenuService {

}

package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.service.HtSqlService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtSqlDAO;
import com.aeye.modules.ht.entity.HtScriptDO;


@Service("htSqlService")
public class HtSqlServiceImpl extends ServiceImpl<HtSqlDAO, HtScriptDO> implements HtSqlService {

}

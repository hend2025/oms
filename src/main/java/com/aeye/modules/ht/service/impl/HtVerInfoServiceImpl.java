package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.service.HtVerInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aeye.modules.ht.dao.HtVerInfoDAO;
import com.aeye.modules.ht.entity.HtVerInfoDO;


@Service("htVerInfoService")
public class HtVerInfoServiceImpl extends ServiceImpl<HtVerInfoDAO, HtVerInfoDO> implements HtVerInfoService {

}

package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.dao.HtFileDAO;
import com.aeye.modules.ht.entity.HtFileDO;
import com.aeye.modules.ht.service.HtFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("htFileService")
public class HtFileServiceImpl extends ServiceImpl<HtFileDAO, HtFileDO> implements HtFileService {

}

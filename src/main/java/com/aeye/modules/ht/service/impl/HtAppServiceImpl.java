package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.entity.HtAppDO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtAppDAO;
import com.aeye.modules.ht.entity.HtAppDO;
import com.aeye.modules.ht.service.HtAppService;

import java.util.List;


@Service("htAppService")
public class HtAppServiceImpl extends ServiceImpl<HtAppDAO, HtAppDO> implements HtAppService {

    @Override
    public List<HtAppDO> getAppVerInfo(String projectId) {
        return baseMapper.getAppVerInfo(projectId);
    }

}

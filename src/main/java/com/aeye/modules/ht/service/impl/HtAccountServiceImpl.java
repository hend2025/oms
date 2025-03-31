package com.aeye.modules.ht.service.impl;

import com.aeye.modules.ht.dao.HtAccountDAO;
import com.aeye.modules.ht.dto.HtAccountSumDTO;
import com.aeye.modules.ht.entity.HtAccountDO;
import com.aeye.modules.ht.service.HtAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HtAccountServiceImpl extends ServiceImpl<HtAccountDAO, HtAccountDO> implements HtAccountService {

    @Override
    public List<HtAccountSumDTO> accountSum(Date beginDate, Date endDate, String searchKey) {
        return baseMapper.accountSum(beginDate,endDate,searchKey);
    }

}
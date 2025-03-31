package com.aeye.modules.ht.service;

import com.aeye.modules.ht.dto.HtAccountSumDTO;
import com.aeye.modules.ht.entity.HtAccountDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HtAccountService extends IService<HtAccountDO> {

    List<HtAccountSumDTO> accountSum(Date beginDate, Date endDate, String searchKey);

    List<Map>  accountTotal();

}
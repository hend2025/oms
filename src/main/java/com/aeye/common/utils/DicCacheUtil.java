package com.aeye.common.utils;

import com.aeye.modules.ht.dto.HtDictDTO;
import com.aeye.modules.ht.service.HtDictService;

import java.util.ArrayList;
import java.util.List;

public class DicCacheUtil {

    public static List<HtDictDTO> dicCache = new ArrayList<>();

    public static String getDicName(String dicType, String dicCode) {
        if (DicCacheUtil.dicCache.size()==0){
            HtDictService htDictService = SpringContextUtils.getBean(HtDictService.class);
            htDictService.getAllDic();
        }
        HtDictDTO dto = DicCacheUtil.dicCache.stream().filter(bean -> bean.getDicTypeCode().equals(dicType) && bean.getDicCode().equals(dicCode)).findFirst().orElse(null);
        if(dto!=null){
            return dto.getDicName();
        }else{
            return null;
        }
    }

}

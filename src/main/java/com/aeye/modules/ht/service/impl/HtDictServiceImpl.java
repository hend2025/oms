package com.aeye.modules.ht.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.aeye.common.utils.DicCacheUtil;
import com.aeye.modules.ht.dto.HtDictDTO;
import com.aeye.modules.ht.entity.*;
import com.aeye.modules.ht.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.aeye.modules.ht.dao.HtDictDAO;

import java.util.ArrayList;
import java.util.List;


@Service("htDictService")
public class HtDictServiceImpl extends ServiceImpl<HtDictDAO, HtDictDO> implements HtDictService {

    @Autowired
    private HtAppService htAppService;
    @Autowired
    private HtProductService htProductService;
    @Autowired
    private HtProjectService htProjectService;
    @Autowired
    private HtVerInfoService htVerInfoService;


    @Override
    public List<HtDictDTO> getAllDic() {

        List<HtDictDTO> list = new ArrayList<>();

        List<HtDictDO> listHtDictDO = this.list(new LambdaQueryWrapper<HtDictDO>().orderByAsc(HtDictDO::getOrderNum));
        if(CollectionUtil.isNotEmpty(listHtDictDO)) {
            for (HtDictDO bean : listHtDictDO) {
                HtDictDTO dictDTO = new HtDictDTO();
                dictDTO.setDicCode(bean.getDicCode());
                dictDTO.setDicName(bean.getDicName());
                dictDTO.setDicTypeCode(bean.getDicTypeCode());
                dictDTO.setDicTypeName(bean.getDicTypeName());
                list.add(dictDTO);
            }
        }

        List<HtAppDO> listHtAppDO = htAppService.list(new LambdaQueryWrapper<HtAppDO>().orderByAsc(HtAppDO::getOrderNum));
        if(CollectionUtil.isNotEmpty(listHtAppDO)) {
            for (HtAppDO bean : listHtAppDO) {
                HtDictDTO dictDTO = new HtDictDTO();
                dictDTO.setDicCode(bean.getAppCode());
                dictDTO.setDicName(bean.getAppName());
                dictDTO.setDicTypeCode("DIC_APP");
                dictDTO.setDicTypeName("应用");
                list.add(dictDTO);
            }
        }

        List<HtProductDO> listHtProductDO = htProductService.list(new LambdaQueryWrapper<HtProductDO>().orderByAsc(HtProductDO::getOrderNum));
        if(CollectionUtil.isNotEmpty(listHtProductDO)) {
            for (HtProductDO bean : listHtProductDO) {
                HtDictDTO dictDTO = new HtDictDTO();
                dictDTO.setDicCode(bean.getProductId());
                dictDTO.setDicName(bean.getProductName());
                dictDTO.setDicTypeCode("DIC_PRODUCT");
                dictDTO.setDicTypeName("产品");
                list.add(dictDTO);
            }
        }

        List<HtProjectDO> listHtProjectDO = htProjectService.list(new LambdaQueryWrapper<HtProjectDO>().orderByAsc(HtProjectDO::getOrderNum));
        if(CollectionUtil.isNotEmpty(listHtProjectDO)) {
            for (HtProjectDO bean : listHtProjectDO) {
                HtDictDTO dictDTO = new HtDictDTO();
                dictDTO.setDicCode(bean.getProjectId());
                dictDTO.setDicName(bean.getProjectName());
                dictDTO.setDicTypeCode("DIC_PROJECT");
                dictDTO.setDicTypeName("项目");
                list.add(dictDTO);
            }
        }

        List<HtVerInfoDO> listHtVerInfoDO = htVerInfoService.list(new LambdaQueryWrapper<HtVerInfoDO>().orderByAsc(HtVerInfoDO::getVerId));
        if(CollectionUtil.isNotEmpty(listHtVerInfoDO)) {
            for (HtVerInfoDO bean : listHtVerInfoDO) {
                HtDictDTO dictDTO = new HtDictDTO();
                dictDTO.setDicCode(bean.getVerId());
                dictDTO.setDicName(bean.getFirstVerNo());
                dictDTO.setDicTypeCode("DIC_VER");
                dictDTO.setDicTypeName("版本");
                list.add(dictDTO);
            }
        }

        DicCacheUtil.dicCache = list;

        return list;
    }


}

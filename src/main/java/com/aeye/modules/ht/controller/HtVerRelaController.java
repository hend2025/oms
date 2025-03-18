package com.aeye.modules.ht.controller;

import java.util.Arrays;
import java.util.List;

import com.aeye.common.utils.*;
import com.aeye.modules.ht.entity.HtVerInfoDO;
import com.aeye.modules.ht.service.HtVerInfoService;
import com.aeye.modules.ht.service.HtVerRelaService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.entity.HtVerRelaDO;
import com.aeye.modules.ht.dto.HtVerRelaDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 版本关系表
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * 1.只负责参数解析、格式校验、数据对象转换、路由到service或rpc或http到其它地方，最终向用户反馈结果。
 * 2.减少在controller写业务代码，提高和rpc的复用，因为dubbo发布在service层，业务代码到service完成
 * restful格式：/{api|web}/模块名/类名前缀
 * @author 沈兴平
 * @date 2024/09/14
 */
@RestController
@RequestMapping(value = "/web/ht/verrela")
@Api(description="HtVerRela", tags = "版本关系表")
public class HtVerRelaController extends AeyeAbstractController{

    @Autowired
    private HtVerRelaService htVerRelaService;
    @Autowired
    private HtVerInfoService htVerInfoService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtVerRelaDTO>> list(HtVerRelaDTO params) throws Exception {
        if(StringUtils.isNotBlank(params.getProjectId())){
            if(params.getProjectId().length()==11){
                params.setVerId(params.getProjectId());
                params.setProjectId(null);
            }else if(params.getProjectId().length()==2){
                params.setProductId(params.getProjectId());
                params.setProjectId(null);
            }
        }
        IPage<HtVerRelaDTO> page = htVerRelaService.queryVerRela(new Query<HtVerRelaDTO>().getPage(buildPageInfo()),params);
        if(page.getRecords()!=null){
            page.getRecords().stream().forEach(e->{
                e.setVerStatus(htVerInfoService.getById(e.getVerId()).getVerStatus());
            });
        }
        return (WrapperResponse) WrapperResponse.success(page);
    }

    /**
     * 信息
     */
    @RequestMapping(value = "/info/{verRelaId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtVerRelaDTO> info(@PathVariable("verRelaId") Integer verRelaId) throws Exception{
        HtVerRelaDO htVerRela = htVerRelaService.getById(verRelaId);

        return (WrapperResponse)WrapperResponse.success(htVerRela);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtVerRelaDTO> save(HtVerRelaDTO htVerRela) throws Exception{
        htVerRela.setProductId(htVerRela.getVerId().substring(0,2));
        htVerRela.setProjectId(htVerRela.getVerId().substring(0,5));
        int cnt = htVerRelaService.count(new LambdaQueryWrapper<HtVerRelaDO>()
                        .eq(HtVerRelaDO::getVerId,htVerRela.getVerId())
                        .eq(HtVerRelaDO::getAppCode,htVerRela.getAppCode()));
        if (cnt>0){
            return (WrapperResponse)WrapperResponse.fail("该应用已存在！",null);
        }
         htVerRelaService.save(HtVerRelaDO.copyBean(htVerRela));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtVerRelaDTO> update(HtVerRelaDTO htVerRela) throws Exception{
        htVerRela.setProductId(htVerRela.getVerId().substring(0,2));
        htVerRela.setProjectId(htVerRela.getVerId().substring(0,5));
        htVerRelaService.updateById(HtVerRelaDO.copyBean(htVerRela));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Integer[] verRelaIds) throws Exception{
        htVerRelaService.removeByIds(Arrays.asList(verRelaIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{verRelaId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("verRelaId") Integer verRelaId) throws Exception{
        htVerRelaService.removeById(verRelaId);

        return WrapperResponse.success(null);
    }
}

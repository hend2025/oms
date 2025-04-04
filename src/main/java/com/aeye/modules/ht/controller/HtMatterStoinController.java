package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.AeyePageInfo;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtMatterStoinDTO;
import com.aeye.modules.ht.entity.HtMatterDO;
import com.aeye.modules.ht.entity.HtMatterStoinDO;
import com.aeye.modules.ht.service.HtCategoryService;
import com.aeye.modules.ht.service.HtMatterStoinService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "物料入库管理")
@RestController
@RequestMapping("/ht/matterStoin")
public class HtMatterStoinController extends AeyeAbstractController {
    
    @Autowired
    private HtMatterStoinService htMatterStoinService;
    @Autowired
    private HtCategoryService htCategoryService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtMatterStoinDTO>> list(HtMatterStoinDTO params) throws Exception{
        IPage<HtMatterStoinDO> page = htMatterStoinService.page(
                new Query<HtMatterStoinDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtMatterStoinDO>()
                        .eq(StringUtils.isNotBlank(params.getCategoryName()),HtMatterStoinDO::getCategoryName,params.getCategoryName())
                        .eq(StringUtils.isNotBlank(params.getOrgName()),HtMatterStoinDO::getOrgName,params.getOrgName())
                        .eq(params.getCategoryId()!=null,HtMatterStoinDO::getCategoryId,params.getCategoryId())
                        .eq(params.getOrgId()!=null,HtMatterStoinDO::getOrgId,params.getOrgId())
                        .ge(params.getStartDate()!=null,HtMatterStoinDO::getStoinDate,params.getStartDate())
                        .le(params.getEndDate()!=null,HtMatterStoinDO::getStoinDate,params.getEndDate())
                        .and(StringUtils.isNotBlank(params.getSearchKey()),
                                wrapper -> wrapper.like(HtMatterStoinDO::getCategoryName, params.getSearchKey())
                                        .or().like(HtMatterStoinDO::getOrgName, params.getSearchKey())
                                        .or().like(HtMatterStoinDO::getOrgCode, params.getSearchKey())
                        )
        );
        return (WrapperResponse)WrapperResponse.success(page);
    }

    @RequestMapping(value = "/info/{appCode}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtMatterStoinDTO> info(@PathVariable("appCode") String appCode) throws Exception{
        HtMatterStoinDO htMatterStoin = htMatterStoinService.getById(appCode);
        htMatterStoin.setCategoryName(htCategoryService.getById(htMatterStoin.getCategoryId()).getCategoryName());
        return (WrapperResponse)WrapperResponse.success(htMatterStoin);
    }

    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtMatterStoinDTO> save(HtMatterStoinDTO htMatterStoin) throws Exception{
        htMatterStoinService.save(HtMatterStoinDO.copyBean(htMatterStoin));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtMatterStoinDTO> update(HtMatterStoinDTO htMatterStoin) throws Exception{
        htMatterStoinService.updateById(HtMatterStoinDO.copyBean(htMatterStoin));

        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody String[] appCodes) throws Exception{
        htMatterStoinService.removeByIds(Arrays.asList(appCodes));

        return WrapperResponse.success(null);
    }

    @RequestMapping(value = "/delete/{appCode}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("appCode") String appCode) throws Exception{
        htMatterStoinService.removeById(appCode);

        return WrapperResponse.success(null);
    }

}
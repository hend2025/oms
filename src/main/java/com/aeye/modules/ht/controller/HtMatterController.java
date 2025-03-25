package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtMatterDTO;
import com.aeye.modules.ht.entity.HtMatterDO;
import com.aeye.modules.ht.service.HtMatterService;
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

@RestController
@RequestMapping("/ht/matter")
@Api(tags = "物料管理")
public class HtMatterController extends AeyeAbstractController {
    
    @Autowired
    private HtMatterService htMatterService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtMatterDTO>> list(HtMatterDTO params) throws Exception {
        IPage<HtMatterDO> page = htMatterService.page(
                new Query<HtMatterDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtMatterDO>()
                        .eq(StringUtils.isNotBlank(params.getCategoryCode()), HtMatterDO::getCategoryCode, params.getCategoryCode())
                        .and(StringUtils.isNotBlank(params.getKeyword()),
                                wrapper -> wrapper.like(HtMatterDO::getMatterCode, params.getKeyword())
                                        .or()
                                        .like(HtMatterDO::getMatterName, params.getKeyword()))
                        .orderByAsc(HtMatterDO::getOrderNum)
        );
        return (WrapperResponse)WrapperResponse.success(page);
    }

    @RequestMapping(value = "/info/{matterId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtMatterDTO> info(@PathVariable("matterId") Integer matterId) throws Exception {
        HtMatterDO matter = htMatterService.getById(matterId);
        return (WrapperResponse)WrapperResponse.success(matter);
    }

    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtMatterDTO> save(HtMatterDTO matterDTO) throws Exception {
        htMatterService.save(HtMatterDO.copyBean(matterDTO));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtMatterDTO> update(HtMatterDTO matterDTO) throws Exception {
        htMatterService.updateById(HtMatterDO.copyBean(matterDTO));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Integer[] matterIds) throws Exception {
        htMatterService.removeByIds(Arrays.asList(matterIds));
        return WrapperResponse.success(null);
    }

    @RequestMapping(value = "/delete/{matterId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("matterId") Integer matterId) throws Exception {
        htMatterService.removeById(matterId);
        return WrapperResponse.success(null);
    }

}
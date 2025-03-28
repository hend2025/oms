package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtOrgDTO;
import com.aeye.modules.ht.entity.HtOrgDO;
import com.aeye.modules.ht.service.HtOrgService;
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
@RequestMapping("/ht/org")
@Api(tags = "机构管理")
public class HtOrgController extends AeyeAbstractController {
    
    @Autowired
    private HtOrgService htOrgService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtOrgDTO>> list(HtOrgDTO params) throws Exception {
        IPage<HtOrgDO> page = htOrgService.page(
                new Query<HtOrgDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtOrgDO>()
                        .and(StringUtils.isNotBlank(params.getKeyword()),
                                wrapper -> wrapper.like(HtOrgDO::getOrgCode, params.getKeyword())
                                        .or()
                                        .like(HtOrgDO::getOrgName, params.getKeyword()))
        );
        return (WrapperResponse)WrapperResponse.success(page);
    }

    @RequestMapping(value = "/info/{orgId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtOrgDTO> info(@PathVariable("orgId") Long orgId) throws Exception {
        HtOrgDO org = htOrgService.getById(orgId);
        return (WrapperResponse)WrapperResponse.success(org);
    }

    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtOrgDTO> save(HtOrgDTO orgDTO) throws Exception {
        htOrgService.save(HtOrgDO.copyBean(orgDTO));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtOrgDTO> update(HtOrgDTO orgDTO) throws Exception {
        htOrgService.updateById(HtOrgDO.copyBean(orgDTO));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Long[] orgIds) throws Exception {
        htOrgService.removeByIds(Arrays.asList(orgIds));
        return WrapperResponse.success(null);
    }

    @RequestMapping(value = "/delete/{orgId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("orgId") Long orgId) throws Exception {
        htOrgService.removeById(orgId);
        return WrapperResponse.success(null);
    }
}
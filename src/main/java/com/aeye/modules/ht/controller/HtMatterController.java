package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.AeyePageInfo;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtMatterDTO;
import com.aeye.modules.ht.entity.HtMatterDO;
import com.aeye.modules.ht.service.IHtMatterService;
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

@Api(tags = "物料分类管理")
@RestController
@RequestMapping("/ht/matterCategory")
public class HtMatterController extends AeyeAbstractController {
    
    @Autowired
    private IHtMatterService htMatterService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtMatterDTO>> list(HtMatterDTO params) throws Exception{

        IPage<HtMatterDO> page = htMatterService.page(
                new Query<HtMatterDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtMatterDO>()
                        .eq(StringUtils.isNotBlank(params.getParentCode()), HtMatterDO::getParentCode, params.getParentCode())
                        .and(StringUtils.isNotBlank(params.getKeyword()),
                                wrapper -> wrapper.like(HtMatterDO::getMatterCode, params.getKeyword())
                                        .or()
                                        .like(HtMatterDO::getMatterName, params.getKeyword()))
                        .orderByAsc(HtMatterDO::getOrderNum)
        );
        return (WrapperResponse)WrapperResponse.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{appCode}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtMatterDTO> info(@PathVariable("appCode") String appCode) throws Exception{
        HtMatterDO htMatterStoin = htMatterService.getById(appCode);

        return (WrapperResponse)WrapperResponse.success(htMatterStoin);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtMatterDTO> save(HtMatterDTO htMatterStoin) throws Exception{
        htMatterService.save(HtMatterDO.copyBean(htMatterStoin));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtMatterDTO> update(HtMatterDTO htMatterStoin) throws Exception{
        htMatterService.updateById(HtMatterDO.copyBean(htMatterStoin));

        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody String[] appCodes) throws Exception{
        htMatterService.removeByIds(Arrays.asList(appCodes));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{appCode}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("appCode") String appCode) throws Exception{
        htMatterService.removeById(appCode);

        return WrapperResponse.success(null);
    }

}
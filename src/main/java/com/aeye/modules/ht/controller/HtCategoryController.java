package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.AeyeBeanUtils;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtCategoryDTO;
import com.aeye.modules.ht.entity.HtCategoryDO;
import com.aeye.modules.ht.service.HtCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ht/category")
@Api(tags = "分类管理")
public class HtCategoryController extends AeyeAbstractController {
    
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
    public WrapperResponse<List<HtCategoryDTO>> list(HtCategoryDTO params) throws Exception{

        IPage<HtCategoryDO> page = htCategoryService.page(
                new Query<HtCategoryDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtCategoryDO>()
                        .eq(StringUtils.isNotBlank(params.getParentCode()), HtCategoryDO::getParentCode, params.getParentCode())
                        .and(StringUtils.isNotBlank(params.getKeyword()),
                                wrapper -> wrapper.like(HtCategoryDO::getCategoryCode, params.getKeyword())
                                        .or()
                                        .like(HtCategoryDO::getCategoryName, params.getKeyword()))
                        .orderByAsc(HtCategoryDO::getOrderNum)
        );
        for(HtCategoryDO bean : page.getRecords()){
            int childNum = htCategoryService.count(new LambdaQueryWrapper<HtCategoryDO>().eq(HtCategoryDO::getParentCode,bean.getCategoryCode()));
            bean.setChildNum(childNum);
        }
        return (WrapperResponse)WrapperResponse.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{appCode}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtCategoryDTO> info(@PathVariable("appCode") String appCode) throws Exception{
        HtCategoryDO htMatterStoin = htCategoryService.getById(appCode);

        return (WrapperResponse)WrapperResponse.success(htMatterStoin);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtCategoryDTO> save(HtCategoryDTO htMatterStoin) throws Exception{
        htCategoryService.save(HtCategoryDO.copyBean(htMatterStoin));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtCategoryDTO> update(HtCategoryDTO htMatterStoin) throws Exception{
        htCategoryService.updateById(HtCategoryDO.copyBean(htMatterStoin));

        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody String[] appCodes) throws Exception{
        htCategoryService.removeByIds(Arrays.asList(appCodes));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{appCode}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("appCode") String appCode) throws Exception{
        htCategoryService.removeById(appCode);

        return WrapperResponse.success(null);
    }
    
}
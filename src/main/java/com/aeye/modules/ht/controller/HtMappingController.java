package com.aeye.modules.ht.controller;

import cn.hutool.core.util.ObjectUtil;
import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtMappingDTO;
import com.aeye.modules.ht.entity.HtJobsDO;
import com.aeye.modules.ht.entity.HtMappingDO;
import com.aeye.modules.ht.service.HtMappingService;
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
@RequestMapping(value = "/web/ht/mapping")
@Api(description="HtMapping", tags = "系统应用")
public class HtMappingController extends AeyeAbstractController {

    @Autowired
    private HtMappingService HtMappingService;

    /**
     * 列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtMappingDTO>> list(HtMappingDTO params) throws Exception{

        IPage<HtMappingDO> page = HtMappingService.page(
                new Query<HtMappingDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtMappingDO>()
                        .eq(StringUtils.isNotBlank(params.getProjectId()), HtMappingDO::getProjectId,params.getProjectId())
                        .eq(StringUtils.isNotBlank(params.getProductId()), HtMappingDO::getProductId,params.getProductId())
                        .eq(StringUtils.isNotBlank(params.getVerId()), HtMappingDO::getProductId,params.getVerId())
        );
        return (WrapperResponse)WrapperResponse.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{appCode}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtMappingDTO> info(@PathVariable("appCode") String appCode) throws Exception{
        HtMappingDO HtMapping = HtMappingService.getById(appCode);

        return (WrapperResponse)WrapperResponse.success(HtMapping);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtMappingDTO> save(HtMappingDTO HtMapping) throws Exception{
        HtMappingService.save(HtMappingDO.copyBean(HtMapping));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtMappingDTO> update(HtMappingDTO HtMapping) throws Exception{
        HtMappingService.updateById(HtMappingDO.copyBean(HtMapping));
        
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody String[] appCodes) throws Exception{
        HtMappingService.removeByIds(Arrays.asList(appCodes));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{appCode}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("appCode") String appCode) throws Exception{
        HtMappingService.removeById(appCode);

        return WrapperResponse.success(null);
    }

}

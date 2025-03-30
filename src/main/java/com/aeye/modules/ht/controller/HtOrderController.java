package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtMatterStoinDTO;
import com.aeye.modules.ht.dto.HtOrderDTO;
import com.aeye.modules.ht.entity.HtMatterStoinDO;
import com.aeye.modules.ht.entity.HtOrderDO;
import com.aeye.modules.ht.service.HtOrderService;
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
@RequestMapping("/ht/order")
@Api(tags = "订单管理")
public class HtOrderController extends AeyeAbstractController {
    
    @Autowired
    private HtOrderService htOrderService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtOrderDTO>> list(HtOrderDTO params) throws Exception {
        IPage<HtOrderDO> page = htOrderService.page(
                new Query<HtOrderDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtOrderDO>()
                        .eq(StringUtils.isNotBlank(params.getCategoryName()),HtOrderDO::getCategoryName,params.getCategoryName())
                        .eq(StringUtils.isNotBlank(params.getOrgName()),HtOrderDO::getOrgName,params.getOrgName())
                        .eq(params.getCategoryId()!=null,HtOrderDO::getCategoryId,params.getCategoryId())
                        .eq(params.getOrgId()!=null,HtOrderDO::getOrgId,params.getOrgId())
                        .ge(params.getStartDate()!=null,HtOrderDO::getOrderDate,params.getStartDate())
                        .le(params.getEndDate()!=null,HtOrderDO::getOrderDate,params.getEndDate())
                        .and(StringUtils.isNotBlank(params.getSearchKey()),
                                wrapper -> wrapper.like(HtOrderDO::getCategoryName, params.getSearchKey())
                                        .or().like(HtOrderDO::getOrgName, params.getSearchKey())
                                        .or().like(HtOrderDO::getOrgCode, params.getSearchKey())
                        )
        );
        return (WrapperResponse)WrapperResponse.success(page);
    }

    @RequestMapping(value = "/info/{orderId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtOrderDTO> info(@PathVariable("orderId") Long orderId) throws Exception {
        HtOrderDO order = htOrderService.getById(orderId);
        return (WrapperResponse)WrapperResponse.success(order);
    }

    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtOrderDTO> save(HtOrderDTO orderDTO) throws Exception {
        htOrderService.save(HtOrderDO.copyBean(orderDTO));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtOrderDTO> update(HtOrderDTO orderDTO) throws Exception {
        htOrderService.updateById(HtOrderDO.copyBean(orderDTO));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Long[] orderIds) throws Exception {
        htOrderService.removeByIds(Arrays.asList(orderIds));
        return WrapperResponse.success(null);
    }

    @RequestMapping(value = "/delete/{orderId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("orderId") Long orderId) throws Exception {
        htOrderService.removeById(orderId);
        return WrapperResponse.success(null);
    }
}
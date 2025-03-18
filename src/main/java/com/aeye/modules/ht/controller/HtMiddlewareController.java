package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtMiddlewareDTO;
import com.aeye.modules.ht.entity.HtMiddlewareDO;
import com.aeye.modules.ht.service.HtMiddlewareService;
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
@RequestMapping(value = "/web/ht/middleware")
@Api(description="middleware", tags = "中间件")
public class HtMiddlewareController extends AeyeAbstractController {

    @Autowired
    private HtMiddlewareService htMiddlewareService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtMiddlewareDTO>> list(HtMiddlewareDTO params) throws Exception{

        IPage<HtMiddlewareDO> page = htMiddlewareService.page(
                new Query<HtMiddlewareDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtMiddlewareDO>()
                        .eq(StringUtils.isNotBlank(params.getProjectId()), HtMiddlewareDO::getProjectId,params.getProjectId())
                        .eq(StringUtils.isNotBlank(params.getProductId()), HtMiddlewareDO::getProductId,params.getProductId())
//                        .eq(StringUtils.isNotBlank(params.getVerId()), HtMiddlewareDO::getProductId,params.getVerId())
        );

        return (WrapperResponse)WrapperResponse.success(page);
    }

    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtMiddlewareDTO> save(HtMiddlewareDTO HtMiddleware) throws Exception{
        htMiddlewareService.save(HtMiddlewareDO.copyBean(HtMiddleware));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtMiddlewareDTO> update(HtMiddlewareDTO HtMiddleware) throws Exception{
        htMiddlewareService.updateById(HtMiddlewareDO.copyBean(HtMiddleware));

        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody String[] appCodes) throws Exception{
        htMiddlewareService.removeByIds(Arrays.asList(appCodes));

        return WrapperResponse.success(null);
    }

    @RequestMapping(value = "/delete/{middlewareId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("middlewareId") String middlewareId) throws Exception{
        htMiddlewareService.removeById(middlewareId);
        return WrapperResponse.success(null);
    }

}

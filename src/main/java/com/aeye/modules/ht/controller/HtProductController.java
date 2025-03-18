package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtMappingDTO;
import com.aeye.modules.ht.dto.HtProductDTO;
import com.aeye.modules.ht.entity.HtMappingDO;
import com.aeye.modules.ht.entity.HtProductDO;
import com.aeye.modules.ht.entity.HtVerInfoDO;
import com.aeye.modules.ht.service.HtMappingService;
import com.aeye.modules.ht.service.HtProductService;
import com.baomidou.mybatisplus.annotation.TableId;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/web/ht/product")
@Api(description="HtProduct", tags = "产品")
public class HtProductController extends AeyeAbstractController{

    @Autowired
    private HtProductService htProductService;

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
    @RequestMapping(value = "/list",method = {RequestMethod.POST})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtProductDTO>> list(HtProductDTO params) throws Exception{
        IPage<HtProductDO> page = htProductService.page(
                new Query<HtProductDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtProductDO>()
        );
        for (HtProductDO bean : page.getRecords()){
           List<HtMappingDO> list = HtMappingService.list(new LambdaQueryWrapper<HtMappingDO>().eq(HtMappingDO::getProductId,bean.getProductId()).eq(HtMappingDO::getProjectId,0L));
            if (list!=null){
                List<String> array = list.stream().map(HtMappingDO::getAppCode).collect(Collectors.toList());
                bean.setAppCode(String.join(",",array));
           }
        }
        return (WrapperResponse)WrapperResponse.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{ProjectId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtProductDTO> info(@PathVariable("ProjectId") Integer ProjectId) throws Exception{
        HtProductDO htProduct = htProductService.getById(ProjectId);

        return (WrapperResponse)WrapperResponse.success(htProduct);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtProductDTO> save(HtProductDTO htProduct) throws Exception{
        if(StringUtils.isBlank(htProduct.getProductName())) {
            return (WrapperResponse) WrapperResponse.fail("产品名称不能为空！", null);
        }
        htProduct.setProductName(htProduct.getProductName().trim());
        int cnt = htProductService.count(new LambdaQueryWrapper<HtProductDO>().eq(HtProductDO::getProductName,htProduct.getProductName()));
        if(cnt>0) {
            return (WrapperResponse) WrapperResponse.fail("产品名称已存在！", null);
        }
        HtProductDO temp = htProductService.getOne(new LambdaQueryWrapper<HtProductDO>().orderByDesc(HtProductDO::getProductId).last("limit 1"));
        long maxId = (temp==null?10:Long.parseLong(temp.getProductId())+1);
        htProduct.setProductId(String.valueOf(maxId));
        htProductService.save(HtProductDO.copyBean(htProduct));

        String[] app = htProduct.getAppCode().split(",");
        for (String s : app){
            HtMappingDO bean = new HtMappingDO();
            bean.setAppCode(s);
            bean.setProductId(htProduct.getProductId());
            HtMappingService.save(bean);
        }

        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtProductDTO> update(HtProductDTO htProduct) throws Exception{
        htProductService.updateById(HtProductDO.copyBean(htProduct));
        HtMappingService.remove(new LambdaQueryWrapper<HtMappingDO>().eq(HtMappingDO::getProductId,htProduct.getProductId()));
        String[] app = htProduct.getAppCode().split(",");
        for (String s : app){
            HtMappingDO bean = new HtMappingDO();
            bean.setAppCode(s);
            bean.setProductId(htProduct.getProductId());
            HtMappingService.save(bean);
        }
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Integer[] ProjectIds) throws Exception{
        htProductService.removeByIds(Arrays.asList(ProjectIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{ProjectId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("ProjectId") Integer ProjectId) throws Exception{
        htProductService.removeById(ProjectId);

        return WrapperResponse.success(null);
    }
}

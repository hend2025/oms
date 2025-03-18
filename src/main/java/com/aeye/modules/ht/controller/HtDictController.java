package com.aeye.modules.ht.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import com.aeye.common.utils.*;
import com.aeye.modules.ht.entity.*;
import com.aeye.modules.ht.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.dto.HtDictDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/web/ht/dict")
@Api(description="HtDict", tags = "数据字典表")
public class HtDictController extends AeyeAbstractController{

    @Autowired
    private HtDictService htDictService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtDictDTO>> list(HtDictDTO params) throws Exception{
        //请求头获取分页参数
        IPage<HtDictDO> page = htDictService.page(
                new Query<HtDictDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtDictDO>()
                    .like(StringUtils.isNotBlank(params.getKeyWord()),HtDictDO::getDicCode,params.getKeyWord())
                    .or()
                    .like(StringUtils.isNotBlank(params.getKeyWord()),HtDictDO::getDicName,params.getKeyWord())
                    .or()
                    .like(StringUtils.isNotBlank(params.getKeyWord()),HtDictDO::getDicTypeCode,params.getKeyWord())
                    .or()
                    .like(StringUtils.isNotBlank(params.getKeyWord()),HtDictDO::getDicTypeName,params.getKeyWord())

        );

        return (WrapperResponse)WrapperResponse.success(page);
    }

    @RequestMapping(value = "/getAllDic",method = {RequestMethod.POST})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtDictDTO>> getAllDic() throws Exception{
        List<HtDictDTO> list = htDictService.getAllDic();
        return (WrapperResponse)WrapperResponse.success(list);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{dicId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtDictDTO> info(@PathVariable("dicId") String dicId) throws Exception{
        HtDictDO htDict = htDictService.getById(dicId);

        return (WrapperResponse)WrapperResponse.success(htDict);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtDictDTO> save(HtDictDTO htDict) throws Exception{
        htDict.setDicId(htDict.getDicTypeCode()+"_"+htDict.getDicCode());
        htDictService.save(HtDictDO.copyBean(htDict));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtDictDTO> update(HtDictDTO htDict) throws Exception{
        htDictService.updateById(HtDictDO.copyBean(htDict));
        
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody String[] dicIds) throws Exception{
        htDictService.removeByIds(Arrays.asList(dicIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{dicId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("dicId") String dicId) throws Exception{
        htDictService.removeById(dicId);

        return WrapperResponse.success(null);
    }
}

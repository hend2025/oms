package com.aeye.modules.ht.controller;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.service.HtSqlService;
import com.aeye.modules.ht.service.HtVerInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.entity.HtScriptDO;
import com.aeye.modules.ht.dto.HtScriptDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/web/ht/script")
@Api(description="HtSql", tags = "脚本")
public class HtScriptController extends AeyeAbstractController{
    @Autowired
    private HtSqlService htSqlService;
    @Autowired
    private HtVerInfoService htVerInfoService;

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
    public WrapperResponse<List<HtScriptDTO>> list(HtScriptDTO params) throws Exception{
        if(StringUtils.isNotBlank(params.getProjectId())){
            if(params.getProjectId().length()==11){
                params.setVerId(params.getProjectId());
                params.setProjectId(null);
            }else if(params.getProjectId().length()==2){
                params.setProductId(params.getProjectId());
                params.setProjectId(null);
            }
        }
        if(StringUtils.isNotBlank(params.getVerId())){
            params.setVerId(params.getVerId().substring(0,9));
        }
        IPage<HtScriptDO> page = htSqlService.page(
                new Query<HtScriptDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtScriptDO>()
                        .like(StringUtils.isNotBlank(params.getKeyWord()), HtScriptDO::getScriptText,params.getKeyWord())
                        .like(ObjectUtil.isNotEmpty(params.getVerId()), HtScriptDO::getVerId,params.getVerId())
                        .eq(StringUtils.isNotBlank(params.getProjectId()), HtScriptDO::getProjectId,params.getProjectId())
                        .eq(StringUtils.isNotBlank(params.getProductId()), HtScriptDO::getProductId,params.getProductId())
                        .orderByDesc(HtScriptDO::getVerId).orderByAsc(HtScriptDO::getCodeType)
        );
        if(page.getRecords()!=null){
            page.getRecords().stream().forEach(e->{
                e.setVerStatus(htVerInfoService.getById(e.getVerId()).getVerStatus());
            });
        }
        return (WrapperResponse)WrapperResponse.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{sqlId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtScriptDTO> info(@PathVariable("sqlId") Integer sqlId) throws Exception{
        HtScriptDO htSql = htSqlService.getById(sqlId);

        return (WrapperResponse)WrapperResponse.success(htSql);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtScriptDTO> save(HtScriptDTO htSql) throws Exception{
        htSql.setProductId(htSql.getVerId().substring(0,2));
        htSql.setProjectId(htSql.getVerId().substring(0,5));
        htSqlService.save(HtScriptDO.copyBean(htSql));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtScriptDTO> update(HtScriptDTO htSql) throws Exception{
        htSql.setProductId(htSql.getVerId().substring(0,2));
        htSql.setProjectId(htSql.getVerId().substring(0,5));
        htSqlService.updateById(HtScriptDO.copyBean(htSql));
        
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Integer[] sqlIds) throws Exception{
        htSqlService.removeByIds(Arrays.asList(sqlIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{sqlId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("sqlId") Integer sqlId) throws Exception{
        htSqlService.removeById(sqlId);

        return WrapperResponse.success(null);
    }

}

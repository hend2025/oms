package com.aeye.modules.ht.controller;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.entity.HtJobsDO;
import com.aeye.modules.ht.service.HtConfigService;
import com.aeye.modules.ht.service.HtVerInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.entity.HtConfigDO;
import com.aeye.modules.ht.dto.HtConfigDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 配置信息
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * 1.只负责参数解析、格式校验、数据对象转换、路由到service或rpc或http到其它地方，最终向用户反馈结果。
 * 2.减少在controller写业务代码，提高和rpc的复用，因为dubbo发布在service层，业务代码到service完成
 * restful格式：/{api|web}/模块名/类名前缀
 * @author 沈兴平
 * @date 2024/09/14
 */
@RestController
@RequestMapping(value = "/web/ht/config")
@Api(description="HtConfig", tags = "配置信息")
public class HtConfigController extends AeyeAbstractController {
    @Autowired
    private HtConfigService htConfigService;
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
    public WrapperResponse<List<HtConfigDTO>> list(HtConfigDTO params) throws Exception{
        if(StringUtils.isNotBlank(params.getProjectId())){
            if(params.getProjectId().length()==11){
                params.setVerId(params.getProjectId());
                params.setProjectId(null);
            }else if(params.getProjectId().length()==2){
                params.setProductId(params.getProjectId());
                params.setProjectId(null);
            }
        }
        IPage<HtConfigDO> page = htConfigService.page(
                new Query<HtConfigDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtConfigDO>()
                        .like(StringUtils.isNotBlank(params.getKeyWord()),HtConfigDO::getYamlText,params.getKeyWord())
                        .or()
                        .like(StringUtils.isNotBlank(params.getKeyWord()),HtConfigDO::getServiceName,params.getKeyWord())
                        .eq(ObjectUtil.isNotEmpty(params.getVerId()), HtConfigDO::getVerId,params.getVerId())
                        .eq(StringUtils.isNotBlank(params.getProjectId()), HtConfigDO::getProjectId,params.getProjectId())
                        .eq(StringUtils.isNotBlank(params.getProductId()), HtConfigDO::getProductId,params.getProductId())
                        .orderByDesc(HtConfigDO::getVerId)
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
    @RequestMapping(value = "/info/{configId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtConfigDTO> info(@PathVariable("configId") Integer configId) throws Exception{
        HtConfigDO htConfig = htConfigService.getById(configId);

        return (WrapperResponse)WrapperResponse.success(htConfig);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtConfigDTO> save(HtConfigDTO htConfig) throws Exception{
        htConfig.setProductId(htConfig.getVerId().substring(0,2));
        htConfig.setProjectId(htConfig.getVerId().substring(0,5));
        htConfigService.save(HtConfigDO.copyBean(htConfig));

        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtConfigDTO> update(HtConfigDTO htConfig) throws Exception{
        htConfig.setProductId(htConfig.getVerId().substring(0,2));
        htConfig.setProjectId(htConfig.getVerId().substring(0,5));
        htConfigService.updateById(HtConfigDO.copyBean(htConfig));
        
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Integer[] configIds) throws Exception{
        htConfigService.removeByIds(Arrays.asList(configIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{configId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("configId") Integer configId) throws Exception{
        htConfigService.removeById(configId);

        return WrapperResponse.success(null);
    }
}

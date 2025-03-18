package com.aeye.modules.ht.controller;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.entity.HtVerContDO;
import com.aeye.modules.ht.service.HtJobsService;
import com.aeye.modules.ht.service.HtVerInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.entity.HtJobsDO;
import com.aeye.modules.ht.dto.HtJobsDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * 1.只负责参数解析、格式校验、数据对象转换、路由到service或rpc或http到其它地方，最终向用户反馈结果。
 * 2.减少在controller写业务代码，提高和rpc的复用，因为dubbo发布在service层，业务代码到service完成
 * restful格式：/{api|web}/模块名/类名前缀
 * @author 沈兴平
 * @date 2024/09/14
 */
@RestController
@RequestMapping(value = "/web/ht/jobs")
@Api(description="HtJobs", tags = "定时任务")
public class HtJobsController extends AeyeAbstractController{
    @Autowired
    private HtJobsService htJobsService;
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
    public WrapperResponse<List<HtJobsDTO>> list(HtJobsDTO params) throws Exception{
        if(StringUtils.isNotBlank(params.getProjectId())){
            if(params.getProjectId().length()==11){
                params.setVerId(params.getProjectId());
                params.setProjectId(null);
            }else if(params.getProjectId().length()==2){
                params.setProductId(params.getProjectId());
                params.setProjectId(null);
            }
        }
        IPage<HtJobsDO> page = htJobsService.page(
                new Query<HtJobsDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtJobsDO>()
                        .like(StringUtils.isNotBlank(params.getKeyWord()), HtJobsDO::getJobName,params.getKeyWord())
                        .or()
                        .like(StringUtils.isNotBlank(params.getKeyWord()),HtJobsDO::getJobDesc,params.getKeyWord())
                        .eq(ObjectUtil.isNotEmpty(params.getVerId()), HtJobsDO::getVerId,params.getVerId())
                        .eq(StringUtils.isNotBlank(params.getProjectId()), HtJobsDO::getProjectId,params.getProjectId())
                        .eq(StringUtils.isNotBlank(params.getProductId()), HtJobsDO::getProductId,params.getProductId())
                        .eq(StringUtils.isNotBlank(params.getServiceName()), HtJobsDO::getServiceName,params.getServiceName())
                        .orderByDesc(HtJobsDO::getVerId)
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
    @RequestMapping(value = "/info/{jobId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtJobsDTO> info(@PathVariable("jobId") Integer jobId) throws Exception{
        HtJobsDO htJobs = htJobsService.getById(jobId);

        return (WrapperResponse)WrapperResponse.success(htJobs);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtJobsDTO> save(HtJobsDTO htJobs) throws Exception{
        htJobs.setProductId(htJobs.getVerId().substring(0,2));
        htJobs.setProjectId(htJobs.getVerId().substring(0,5));
        htJobsService.save(HtJobsDO.copyBean(htJobs));

        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtJobsDTO> update(HtJobsDTO htJobs) throws Exception{
        htJobs.setProductId(htJobs.getVerId().substring(0,2));
        htJobs.setProjectId(htJobs.getVerId().substring(0,5));
        htJobsService.updateById(HtJobsDO.copyBean(htJobs));
        
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Integer[] jobIds) throws Exception{
        htJobsService.removeByIds(Arrays.asList(jobIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{jobId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("jobId") Integer jobId) throws Exception{
        htJobsService.removeById(jobId);

        return WrapperResponse.success(null);
    }
}

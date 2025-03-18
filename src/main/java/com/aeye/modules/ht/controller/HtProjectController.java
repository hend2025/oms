package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtProjectDTO;
import com.aeye.modules.ht.entity.HtMappingDO;
import com.aeye.modules.ht.entity.HtProductDO;
import com.aeye.modules.ht.entity.HtProjectDO;
import com.aeye.modules.ht.service.HtMappingService;
import com.aeye.modules.ht.service.HtProductService;
import com.aeye.modules.ht.service.HtProjectService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/web/ht/project")
@Api(description="HtProject", tags = "项目")
public class HtProjectController extends AeyeAbstractController{

    @Autowired
    private HtProjectService htProjectService;

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
    public WrapperResponse<List<HtProjectDTO>> list(HtProjectDTO params) throws Exception{
        IPage<HtProjectDO> page = htProjectService.page(
                new Query<HtProjectDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtProjectDO>()
        );
        for (HtProjectDO bean : page.getRecords()){
            List<HtMappingDO> list = HtMappingService.list(new LambdaQueryWrapper<HtMappingDO>().eq(HtMappingDO::getProjectId,bean.getProjectId()));
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
    public WrapperResponse<HtProjectDTO> info(@PathVariable("ProjectId") Integer ProjectId) throws Exception{
        HtProjectDO htProject = htProjectService.getById(ProjectId);

        return (WrapperResponse)WrapperResponse.success(htProject);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtProjectDTO> save(HtProjectDTO htProject) throws Exception{
        if(StringUtils.isBlank(htProject.getProjectName())) {
            return (WrapperResponse) WrapperResponse.fail("项目名称不能为空！", null);
        }
        if(StringUtils.isBlank(htProject.getProductId())) {
            return (WrapperResponse) WrapperResponse.fail("所属产品不能为空！", null);
        }
        htProject.setProjectName(htProject.getProjectName().trim());
        int cnt = htProjectService.count(new LambdaQueryWrapper<HtProjectDO>().eq(HtProjectDO::getProjectName,htProject.getProjectName()));
        if(cnt>0) {
            return (WrapperResponse) WrapperResponse.fail("项目名称已存在！", null);
        }
        HtProjectDO temp = htProjectService.getOne(new LambdaQueryWrapper<HtProjectDO>().eq(HtProjectDO::getProductId,htProject.getProductId()).orderByDesc(HtProjectDO::getProjectId).last("limit 1"));
        long maxId = (temp==null?Long.parseLong(htProject.getProductId()+"001"):Long.parseLong(temp.getProjectId())+1);
        htProject.setProjectId(String.valueOf(maxId));
        htProjectService.save(HtProjectDO.copyBean(htProject));

        String[] app = htProject.getAppCode().split(",");
        for (String s : app){
            HtMappingDO bean = new HtMappingDO();
            bean.setAppCode(s);
            bean.setProductId(htProject.getProductId());
            bean.setProjectId(htProject.getProjectId());
            HtMappingService.save(bean);
        }

        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtProjectDTO> update(HtProjectDTO htProject) throws Exception{
        htProjectService.updateById(HtProjectDO.copyBean(htProject));
        HtMappingService.remove(new LambdaQueryWrapper<HtMappingDO>().eq(HtMappingDO::getProjectId,htProject.getProjectId()));
        String[] app = htProject.getAppCode().split(",");
        for (String s : app){
            HtMappingDO bean = new HtMappingDO();
            bean.setAppCode(s);
            bean.setProductId(htProject.getProductId());
            bean.setProjectId(htProject.getProjectId());
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
        htProjectService.removeByIds(Arrays.asList(ProjectIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{ProjectId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("ProjectId") Integer ProjectId) throws Exception{
        htProjectService.removeById(ProjectId);

        return WrapperResponse.success(null);
    }
}

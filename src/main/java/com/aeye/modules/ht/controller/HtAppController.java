package com.aeye.modules.ht.controller;

import java.util.Arrays;
import java.util.List;

import cn.hsa.hsaf.core.framework.context.HsafContextHolder;
import cn.hsa.hsaf.core.framework.util.CurrentUser;
import cn.hutool.json.JSONUtil;
import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.entity.HtVerInfoDO;
import com.aeye.modules.ht.service.HtAppService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.entity.HtAppDO;
import com.aeye.modules.ht.dto.HtAppDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 系统应用
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * 1.只负责参数解析、格式校验、数据对象转换、路由到service或rpc或http到其它地方，最终向用户反馈结果。
 * 2.减少在controller写业务代码，提高和rpc的复用，因为dubbo发布在service层，业务代码到service完成
 * restful格式：/{api|web}/模块名/类名前缀
 * @author 沈兴平
 * @date 2024/09/14
 */
@RestController
@RequestMapping(value = "/web/ht/app")
@Api(description="HtApp", tags = "系统应用")
public class HtAppController extends AeyeAbstractController {
    @Autowired
    private HtAppService htAppService;

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
    public WrapperResponse<List<HtAppDTO>> list(HtAppDTO params) throws Exception{
        //请求头获取分页参数
        IPage<HtAppDO> page = htAppService.page(
                new Query<HtAppDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtAppDO>()
                    .eq(StringUtils.isNotBlank(params.getAppType()),HtAppDO::getAppType,params.getAppType())
                    .like(StringUtils.isNotBlank(params.getKeyWord()),HtAppDO::getAppCode,params.getKeyWord())
                    .or()
                    .like(StringUtils.isNotBlank(params.getKeyWord()),HtAppDO::getAppName,params.getKeyWord())
                .orderBy(true,true,HtAppDO::getOrderNum)
        );

        return (WrapperResponse)WrapperResponse.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{appCode}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtAppDTO> info(@PathVariable("appCode") String appCode) throws Exception{
        HtAppDO htApp = htAppService.getById(appCode);

        return (WrapperResponse)WrapperResponse.success(htApp);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtAppDTO> save(HtAppDTO htApp) throws Exception{
        if(StringUtils.isBlank(htApp.getAppCode())) {
            return (WrapperResponse) WrapperResponse.fail("应用名称不能为空！", null);
        }
        if(StringUtils.isBlank(htApp.getAppName())) {
            return (WrapperResponse) WrapperResponse.fail("中文名称不能为空！", null);
        }
        if(StringUtils.isBlank(htApp.getAppType())) {
            return (WrapperResponse) WrapperResponse.fail("应用类型不能为空！", null);
        }
        htApp.setAppCode(htApp.getAppCode().trim());
        htApp.setAppName(htApp.getAppName().trim());
        int cnt = htAppService.count(new LambdaQueryWrapper<HtAppDO>()
                .eq(HtAppDO::getAppCode,htApp.getAppCode())
                .or()
                .eq(HtAppDO::getAppName,htApp.getAppName())
        );
        if(cnt>0) {
            return (WrapperResponse) WrapperResponse.fail("应用名称已存在！", null);
        }
        htAppService.save(HtAppDO.copyBean(htApp));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtAppDTO> update(HtAppDTO htApp) throws Exception{
        htAppService.updateById(HtAppDO.copyBean(htApp));
        
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody String[] appCodes) throws Exception{
        htAppService.removeByIds(Arrays.asList(appCodes));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{appCode}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("appCode") String appCode) throws Exception{
        htAppService.removeById(appCode);

        return WrapperResponse.success(null);
    }
}

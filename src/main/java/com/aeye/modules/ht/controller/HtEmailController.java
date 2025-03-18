package com.aeye.modules.ht.controller;

import java.util.Arrays;
import java.util.List;
import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.entity.HtDictDO;
import com.aeye.modules.ht.service.HtEmailService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeye.modules.ht.entity.HtEmailDO;
import com.aeye.modules.ht.dto.HtEmailDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 邮箱地址
 * 所有的接口定义中显式的声明抛出异常（throws Exception）
 * 1.只负责参数解析、格式校验、数据对象转换、路由到service或rpc或http到其它地方，最终向用户反馈结果。
 * 2.减少在controller写业务代码，提高和rpc的复用，因为dubbo发布在service层，业务代码到service完成
 * restful格式：/{api|web}/模块名/类名前缀
 * @author 沈兴平
 * @date 2024/09/14
 */
@RestController
@RequestMapping(value = "/web/ht/email")
@Api(description="HtEmail", tags = "邮箱地址")
public class HtEmailController extends AeyeAbstractController{
    @Autowired
    private HtEmailService htEmailService;

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
    public WrapperResponse<List<HtEmailDTO>> list(HtEmailDTO params) throws Exception{
        //请求头获取分页参数
        IPage<HtEmailDO> page = htEmailService.page(
                new Query<HtEmailDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtEmailDO>()
                .like(StringUtils.isNotBlank(params.getKeyWord()),HtEmailDO::getEmailName,params.getKeyWord())
                .or()
                .like(StringUtils.isNotBlank(params.getKeyWord()),HtEmailDO::getEmailUrl,params.getKeyWord())
                .orderByAsc(HtEmailDO::getOrderNum)
        );

        return (WrapperResponse)WrapperResponse.success(page);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{emailId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtEmailDTO> info(@PathVariable("emailId") Integer emailId) throws Exception{
        HtEmailDO htEmail = htEmailService.getById(emailId);

        return (WrapperResponse)WrapperResponse.success(htEmail);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtEmailDTO> save(HtEmailDTO htEmail) throws Exception{
        htEmailService.save(HtEmailDO.copyBean(htEmail));

        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtEmailDTO> update(HtEmailDTO htEmail) throws Exception{
        htEmailService.updateById(HtEmailDO.copyBean(htEmail));
        
        return (WrapperResponse)WrapperResponse.success(null);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Integer[] emailIds) throws Exception{
        htEmailService.removeByIds(Arrays.asList(emailIds));

        return WrapperResponse.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{emailId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("emailId") Integer emailId) throws Exception{
        htEmailService.removeById(emailId);

        return WrapperResponse.success(null);
    }
}

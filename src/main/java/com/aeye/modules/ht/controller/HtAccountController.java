package com.aeye.modules.ht.controller;

import com.aeye.common.utils.AeyeAbstractController;
import com.aeye.common.utils.DateUtils;
import com.aeye.common.utils.Query;
import com.aeye.common.utils.WrapperResponse;
import com.aeye.modules.ht.dto.HtAccountDTO;
import com.aeye.modules.ht.dto.HtAccountSumDTO;
import com.aeye.modules.ht.entity.HtAccountDO;
import com.aeye.modules.ht.service.HtAccountService;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ht/account")
@Api(tags = "收支记账管理")
public class HtAccountController extends AeyeAbstractController {
    
    @Autowired
    private HtAccountService htAccountService;

    @RequestMapping(value = "/accountSum",method = {RequestMethod.POST,RequestMethod.GET})
    public WrapperResponse<List<HtAccountSumDTO>> accountSum(HtAccountDTO params) throws Exception {
        if (params.getStartDate()==null){
            params.setStartDate(DateUtils.addDateMonths(new Date(),-3));
            params.setEndDate(new Date());
        }
        List<HtAccountSumDTO> list = htAccountService.accountSum(params.getStartDate(),params.getEndDate(),params.getSearchKey());
        return WrapperResponse.success(list);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "当前页码", dataType="int", paramType = "header"),
            @ApiImplicitParam(name="pageSize",value="每页条目",dataType="int", paramType = "header"),
            @ApiImplicitParam(name="orderField",value="排序字段",dataType="string", paramType = "header"),
            @ApiImplicitParam(name="orderType",value="排序类型",dataType="string", paramType = "header", example = "asc或者desc")
    })
    @RequestMapping(value = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "查询列表")
    public WrapperResponse<List<HtAccountDTO>> list(HtAccountDTO params) throws Exception {
        IPage<HtAccountDO> page = htAccountService.page(
                new Query<HtAccountDO>().getPage(buildPageInfo()),
                new LambdaQueryWrapper<HtAccountDO>()
                        .eq(StringUtils.isNotBlank(params.getPayType()), HtAccountDO::getPayType, params.getPayType())
                        .eq(params.getOrgId() != null, HtAccountDO::getOrgId, params.getOrgId())
                        .ge(params.getStartDate() != null, HtAccountDO::getAccountDate, params.getStartDate())
                        .le(params.getEndDate() != null, HtAccountDO::getAccountDate, params.getEndDate())
                        .and(StringUtils.isNotBlank(params.getSearchKey()),
                                wrapper -> wrapper.like(HtAccountDO::getOrgName, params.getSearchKey())
                                        .or().like(HtAccountDO::getOrgCode, params.getSearchKey()))
                        .orderByDesc(HtAccountDO::getAccountDate)
        );
        return (WrapperResponse)WrapperResponse.success(page);
    }

    @RequestMapping(value = "/info/{accountId}", method = {RequestMethod.GET})
    @ApiOperation(value = "查询")
    public WrapperResponse<HtAccountDTO> info(@PathVariable("accountId") Long accountId) throws Exception {
        HtAccountDO account = htAccountService.getById(accountId);
        return (WrapperResponse)WrapperResponse.success(account);
    }

    @RequestMapping(value = "/save",method = {RequestMethod.POST})
    @ApiOperation(value = "保存")
    public WrapperResponse<HtAccountDTO> save(HtAccountDTO accountDTO) throws Exception {
        htAccountService.save(HtAccountDO.copyBean(accountDTO));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ApiOperation(value = "修改")
    public WrapperResponse<HtAccountDTO> update(HtAccountDTO accountDTO) throws Exception {
        htAccountService.updateById(HtAccountDO.copyBean(accountDTO));
        return (WrapperResponse)WrapperResponse.success(null);
    }

    @RequestMapping(value = "/deleteBatch",method = {RequestMethod.POST})
    @ApiOperation(value = "批量删除")
    public WrapperResponse deleteBatch(@RequestBody Long[] accountIds) throws Exception {
        htAccountService.removeByIds(Arrays.asList(accountIds));
        return WrapperResponse.success(null);
    }

    @RequestMapping(value = "/delete/{accountId}",method = {RequestMethod.GET})
    @ApiOperation(value = "删除")
    public WrapperResponse delete(@PathVariable("accountId") Long accountId) throws Exception {
        htAccountService.removeById(accountId);
        return WrapperResponse.success(null);
    }
}
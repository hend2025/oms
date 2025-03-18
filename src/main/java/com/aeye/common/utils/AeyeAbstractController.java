package com.aeye.common.utils;


import cn.hsa.hsaf.core.framework.context.HsafContextHolder;
import cn.hsa.hsaf.core.framework.util.CurrentUser;
import cn.hsa.hsaf.core.framework.util.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class AeyeAbstractController  {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession session;
    @Autowired(required = false)
    protected HttpServletResponse response;

    public AeyeAbstractController() {
    }

    protected CurrentUser getCurrentUser() {
        return HsafContextHolder.getContext().getCurrentUser();
    }

    protected HttpSession getSession() {
        return this.session;
    }

    protected HttpServletRequest getRequest() {
        return this.request;
    }

    protected HttpServletResponse getResponse() {
        return this.response;
    }

    protected AeyePageInfo buildPageInfo() {
        String pageNum = this.request.getHeader(AeyeReflectionUtil.getFieldName(PageInfo::getPageNum));
        String pageSize = this.request.getHeader(AeyeReflectionUtil.getFieldName(PageInfo::getPageSize));
        String orderField = this.request.getHeader(AeyeReflectionUtil.getFieldName(PageInfo::getOrderField));
        String orderType = this.request.getHeader(AeyeReflectionUtil.getFieldName(PageInfo::getOrderType));
        AeyePageInfo pageInfo = new AeyePageInfo();
        pageInfo.setPageNum(StringUtils.isBlank(pageNum) ? 1 : Integer.parseInt(pageNum));
        pageInfo.setPageSize(StringUtils.isBlank(pageSize) ? 10 : Integer.parseInt(pageSize));
        pageInfo.setOrderField(orderField);
        pageInfo.setOrderType(orderType);
        return pageInfo;
    }

}

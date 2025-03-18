package cn.hsa.hsaf.auth.security.filter;

import cn.hsa.hsaf.auth.security.utils.ApiVerifyUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse.ResponseType;
import com.aeye.common.utils.SpringContextUtils;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class ApiAuthorizationFilter implements Filter {
    private static Logger log = LoggerFactory.getLogger(ApiAuthorizationFilter.class);

    public ApiAuthorizationFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Environment env = SpringContextUtils.getBean(Environment.class);
        Boolean isPrivilege = env.getProperty("security.isPrivilege", Boolean.class);
        if(isPrivilege == null || isPrivilege==false){
            this.OldDoFilter(request, response, chain);
        }else{
             chain.doFilter(request, response);
        }
    }

    public void OldDoFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest)request;
        HttpServletResponse httpresponse = (HttpServletResponse)response;
        ApiVerifyUtil apiVerifyUtil = new ApiVerifyUtil();

        try {
            if (apiVerifyUtil.whiteListVerify(httprequest.getServletPath().toString())) {
                chain.doFilter(request, response);
            } else if (apiVerifyUtil.access(httprequest)) {
                chain.doFilter(request, response);
            } else {
                log.info("callpath:" + httprequest.getServletPath().toString());
                httpresponse.setCharacterEncoding("UTF-8");
                httpresponse.setContentType("application/json; charset=utf-8");
                PrintWriter out = httpresponse.getWriter();
                JSONObject res = new JSONObject();
                res.put("code", 110001);
                res.put("type", ResponseType.TYPE_ERROR);
                res.put("message", "API没有访问权限!");
                res.put("data", "");
                out.append(res.toString());
                out.flush();
                out.close();
            }
        } catch (Exception var10) {
            log.error(var10.toString(), var10);
            httpresponse.setCharacterEncoding("UTF-8");
            httpresponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = httpresponse.getWriter();
            JSONObject res = new JSONObject();
            res.put("code", 110001);
            res.put("type", ResponseType.TYPE_ERROR);
            res.put("message", "API没有访问权限!");
            res.put("data", "");
            out.append(res.toString());
            out.flush();
            out.close();
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

}

package com.aeye.common.filter;

import com.aeye.common.utils.AeyeHttpClientUtil;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableAnalyzer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author shenxingping
 * @date 2021/06/27
 */
public class CustomOauth2RedirectFilter implements Filter {
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    private static String CUSTOM_URI_PREX = "/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(servletRequest, servletResponse);
        } catch (IOException var9) {
            throw var9;
        } catch (Exception var10) {
            Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(var10);
            UserRedirectRequiredException redirect = (UserRedirectRequiredException)this.throwableAnalyzer.getFirstThrowableOfType(UserRedirectRequiredException.class, causeChain);
            if (redirect != null) {
                if(redirect.getRedirectUri().startsWith(CUSTOM_URI_PREX)){
                    String url = AeyeHttpClientUtil.getServerUrl((HttpServletRequest) servletRequest);
                    throw new UserRedirectRequiredException(url+redirect.getRedirectUri(), redirect.getRequestParams());
                }else{
                    throw redirect;
                }
            }
            throw var10;
        }
    }

    @Override
    public void destroy() {

    }
}

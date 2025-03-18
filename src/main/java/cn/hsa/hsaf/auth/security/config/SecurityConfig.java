package cn.hsa.hsaf.auth.security.config;

import cn.hsa.hsaf.auth.security.filter.ApiAuthorizationFilter;
import cn.hsa.hsaf.auth.security.filter.RefererFilter;
import cn.hsa.hsaf.auth.security.filter.UserContextFilter;
import cn.hsa.hsaf.auth.security.handler.RedirectAuthenticationSuccessHandler;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.util.StringUtils;

@Configuration
@EnableOAuth2Sso
@AutoConfigureOrder(-2147483648)
@Order(-30)
@ConditionalOnProperty(
        name = {"security.type"},
        havingValue = "hsa-sso",
        matchIfMissing = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.oauth2.client.permits:'/hsaf_pass'}")
    private String permits;
    @Value("${security.referer.permits:}")
    private String referePermits;

    public SecurityConfig() {
    }

    protected void configure(HttpSecurity http) throws Exception {
        this.permits = this.permits + ",/*/api/**,/api/**";
        http.antMatcher("/**").authorizeRequests()
                .antMatchers(this.permits.split(",")).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable().and()
                .addFilterBefore(this.oauth2SsoFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new UserContextFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new RefererFilter(), UserContextFilter.class)
                .addFilterAfter(new ApiAuthorizationFilter(), UserContextFilter.class)
                .csrf()
                .csrfTokenRepository(this.cookieCsrfTokenRepository())
                .requireCsrfProtectionMatcher(this.csrfSecurityRequestMatcher())
                .disable()

        ;
    }

    private CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookiePath("/");
        cookieCsrfTokenRepository.setCookieHttpOnly(false);
        cookieCsrfTokenRepository.setCookieName("XSRF-TOKEN");
        cookieCsrfTokenRepository.setHeaderName("X-XSRF-TOKEN");
        return cookieCsrfTokenRepository;
    }

    private CsrfSecurityRequestMatcher csrfSecurityRequestMatcher() {
        CsrfSecurityRequestMatcher csrfSecurityRequestMatcher = new CsrfSecurityRequestMatcher();
        List<String> list = new ArrayList();
        list.add("/api/");
        if (!StringUtils.isEmpty(this.referePermits)) {
            for(int i = 0; i < this.referePermits.split(",").length; ++i) {
                String exclusions = this.referePermits.split(",")[i];
                if (!"".equals(exclusions)) {
                    list.add(exclusions);
                }
            }
        }

        csrfSecurityRequestMatcher.setExecludeUrls(list);
        return csrfSecurityRequestMatcher;
    }

    private OAuth2ClientAuthenticationProcessingFilter oauth2SsoFilter() {
        OAuth2RestOperations restTemplate = ((UserInfoRestTemplateFactory)this.getApplicationContext().getBean(UserInfoRestTemplateFactory.class)).getUserInfoRestTemplate();
        ResourceServerTokenServices tokenServices = (ResourceServerTokenServices)this.getApplicationContext().getBean(ResourceServerTokenServices.class);
        OAuth2SsoProperties oAuth2SsoProperties = (OAuth2SsoProperties)this.getApplicationContext().getBean(OAuth2SsoProperties.class);
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(oAuth2SsoProperties.getLoginPath());
        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
        authCodeProvider.setStateMandatory(false);
        ((OAuth2RestTemplate)restTemplate).setAccessTokenProvider(authCodeProvider);
        filter.setRestTemplate(restTemplate);
        filter.setTokenServices(tokenServices);
        filter.setAuthenticationSuccessHandler(this.hsafSuccessHandler());
        return filter;
    }

    private AuthenticationSuccessHandler hsafSuccessHandler() {
        String targetUrl = "/";
        RedirectAuthenticationSuccessHandler successHandler = new RedirectAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl(targetUrl);
        successHandler.setTargetUrlParameter("hsaf_redirect_uri");
        return successHandler;
    }

}

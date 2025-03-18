package com.aeye.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.aeye.common.utils.SslUtil;

import javax.annotation.PostConstruct;


@Slf4j
@Component
public class AeyeApplicationRunner implements ApplicationRunner {

    @Value("${spring.application.name:}")
    private String appName;
    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @PostConstruct
    public void init(){
        try{
            SslUtil.ignoreSsl();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String lastPath = contextPath.endsWith("/") ? "doc.html" : "/doc.html";
        log.warn("当前SpringBoot【{}】服务已启动完成!!! 端口【{}】，上下文【{}】",appName, port, contextPath);
        log.warn("访问路径【http://127.0.0.1:{}{}{}",port,contextPath,lastPath);
    }

}

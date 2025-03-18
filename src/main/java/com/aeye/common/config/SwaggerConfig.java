package com.aeye.common.config;

import cn.hutool.core.codec.Base64;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.Date;


@Configuration
@EnableSwagger2
@ConditionalOnWebApplication
public class SwaggerConfig {


    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${spring.application.swaggerShow:true}")
    private boolean swaggerShow;

    @Bean
    public Docket createRestBs() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .groupName(appName)
                .directModelSubstitute(Date.class, Long.class)
                .directModelSubstitute(byte[].class, String.class)
                .apiInfo(apiInfo()).useDefaultResponseMessages(false).select()
                .apis(RequestHandlerSelectors.basePackage("com.aeye"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build();
    }


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 处理字节
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(byte[].class, getByteSerialize());
        mapper.registerModule(simpleModule);

        return mapper;
    }


    private JsonSerializer<byte[]> getByteSerialize() {
        return new JsonSerializer<byte[]>() {
            @Override
            public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                if(null != value){
                    gen.writeString(Base64.encode(value));
                }else{
                    gen.writeString("");
                }
            }
        };
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appName+"微服务")
                .contact(new Contact("医保开发部", "http://www.a-eye.cn/", "shenxingping@a-eye.cn"))
                .description("restFul接口文档，新UI访问:"+contextPath+"/doc.html")
                .termsOfServiceUrl("www.a-eye.cn")
                .version("1.0.0")
                .build();
    }

}
package com.wong.shopingmall.auth.config;

import com.wong.shopingmall.commons.config.BaseSwaggerConfig;
import com.wong.shopingmall.commons.properties.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Wong
 * @Description: 接口文档配置
 * @DateTime: 2021/4/6 下午2:51
 **/
@EnableSwagger2
@Configuration
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .title("权限管理中心")
                .apiBasePackage("com.wong.shopingmall.auth.controller")
                .version("1.0.0")
                .contactUrl("https://www.wong.mall.com")
                .contactName("wong")
                .description("mall微服务架构权限控制中心web接口文档")
                .enableSecurity(true)
                .build();
    }
}

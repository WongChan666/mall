package com.wong.shopingmall.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：Wong
 * @date ：Created in 2021/3/27 13:05
 * @description：授权客户端配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "mall.auth")
public class ClientConfigProperties {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密码
     */
    private String clientSecret;

    /**
     * 授权模式
     */
    private String grantType;


}

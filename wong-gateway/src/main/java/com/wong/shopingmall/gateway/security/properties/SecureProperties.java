package com.wong.shopingmall.gateway.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：Wong
 * @date ：Created in 2021/4/7 18:33
 * @description：认证忽略白名单
 */

@Data
@Component
@ConfigurationProperties(prefix = "mall.auth.ignore")
public class SecureProperties {

    private String [] urls;
}

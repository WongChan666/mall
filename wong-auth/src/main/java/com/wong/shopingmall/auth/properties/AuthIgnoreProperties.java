package com.wong.shopingmall.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Wong
 * @Description: 权限校验忽略白名单
 * @DateTime: 2021/4/6 下午2:08
 **/
@Data
@Component
@ConfigurationProperties(prefix = "mall.auth.ignore")
public class AuthIgnoreProperties {

    private String [] urls;
}

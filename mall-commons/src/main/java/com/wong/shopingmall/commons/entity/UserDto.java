package com.wong.shopingmall.commons.entity;

import lombok.Data;

import java.util.Map;

/**
 * @Author: Wong
 * @Description: 用户信息数据传输对象
 * @DateTime: 2021/4/13 下午3:36
 **/
@Data
public class UserDto {

    /**
     * 用户名/手机号码
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号状态(1-正常；0-锁定)
     */
    private Integer status;
    /**
     * 是否没有被锁定
     */
    private boolean accountNonLocked;
    /**
     * 账户是否没有过期
     */
    private boolean accountNonExpired;
    /**
     * 是否启用
     */
    private boolean enabled;
    /**
     * 凭证是否没有过期
     */
    private boolean credentialsNonExpired;
    /**
     * 认证客户端ID
     */
    private String clientId;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户描述
     */
    private String userDesc;

    /***
     * 账号类型
     */
    private String accountType;

    /**
     * 用户附加属性
     */
    private Map<String, Object> attrs;

    /**
     * 企业ID
     */
    private String companyCode;

    /**
     * 企业名称
     */
    private String companyName;
}

package com.wong.shopingmall.auth.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author ：Wong
 * @date ：Created in 2021/3/23 22:13
 * @description：授权管理用户信息对象
 */
@Data
public class SecurityUser implements UserDetails {


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

    /**
     * 用户权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(String username, String password, Integer status, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            return Collections.emptyList();
        }
        return this.authorities;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}

package com.wong.shopingmall.auth.service;

import com.wong.shopingmall.auth.entity.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：Wong
 * @date ：Created in 2021/3/23 22:12
 * @description：用户授权业务实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<SecurityUser> list = Arrays.asList(new SecurityUser("admin",passwordEncoder.encode("123456"),1,Arrays.asList(new SimpleGrantedAuthority("authority1"),new SimpleGrantedAuthority("authority2"))),
                new SecurityUser("user", passwordEncoder.encode("123456"), 1, Arrays.asList(new SimpleGrantedAuthority("authority1"), new SimpleGrantedAuthority("authority2"))));
        SecurityUser securityUser = list.stream().filter(e -> e.getUsername().equals(s)).findFirst().orElse(null);
        if(ObjectUtils.isEmpty(securityUser)){
            throw new BadCredentialsException("用户不存在，请先注册...");
        }
        //是否启用
        securityUser.setEnabled(true);
        //账户是否没有被锁定
        securityUser.setAccountNonLocked(true);
        //账户是否没有过期
        securityUser.setAccountNonExpired(true);
        //凭证是否没有过期
        securityUser.setCredentialsNonExpired(true);
        log.info("用户信息："+securityUser.toString());
        return securityUser;
    }
}

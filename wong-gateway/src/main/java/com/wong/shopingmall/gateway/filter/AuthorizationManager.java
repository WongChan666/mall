package com.wong.shopingmall.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.wong.shopingmall.commons.constant.AuthConstant;
import com.wong.shopingmall.commons.entity.UserDto;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;
import com.wong.shopingmall.gateway.properties.SecureProperties;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: Wong
 * @Description: 权限管理器
 * @DateTime: 2021/4/13 上午11:54
 **/
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private SecureProperties secureProperties;
    /**
     * 鉴权失败处理
     * @param response
     * @return
     */
    private Mono<Void> out(ServerHttpResponse response) {
        JSONObject message = new JSONObject();
        message.appendField("success", false);
        message.appendField("code", 401);
        message.appendField("data", "权限校验失败");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();
        //配置白名单放行
        List<String> urls = secureProperties.getUrls();
        for (String url : urls) {
            if (antPathMatcher.match(url, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }
        //跨域检查放行
        if(request.getMethod()== HttpMethod.OPTIONS){
            return Mono.just(new AuthorizationDecision(true));
        }
        //访问商城公共信息api放行
        if(antPathMatcher.match("**/api/commons/**",uri.getPath())){
            return Mono.just(new AuthorizationDecision(true));
        }
        //设置管理员和普通用户的访问权限
        try{
            //token获取
            String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
            if(StringUtils.isEmpty(token)){
                return Mono.just(new AuthorizationDecision(true));
            }
            //去掉前缀
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX,"");
            //解析jwt获取jwt对象
            JWSObject jwsObject = JWSObject.parse(realToken);
            //负荷，负载信息
            String payload = jwsObject.getPayload().toString();
            UserDto userDto = JSONUtil.toBean(payload, UserDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

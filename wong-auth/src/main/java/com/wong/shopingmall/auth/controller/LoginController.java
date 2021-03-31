package com.wong.shopingmall.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.wong.shopingmall.auth.constants.AuthConstant;
import com.wong.shopingmall.auth.utils.VerifyCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Map;

/**
 * @author ：Wong
 * @date ：Created in 2021/3/23 22:50
 * @description：登录授权控制器
 */
@Controller
public class LoginController {

    @Autowired
    private TokenStore jwtTokenStore;
    @Autowired
    private TokenEndpoint tokenEndpoint;


    @ApiOperation("Oauth2获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", value = "登录用户名"),
            @ApiImplicitParam(name = "password", value = "登录密码")
    })
    @ResponseBody
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public JSONObject postAccessToken(@ApiIgnore Principal principal, @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Assert.notNull(oAuth2AccessToken,"获取token异常");
        JSONObject result = new JSONObject();
        result.put("token",oAuth2AccessToken.getValue());
        result.put("refreshToken",oAuth2AccessToken.getRefreshToken());
        result.put("expires",oAuth2AccessToken.getExpiresIn());
        result.put("tokenHead",AuthConstant.JWT_TOKEN_PREFIX);
        return result;
    }


    /**
     * 退出移除令牌
     *
     * @param accessToken token令牌
     * @return
     */
    @ApiOperation(value = "退出并移除令牌", notes = "退出并移除令牌,令牌将失效")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessToken", required = true, value = "访问令牌", paramType = "form")
    })
    @ResponseBody
    @PostMapping("/logout/token")
    public String removeToken(@RequestParam("accessToken") String accessToken) {
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(accessToken);
        if (oAuth2AccessToken == null) {
            return "token已过期或不存在!";
        }
        jwtTokenStore.removeAccessToken(oAuth2AccessToken);
        return "登出成功";
    }


    @ApiOperation("验证码图片获取")
    @ResponseBody
    @GetMapping(value = "/login/verify_code",produces = MediaType.IMAGE_PNG_VALUE)
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            int width=200;
            int height=69;
            //生成对应宽高的初始图片
            BufferedImage verifyImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //生成验证码字符并加上噪点，干扰线，返回值为验证码字符
            String randomText = VerifyCode.drawRandomText(width,height,verifyImg);
            //存入session
            request.getSession().setAttribute("verifyCode", randomText);
            //必须设置响应内容类型为图片，否则前台不识别
            response.setContentType("image/png");
            //获取文件输出流
            OutputStream os = response.getOutputStream();
            //输出图片流
            ImageIO.write(verifyImg,"png",os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

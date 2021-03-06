package com.pokaboo.userservice.controller;

import com.google.gson.Gson;
import com.pokaboo.commonutils.JwtUtils;
import com.pokaboo.servicebase.exceptionhandler.MyException;
import com.pokaboo.userservice.entity.UcenterMember;
import com.pokaboo.userservice.service.UcenterMemberService;
import com.pokaboo.userservice.utils.ConstantPropertiesUtil;
import com.pokaboo.userservice.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author pokab
 */
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 微信扫码登陆回调
     * @param code
     * @param state
     * @param session
     * @return
     */
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {

        //1、得到授权临时票据code
        try {
            System.out.println("code = " + code);
            System.out.println("state = " + state);
            //2、向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            String result = null;
            try {
                result = HttpClientUtils.get(accessTokenUrl);
                System.out.println("accessToken=============" + result);
            } catch (Exception e) {
                throw new MyException(20001, "获取access_token失败");
            }
            //3、解析json字符串得打access_token、openid
            Gson gson = new Gson();
            HashMap map = gson.fromJson(result, HashMap.class);
            String accessToken = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            UcenterMember ucenterMember = ucenterMemberService.getByOpenid(openid);
            if (ucenterMember == null) {
                System.out.println("新用户注册");
                //4、根据access_token、openid访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String resultUserInfo = null;
                try {
                    resultUserInfo = HttpClientUtils.get(userInfoUrl);
                    System.out.println("resultUserInfo==========" + resultUserInfo);
                } catch (Exception e) {
                    throw new MyException(20001, "获取用户信息失败");
                }
                //解析json
                HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
                String nickname = (String) mapUserInfo.get("nickname");
                String headimgurl = (String) mapUserInfo.get("headimgurl");

                //向数据库中插入一条记录
                ucenterMember = new UcenterMember();
                ucenterMember.setNickname(nickname);
                ucenterMember.setOpenid(openid);
                ucenterMember.setAvatar(headimgurl);
                ucenterMemberService.save(ucenterMember);
            }
            String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
            return "redirect:http://localhost:3000?token="+token;
        } catch (Exception e) {
            throw new MyException(20001, "登录失败");
        }
    }

    /**
     * 微信扫码登陆
     * @param session
     * @return
     */
    @GetMapping("/login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址 //获取业务服务器重定向地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            //url编码
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new MyException(20001, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }
}

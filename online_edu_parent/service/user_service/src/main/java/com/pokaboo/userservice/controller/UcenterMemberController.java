package com.pokaboo.userservice.controller;


import com.pokaboo.commonutils.JwtUtils;
import com.pokaboo.commonutils.Result;
import com.pokaboo.servicebase.exceptionhandler.MyException;
import com.pokaboo.userservice.entity.UcenterMember;
import com.pokaboo.userservice.entity.vo.LoginVo;
import com.pokaboo.userservice.entity.vo.RegisterVo;
import com.pokaboo.userservice.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author pookaboo
 * @since 2020-08-15
 */
@Api(value = "用户服务", tags = {"用户服务"})
@CrossOrigin
@RestController
@RequestMapping("/userservice/ucenter-member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public Result login(
            @ApiParam(name = "loginVo", value = "登录对象", required = true)
            @RequestBody LoginVo loginVo) {
        String token = ucenterMemberService.login(loginVo);
        return Result.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public Result register(
            @ApiParam(name = "registerVo", value = "注册对象", required = true)
            @RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public Result getLoginInfo(HttpServletRequest request) {
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            UcenterMember ucenterMember = ucenterMemberService.getLoginInfo(memberId);
            return Result.ok().data("item", ucenterMember);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "error");
        }
    }
}


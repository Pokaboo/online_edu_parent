package com.pokaboo.eduservice.controller;

import com.pokaboo.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName EduLoginController
 * @Description TODO
 * @Author Pokaboo
 * @Date 2020/5/6 20:29
 * @Version 1.0
 */
@Api(value = "后台登录", tags = {"后台登录"})
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login() {
        return Result.ok();
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public Result info() {
        return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://static.easyicon.net/preview/125/1258061.gif");
    }
}

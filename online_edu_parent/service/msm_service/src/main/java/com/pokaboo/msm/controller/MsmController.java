package com.pokaboo.msm.controller;

import com.pokaboo.commonutils.Result;
import com.pokaboo.msm.service.MsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pokab
 */
@Api(value = "短信服务", tags = {"短信服务"})
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;


    @ApiOperation(value="发送短信验证码")
    @GetMapping(value = "/send/{phone}")
    public Result sendMsg(
            @ApiParam(name = "phone", value = "手机号", required = true)
            @PathVariable String phone){
        boolean isSend = msmService.sendMsg(phone);
        if(isSend){
            return Result.ok();
        }
        return Result.error().message("发送短信验证码失败");
    }
}

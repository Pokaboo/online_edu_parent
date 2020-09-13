package com.pokaboo.staservice.controller;


import com.pokaboo.commonutils.Result;
import com.pokaboo.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author pokaboo
 * @since 2020-09-13
 */
@Api(value = "数据统计")
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation(value = "获取指定日期注册人数")
    @GetMapping(value = "/countregister/{day}")
    public Result registerCount(
            @ApiParam(name = "day", value = "日期", required = true)
            @PathVariable String day) {
        statisticsDailyService.countRegisterByDay(day);
        return Result.ok();
    }
}



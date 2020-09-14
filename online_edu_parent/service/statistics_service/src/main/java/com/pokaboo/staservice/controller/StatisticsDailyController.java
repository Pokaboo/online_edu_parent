package com.pokaboo.staservice.controller;


import com.pokaboo.commonutils.Result;
import com.pokaboo.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @PostMapping(value = "/countregister/{day}")
    public Result registerCount(
            @ApiParam(name = "day", value = "日期", required = true)
            @PathVariable String day) {
        statisticsDailyService.countRegisterByDay(day);
        return Result.ok();
    }

    @ApiOperation(value = "数据统计")
    @GetMapping("/show-chart/{type}/{start}/{end}")
    public Result showChart(
            @ApiParam(name = "type", value = "类型", required = true)
            @PathVariable String type,
            @ApiParam(name = "start", value = "开始时间", required = true)
            @PathVariable String start,
            @ApiParam(name = "end", value = "结束时间", required = true)
            @PathVariable String end
    ) {
        Map<String, Object> data = statisticsDailyService.showChart(type, start, end);
        return Result.ok().data(data);
    }
}



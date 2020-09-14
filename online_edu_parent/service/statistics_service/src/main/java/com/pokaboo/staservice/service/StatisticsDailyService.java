package com.pokaboo.staservice.service;

import com.pokaboo.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author pokaboo
 * @since 2020-09-13
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 统计某一天的注册人数
     * @param day
     */
    void countRegisterByDay(String day);

    /**
     * 数据统计
     * @param type
     * @param start
     * @param end
     * @return
     */
    Map<String, Object> showChart(String type, String start, String end);
}

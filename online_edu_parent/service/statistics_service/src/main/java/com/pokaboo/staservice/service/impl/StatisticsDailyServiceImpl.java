package com.pokaboo.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pokaboo.commonutils.Result;
import com.pokaboo.staservice.client.UserClient;
import com.pokaboo.staservice.entity.StatisticsDaily;
import com.pokaboo.staservice.mapper.StatisticsDailyMapper;
import com.pokaboo.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author pokaboo
 * @since 2020-09-13
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UserClient userClient;

    @Override
    public void countRegisterByDay(String day) {

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);

        // 统计信息
        Result registerCount = userClient.registerCount(day);
        Integer registerNum = (Integer) registerCount.getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);


        baseMapper.delete(queryWrapper);

    }

    /**
     * 数据统计
     *
     * @param type
     * @param start
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> showChart(String type, String start, String end) {
        Map<String, Object> dataMap = new HashMap<>();
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> dataNum = new ArrayList<>();

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date_calculated", type);
        queryWrapper.between("date_calculated", start, end);

        List<StatisticsDaily> dailyList = baseMapper.selectList(queryWrapper);
        if (dailyList != null && !dailyList.isEmpty()) {
            for (StatisticsDaily statisticsDaily : dailyList) {
                date_calculatedList.add(statisticsDaily.getDateCalculated());
                switch (type) {
                    case "register_num":
                        dataNum.add(statisticsDaily.getRegisterNum());
                        break;
                    case "login_num":
                        dataNum.add(statisticsDaily.getLoginNum());
                        break;
                    case "video_view_num":
                        dataNum.add(statisticsDaily.getVideoViewNum());
                        break;
                    case "course_num":
                        dataNum.add(statisticsDaily.getCourseNum());
                        break;
                    default:
                        break;
                }
            }
            dataMap.put("date_calculatedList", date_calculatedList);
            dataMap.put("dataNum", dataNum);
            return dataMap;
        }

        return null;
    }
}

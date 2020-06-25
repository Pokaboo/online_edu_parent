package com.pokaboo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.client.VodClient;
import com.pokaboo.eduservice.entity.EduVideo;
import com.pokaboo.eduservice.mapper.EduVideoMapper;
import com.pokaboo.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pokaboo.servicebase.exceptionhandler.MyException;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(queryWrapper);
        List<String> videoIdList = new ArrayList<>();
        if (eduVideoList != null && !eduVideoList.isEmpty()) {
            for (EduVideo eduVideo : eduVideoList) {
                videoIdList.add(eduVideo.getVideoSourceId());
            }
        }

        // 批量删除视频
        if (videoIdList != null && !videoIdList.isEmpty()) {
            Result result = vodClient.deleteBatch(videoIdList);
            if(result.getCode() == 20001){
                throw new MyException(20001,"Hystrix————> time out");
            }
        }

        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(videoQueryWrapper);
    }
}

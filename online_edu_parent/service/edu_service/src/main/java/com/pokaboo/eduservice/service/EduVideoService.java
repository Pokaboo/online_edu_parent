package com.pokaboo.eduservice.service;

import com.pokaboo.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 删除课程下的视频
     * @param courseId
     */
    void removeByCourseId(String courseId);
}

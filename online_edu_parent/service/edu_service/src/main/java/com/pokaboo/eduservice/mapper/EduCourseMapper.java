package com.pokaboo.eduservice.mapper;

import com.pokaboo.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pokaboo.eduservice.entity.frontvo.CourseWebVo;
import com.pokaboo.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 获取课程发布信息
     * @param courseId
     * @return
     */
    CoursePublishVo findCoursePublishInfo(String courseId);

    /**
     * 获取课程详情
     * @param courseId
     * @return
     */
    CourseWebVo getCourseInfo(String courseId);
}

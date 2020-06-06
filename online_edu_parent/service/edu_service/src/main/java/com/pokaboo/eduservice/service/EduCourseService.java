package com.pokaboo.eduservice.service;

import com.pokaboo.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pokaboo.eduservice.entity.vo.CourseInfoForm;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
public interface EduCourseService extends IService<EduCourse> {
    /**
     * 新增课程
     * @param courseInfoForm
     * @return
     */
    String addCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 根据id获取课程信息
     * @param courseId
     * @return
     */
    CourseInfoForm findCourseInfoById(String courseId);

    /**
     * 更新课程信息
     * @param courseInfoForm
     * @return
     */
    boolean updateCourseInfo(CourseInfoForm courseInfoForm);
}

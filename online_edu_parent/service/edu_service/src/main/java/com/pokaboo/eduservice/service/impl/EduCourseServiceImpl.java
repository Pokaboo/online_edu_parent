package com.pokaboo.eduservice.service.impl;

import com.pokaboo.eduservice.entity.EduCourse;
import com.pokaboo.eduservice.entity.EduCourseDescription;
import com.pokaboo.eduservice.entity.vo.CourseInfoForm;
import com.pokaboo.eduservice.mapper.EduCourseMapper;
import com.pokaboo.eduservice.service.EduCourseDescriptionService;
import com.pokaboo.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pokaboo.eduservice.utils.Course;
import com.pokaboo.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;


    /**
     * 新增课程
     * @param courseInfoForm
     * @return
     */
    @Override
    public String addCourseInfo(CourseInfoForm courseInfoForm) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        eduCourse.setStatus(Course.COURSE_DRAFT);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            throw new MyException(20001,"课程信息保存失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm,eduCourseDescription);
        eduCourseDescription.setId(eduCourse.getId());
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if(!save){
            throw  new MyException(20001,"课程描述信息保存失败");
        }
        return eduCourse.getId();
    }

    /**
     * 根据id查询课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoForm findCourseInfoById(String courseId) {
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        if(eduCourse != null){
            BeanUtils.copyProperties(eduCourse,courseInfoForm);
            EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
            BeanUtils.copyProperties(eduCourseDescription,courseInfoForm);
        }
        return courseInfoForm;
    }

    /**
     * 更新课程信息
     * @param courseInfoForm
     * @return
     */
    @Override
    public boolean updateCourseInfo(CourseInfoForm courseInfoForm) {
        boolean successFlag = false;
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0){
            successFlag = false;
            throw  new MyException(20001,"更新课程信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm,eduCourseDescription);
        boolean update1 = eduCourseDescriptionService.updateById(eduCourseDescription);
        if(!update1){
            successFlag = false;
            throw  new MyException(20001,"更新课程简介信息失败");
        }
        if(update > 0 && update1){
            successFlag = true;
        }
        return successFlag;
    }
}

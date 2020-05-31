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
}

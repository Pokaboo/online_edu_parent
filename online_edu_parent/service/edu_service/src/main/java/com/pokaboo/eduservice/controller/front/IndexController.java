package com.pokaboo.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.EduCourse;
import com.pokaboo.eduservice.entity.EduTeacher;
import com.pokaboo.eduservice.service.EduCourseService;
import com.pokaboo.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pokaboo
 */
@Api(value = "首页数据显示", tags = {"首页数据显示"})
@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询前8条热门课程，查询前4条名师
     * @return
     */
    @ApiOperation(value = "热门课程和名师查询")
    @GetMapping("/index")
    public Result index() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return Result.ok().data("courseList", eduList).data("teacherList", teacherList);
    }
}

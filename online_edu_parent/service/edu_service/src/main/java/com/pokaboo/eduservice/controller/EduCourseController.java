package com.pokaboo.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.EduCourse;
import com.pokaboo.eduservice.entity.vo.CourseInfoForm;
import com.pokaboo.eduservice.entity.vo.CoursePublishVo;
import com.pokaboo.eduservice.entity.vo.CourseQuery;
import com.pokaboo.eduservice.service.EduCourseService;
import com.pokaboo.eduservice.utils.Course;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
@Api(value = "课程发布", tags = {"课程发布"})
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    @ApiOperation(value = "新增课程")
    public Result addCourseInfo(
            @ApiParam(name = "courseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {
        String courseId = eduCourseService.addCourseInfo(courseInfoForm);
        if (StringUtils.isBlank(courseId)) {
            return Result.error().message("保存失败");
        }
        return Result.ok().data("courseId", courseId).message("保存成功");
    }

    @ApiOperation(value = "根据id获取课程信息")
    @GetMapping("/findCourseInfoById/{courseId}")
    public Result findCourseInfoById(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        CourseInfoForm courseInfoForm = eduCourseService.findCourseInfoById(courseId);
        if (courseInfoForm != null) {
            return Result.ok().data("courseInfo", courseInfoForm);
        }
        return Result.error().message("查询课程信息失败");
    }

    @ApiOperation(value = "更新课程信息")
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(
            @ApiParam(name = "courseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {
        boolean updateFlag = eduCourseService.updateCourseInfo(courseInfoForm);
        if (updateFlag) {
            return Result.ok();
        }
        return Result.error().message("更新课程信息失败");
    }

    @ApiOperation(value = "获取课程发布信息")
    @GetMapping("/findCoursePublishInfo/{courseId}")
    public Result findCoursePublishInfo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId
    ) {
        CoursePublishVo coursePublishInfo = eduCourseService.findCoursePublishInfo(courseId);
        if (coursePublishInfo != null) {
            return Result.ok().data("coursePublishInfo", coursePublishInfo);
        }
        return Result.error();
    }

    @ApiOperation(value = "发布课程")
    @PostMapping("/publishCourse/{courseId}")
    public Result publishCourse(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId
    ) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(Course.COURSE_NORMAL);
        boolean update = eduCourseService.updateById(eduCourse);
        if (update) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "课程列表")
    @PostMapping("/pageQuery/{current}/{limit}")
    public Result pageQuery(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false)  CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(current, limit);
        eduCourseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "删除课程")
    @DeleteMapping("/removeCourse/{courseId}")
    public Result removeCourse(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId
    ) {
        boolean removeFlag = eduCourseService.removeCourse(courseId);
        if (removeFlag) {
            return Result.ok();
        }
        return Result.error();
    }

}


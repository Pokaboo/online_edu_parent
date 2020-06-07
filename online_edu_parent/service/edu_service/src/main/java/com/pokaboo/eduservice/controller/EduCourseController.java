package com.pokaboo.eduservice.controller;


import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.vo.CourseInfoForm;
import com.pokaboo.eduservice.entity.vo.CoursePublishVo;
import com.pokaboo.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}


package com.pokaboo.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.EduCourse;
import com.pokaboo.eduservice.entity.EduTeacher;
import com.pokaboo.eduservice.entity.frontvo.CourseQueryVo;
import com.pokaboo.eduservice.entity.frontvo.CourseWebVo;
import com.pokaboo.eduservice.entity.vo.ChapterVo;
import com.pokaboo.eduservice.service.EduChapterService;
import com.pokaboo.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * front course deal
 *
 * @author pokaboo
 */
@Api(value = "课程详情", tags = {"课程详情"})
@RestController
@RequestMapping("/eduservice/frontcourse")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "/pageCourseList/{page}/{limit}")
    public Result pageCourseList(@ApiParam(name = "page", value = "当前页码", required = true)
                                 @PathVariable Long page,

                                 @ApiParam(name = "limit", value = "每页记录数", required = true)
                                 @PathVariable Long limit,

                                 @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                                 @RequestBody(required = false) CourseQueryVo courseQuery) {

        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = eduCourseService.pageCourseList(pageParam, courseQuery);
        return Result.ok().data(map);

    }

    /**
     * 获取课程详情
     *
     * @param courseId
     * @return
     */
    @ApiOperation(value = "课程详情")
    @GetMapping("/getCourseInfo/{courseId}")
    public Result getCourseInfo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId
    ) {
        CourseWebVo courseWebVo = eduCourseService.getCourseInfo(courseId);
        List<ChapterVo> chapterVoList = eduChapterService.findAllChapterInfo(courseId);
        return Result.ok().data("courseWebVo", courseWebVo).data("chapterVoList", chapterVoList);
    }
}


package com.pokaboo.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.EduCourse;
import com.pokaboo.eduservice.entity.EduTeacher;
import com.pokaboo.eduservice.service.EduCourseService;
import com.pokaboo.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author pokaboo
 * 首页讲师展示
 */
@Api(value = "首页讲师展示", tags = {"首页讲师展示"})
@RestController
@RequestMapping("/eduservice/frontTeacher")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/pageList/{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);
        Map<String, Object> map = eduTeacherService.pageTeacherList(pageParam);
        return Result.ok().data(map);
    }

    @ApiOperation(value = "获取讲师详情")
    @GetMapping("/getTeacherInfo/{id}")
    public Result getTeacherInfo(@ApiParam(name = "id", value = "讲师id", required = true)
                                 @PathVariable String id) {

        EduTeacher eduTeacher = eduTeacherService.getById(id);
        List<EduCourse> courseList = eduCourseService.findCourseInfoByTeacherId(id);
        return Result.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }
}

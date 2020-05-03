package com.pokaboo.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.EduTeacher;
import com.pokaboo.eduservice.query.TeacherQuery;
import com.pokaboo.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Pokaboo
 * @since 2020-05-02
 */
@Api(value = "讲师管理", tags = {"讲师管理"})
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "获取讲师列表")
    @GetMapping("/findAll")
    public Result findAllEduTeacher() {
        List<EduTeacher> eduTeacherList = eduTeacherService.list(null);
        return Result.ok().data("items", eduTeacherList);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public Result removeEduTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @ApiOperation(value = "讲师分页查询")
    @GetMapping("/pageTheacher/{current}/{limit}")
    public Result pageListTeacher(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页显示数", required = true)
            @PathVariable long limit) {
        // 使用mybatis-plus分页插件
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        eduTeacherService.page(eduTeacherPage, null);
        List<EduTeacher> eduTeacherList = eduTeacherPage.getRecords();
        long totals = eduTeacherPage.getTotal();
        if (eduTeacherList != null && eduTeacherList.size() > 0) {
            return Result.ok().data("total", totals).data("rows", eduTeacherList);
        }
        return Result.error();
    }

    @ApiOperation(value = "讲师按条件查询")
    @PostMapping("/pageTheacherByConditons/{current}/{limit}")
    public Result pageTheacherByConditons(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页显示数", required = true)
            @PathVariable long limit,
            @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        eduTeacherService.pageQuery(eduTeacherPage, teacherQuery);
        List<EduTeacher> eduTeacherList = eduTeacherPage.getRecords();
        if (eduTeacherList != null && eduTeacherList.size() > 0) {
            long totals = eduTeacherPage.getTotal();
            return Result.ok().data("total", totals).data("rows", eduTeacherList);
        }
        return Result.error();
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public Result save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {

        eduTeacherService.save(teacher);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/{id}")
    public Result getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.ok().data("item", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("/{id}")
    public Result updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {

        teacher.setId(id);
        eduTeacherService.updateById(teacher);
        return Result.ok();
    }

}


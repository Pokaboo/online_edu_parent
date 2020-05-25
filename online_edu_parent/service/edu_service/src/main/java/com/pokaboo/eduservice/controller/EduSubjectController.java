package com.pokaboo.eduservice.controller;


import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.subject.PrimarySubject;
import com.pokaboo.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-24
 */
@Api(value = "课程分类", tags = {"课程分类"})
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;


    @ApiOperation(value = "批量添加课程分类")
    @PostMapping("/addSubject")
    public Result addSubject(@ApiParam(value = "上传的Excel", required = true)MultipartFile file){
        eduSubjectService.addSubject(eduSubjectService ,file);
        return Result.ok();
    }

    @ApiOperation(value="查询课程分类列表")
    @GetMapping("/findAllSubject")
    public Result findAllSubject(){
        List<PrimarySubject> subjects = eduSubjectService.findAllSubject();
        return Result.ok().data("subjects",subjects);
    }
}


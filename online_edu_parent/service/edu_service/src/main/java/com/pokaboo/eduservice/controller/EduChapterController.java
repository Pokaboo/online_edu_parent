package com.pokaboo.eduservice.controller;


import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.vo.ChapterVo;
import com.pokaboo.eduservice.entity.vo.VideoVo;
import com.pokaboo.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "课程章节管理", tags = {"课程章节管理"})
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 根据课程id获取所有的章节以及课时信息
     * @param courseId
     * @return
     */
    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("/findAllChapterInfo/{courseId}")
    public Result findAllChapterInfo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId){
        List<ChapterVo> chapterList = eduChapterService.findAllChapterInfo(courseId);
        return  Result.ok().data("chapterList",chapterList);
    }
}


package com.pokaboo.eduservice.controller;


import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.entity.EduChapter;
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
     *
     * @param courseId
     * @return
     */
    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("/findAllChapterInfo/{courseId}")
    public Result findAllChapterInfo(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        List<ChapterVo> chapterList = eduChapterService.findAllChapterInfo(courseId);
        return Result.ok().data("chapterList", chapterList);
    }

    @ApiOperation(value = "根据id查询章节信息")
    @GetMapping("/findChapter/{chapterId}")
    public Result findChapterInfo(
            @ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return Result.ok().data("eduChapter", eduChapter);
    }

    @ApiOperation(value = "添加章节信息")
    @PostMapping("/addChapter")
    public Result addChapter(
            @ApiParam(name = "eduChapter", value = "章节信息", required = true)
            @RequestBody EduChapter eduChapter
    ) {
        boolean save = eduChapterService.save(eduChapter);
        if (save) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "修改章节信息")
    @PostMapping("/updateChapter")
    public Result updateChapter(
            @ApiParam(name = "eduChapter", value = "章节信息", required = true)
            @RequestBody EduChapter eduChapter
    ) {
        boolean update = eduChapterService.updateById(eduChapter);
        if (update) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("/{chapterId}")
    public Result deleteChapter(
            @ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

}


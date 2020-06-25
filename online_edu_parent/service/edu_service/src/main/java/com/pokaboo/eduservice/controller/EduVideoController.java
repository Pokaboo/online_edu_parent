package com.pokaboo.eduservice.controller;


import com.pokaboo.commonutils.Result;
import com.pokaboo.eduservice.client.VodClient;
import com.pokaboo.eduservice.entity.EduVideo;
import com.pokaboo.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
@Api(value = "课时管理", tags = {"课时管理"})
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @ApiOperation(value = "添加课时")
    @PostMapping("/saveVideo")
    public Result saveEduVideo(
            @ApiParam(value = "eduVideo", name = "课时信息", required = true)
            @RequestBody EduVideo eduVideo
    ) {
        boolean save = eduVideoService.save(eduVideo);
        if (save) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "修改课时")
    @PostMapping("/updateEduVideo")
    public Result updateEduVideo(
            @ApiParam(value = "eduVideo", name = "课时信息", required = true)
            @RequestBody EduVideo eduVideo
    ) {
        boolean update = eduVideoService.updateById(eduVideo);
        if (update) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "删除课时")
    @DeleteMapping("/{id}")
    public Result deleteEduVideo(
            @ApiParam(value = "id", name = "课时id", required = true)
            @PathVariable String id
    ) {
        EduVideo eduVideo = eduVideoService.getById(id);
        if(eduVideo != null && StringUtils.isNotBlank(eduVideo.getVideoSourceId())){
            System.out.println("调用了vod-service");
            vodClient.removeVideo(eduVideo.getVideoSourceId());
        }
        boolean remove = eduVideoService.removeById(id);
        if (remove) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "根据id获取课时信息")
    @GetMapping("/findEduVideo/{id}")
    public Result findEduVideo(
            @ApiParam(value = "", name = "", required = true)
            @PathVariable String id
    ) {
        EduVideo eduVideo = eduVideoService.getById(id);
        if (eduVideo != null) {
            return Result.ok().data("video", eduVideo);
        }
        return Result.error();
    }
}


package com.pokaboo.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.baomidou.mybatisplus.extension.api.R;
import com.pokaboo.commonutils.Result;
import com.pokaboo.servicebase.exceptionhandler.MyException;
import com.pokaboo.vod.service.VideoService;
import com.pokaboo.vod.utils.AliyunVodSDKUtils;
import com.pokaboo.vod.utils.ConstantVodUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author 阿里云视频点播
 */
@Api(value = "阿里云视频点播", tags = {"阿里云视频点播"})
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "视频上传")
    @PostMapping("/uploadVideo")
    public Result uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoId = videoService.uploadVideo(file);
        return Result.ok().message("视频上传成功").data("videoId", videoId);
    }

    @ApiOperation(value = "视频删除")
    @DeleteMapping("/removeVideo/{videoId}")
    public Result removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                              @PathVariable String videoId) {

        videoService.removeVideo(videoId);
        return Result.ok().message("视频删除成功");
    }

    @ApiOperation(value = "批量视频删除")
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(
            @ApiParam(name = "videoIdList", value = "云端视频id集合", required = true)
            @RequestParam("videoIdList") List<String> videoIdList) {
        videoService.deleteBatchAliyunVideo(videoIdList);
        return Result.ok();
    }


    @ApiOperation(value = "获取视频凭证")
    @GetMapping("/get-play-auth/{vid}")
    public Result getVideoPlayAuth(
            @ApiParam(name = "vid", value = "视频id", required = true)
            @PathVariable("vid") String vid) throws ClientException {
        try {
            //初始化
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantVodUtil.ACCESS_KEY_ID,
                    ConstantVodUtil.ACCESS_KEY_SECRET);
            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(vid);
            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //得到播放凭证
            String playAuth = response.getPlayAuth();
            return Result.ok().message("获取凭证成功").data("playAuth", playAuth);
        } catch (Exception e) {
            throw new MyException(20001, "获取视频凭证失败");
        }
    }
}

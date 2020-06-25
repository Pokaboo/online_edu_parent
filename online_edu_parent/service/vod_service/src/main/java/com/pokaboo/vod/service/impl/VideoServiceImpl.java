package com.pokaboo.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.pokaboo.servicebase.exceptionhandler.MyException;
import com.pokaboo.vod.service.VideoService;
import com.pokaboo.vod.utils.AliyunVodSDKUtils;
import com.pokaboo.vod.utils.ConstantVodUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author pokab
 */
@Service
public class VideoServiceImpl implements VideoService {

    /**
     * 上传视频到阿里云
     *
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtil.ACCESS_KEY_ID,
                    ConstantVodUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if (StringUtils.isEmpty(videoId)) {
                    throw new MyException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new MyException(20001, "视频服务上传失败");
        }
    }

    /**
     * 删除视频
     *
     * @param videoId
     */
    @Override
    public void removeVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantVodUtil.ACCESS_KEY_ID,
                    ConstantVodUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            throw new MyException(20001, "视频删除失败");
        }
    }

    /**
     * 批量删除视频
     * @param videoIdList
     */
    @Override
    public void deleteBatchAliyunVideo(List<String> videoIdList) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantVodUtil.ACCESS_KEY_ID,
                    ConstantVodUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(org.apache.commons.lang.StringUtils.join(videoIdList.toArray(),","));
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            throw new MyException(20001, "视频删除失败");
        }
    }
}
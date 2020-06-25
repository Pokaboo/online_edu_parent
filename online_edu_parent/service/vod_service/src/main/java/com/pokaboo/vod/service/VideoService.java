package com.pokaboo.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author pokab
 */
public interface VideoService {

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 删除视频
     * @param videoId
     */
    void removeVideo(String videoId);

    /**
     * 批量删除视频
     * @param videoIdList
     */
    void deleteBatchAliyunVideo(List<String> videoIdList);
}

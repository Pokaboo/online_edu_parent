package com.pokaboo.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author pokab
 */
public interface FileService {
    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}

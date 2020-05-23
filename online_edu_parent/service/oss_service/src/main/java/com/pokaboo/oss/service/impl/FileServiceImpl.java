package com.pokaboo.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.pokaboo.oss.service.FileService;
import com.pokaboo.oss.utils.ConstantPropertiesUtil;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author pokab
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 上传文件到aliyun OSS
     *
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        // 上传文件流。
        InputStream inputStream = null;
        String url = "";
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            String fileName = file.getOriginalFilename();
            inputStream = file.getInputStream();

            // 防止后面上传的图片覆盖前面的图片，图片名称都加上uuid
            String imgUUID = UUID.randomUUID().toString().replaceAll("-", "");
            //使用joda-time工具类 设置文件夹名称
            String folderName = new DateTime().toString("yyyy/MM/DD");
            fileName = folderName + imgUUID + fileName;

            //bucket名称   文件名称/路径
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //https://edu-repo.oss-cn-chengdu.aliyuncs.com/%E5%A4%B4%E5%83%8F1.jpg
            url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return url;
    }
}

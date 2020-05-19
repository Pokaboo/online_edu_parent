package com.pokaboo.oss.controller;

import com.pokaboo.commonutils.Result;
import com.pokaboo.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pokab
 * 文件上传
 */
@Api(value = "上传文件", tags = {"上传文件"})
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class FileOSSController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传头像")
    @PostMapping(value = "/upload", headers="content-type=multipart/form-data")
    public Result uploadFile(@ApiParam(value = "上传的文件", required = true) MultipartFile file) {
        String url = fileService.upload(file);
        return Result.ok().message("头像上传成功").data("url",url);
    }
}

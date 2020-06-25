package com.pokaboo.eduservice.client;

import com.pokaboo.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author pokaboo
 * @FeignClient注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
 * @GetMapping注解用于对被调用的微服务进行地址映射。
 * @PathVariable注解一定要指定参数名称，否则出错
 * @Component注解防止，在其他位置注入CodClient时idea报错
 */
@FeignClient("vod-service")
@Component
public interface VodClient {

    /**
     * 调用vod-service中的删除视频方法
     * @param videoId
     * @return
     */
    @DeleteMapping("/eduvod/video/removeVideo/{videoId}")
    public Result removeVideo(@PathVariable("videoId") String videoId) ;
}

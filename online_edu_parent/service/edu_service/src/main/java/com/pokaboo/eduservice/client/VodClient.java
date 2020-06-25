package com.pokaboo.eduservice.client;

import com.pokaboo.commonutils.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author pokaboo
 * @FeignClient注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
 * @GetMapping注解用于对被调用的微服务进行地址映射。
 * @PathVariable注解一定要指定参数名称，否则出错
 * @Component注解防止，在其他位置注入CodClient时idea报错
 * fallback = VodFileDegradeFeignClient.class 熔断器
 */
@FeignClient(name = "vod-service", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    /**
     * 调用vod-service中的删除视频方法
     *
     * @param videoId
     * @return
     */
    @DeleteMapping("/eduvod/video/removeVideo/{videoId}")
    public Result removeVideo(@PathVariable("videoId") String videoId);


    /**
     * 调用vod-service中的批量删除视频
     *
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/eduvod/video/deleteBatch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}

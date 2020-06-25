package com.pokaboo.eduservice.client;

import com.pokaboo.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pokaboo
 * Hystrix 熔断器
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    @Override
    public Result removeVideo(String videoId) {
        return Result.error().message("Hystrix————> time out");
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        return Result.error().message("Hystrix————> time out");
    }
}
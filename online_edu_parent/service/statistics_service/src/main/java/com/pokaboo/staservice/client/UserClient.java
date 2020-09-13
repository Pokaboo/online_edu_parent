package com.pokaboo.staservice.client;

import com.pokaboo.commonutils.Result;
import feign.Param;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author pokaboo
 */
@FeignClient(name = "user-service")
@Component
public interface UserClient {

    @GetMapping(value = "/eduucenter/ucenter/countregister/{day}")
    public Result registerCount(@PathVariable("day") String day);
}

package com.pokaboo.cmsservice.controller;


import com.pokaboo.cmsservice.entity.CrmBanner;
import com.pokaboo.cmsservice.service.CrmBannerService;
import com.pokaboo.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author pookaboo
 * @since 2020-06-27
 */
@Api(value = "banner管理", tags = {"banner管理"})
@RestController
@RequestMapping("/educms/frontbanner")
@CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("/getAllBanner")
    public Result getAllBanner() {
        List<CrmBanner> list = crmBannerService.selectList();
        return Result.ok().data("bannerList", list);
    }

}


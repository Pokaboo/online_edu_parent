package com.pokaboo.cmsservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pokaboo.cmsservice.entity.CrmBanner;
import com.pokaboo.cmsservice.service.CrmBannerService;
import com.pokaboo.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author pookaboo
 * @since 2020-06-27
 */
@Api(value = "banner后台管理", tags = {"banner后台管理"})
@RestController
@RequestMapping("/cmsservice/adminbanner")
@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("/findPageBanner/{page}/{limit}")
    public Result findPageBanner(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<CrmBanner> pageParam = new Page<>(page, limit);
        crmBannerService.page(pageParam, null);
        return Result.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("/findBanner/{id}")
    public Result findBanner(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return Result.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("/saveBanner")
    public Result saveBanner(@RequestBody CrmBanner banner) {
        crmBannerService.save(banner);
        return Result.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/updateBanner")
    public Result updateBanner(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return Result.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/removeBanner/{id}")
    public Result removeBanner(@PathVariable String id) {
        crmBannerService.removeById(id);
        return Result.ok();
    }
}

package com.pokaboo.cmsservice.service;

import com.pokaboo.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author pookaboo
 * @since 2020-06-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 获取所以的banner
     * @return
     */
    List<CrmBanner> selectList();
}

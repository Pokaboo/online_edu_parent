package com.pokaboo.cmsservice.service.impl;

import com.pokaboo.cmsservice.entity.CrmBanner;
import com.pokaboo.cmsservice.mapper.CrmBannerMapper;
import com.pokaboo.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author pookaboo
 * @since 2020-06-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 获取所有的banner
     * @return
     */
    @Override
    public List<CrmBanner> selectList() {
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }
}

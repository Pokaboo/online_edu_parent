package com.pokaboo.userservice.mapper;

import com.pokaboo.userservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author pookaboo
 * @since 2020-08-15
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 获取指定日期的注册人数
     * @param day
     * @return
     */
    int countRegisterByDay(String day);
}

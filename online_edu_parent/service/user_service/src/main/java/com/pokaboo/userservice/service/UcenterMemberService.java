package com.pokaboo.userservice.service;

import com.pokaboo.userservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pokaboo.userservice.entity.vo.LoginVo;
import com.pokaboo.userservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author pookaboo
 * @since 2020-08-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 获取登录对象
     * @param memberId
     * @return
     */
    UcenterMember getLoginInfo(String memberId);
}

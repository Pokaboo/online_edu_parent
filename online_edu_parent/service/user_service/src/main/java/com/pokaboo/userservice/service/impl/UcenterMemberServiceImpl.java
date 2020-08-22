package com.pokaboo.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pokaboo.commonutils.JwtUtils;
import com.pokaboo.servicebase.exceptionhandler.MyException;
import com.pokaboo.userservice.entity.UcenterMember;
import com.pokaboo.userservice.entity.vo.LoginVo;
import com.pokaboo.userservice.entity.vo.RegisterVo;
import com.pokaboo.userservice.mapper.UcenterMemberMapper;
import com.pokaboo.userservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pokaboo.userservice.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author pookaboo
 * @since 2020-08-15
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    /**
     * 登录
     *
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if (StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(mobile)) {
            throw new MyException(20001, "error");
        }

        //获取会员
        UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (null == member) {
            throw new MyException(20001, "error");
        }

        //校验密码
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new MyException(20001, "error");
        }

        //校验是否被禁用
        if (member.getIsDisabled()) {
            throw new MyException(20001, "error");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    /**
     * 注册
     *
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new MyException(20001, "error");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (count.intValue() > 0) {
            throw new MyException(20001, "error");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://edu-repo.oss-cn-chengdu.aliyuncs.com/avatar/default.jpg");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getLoginInfo(String memberId) {
        return baseMapper.selectById(memberId);
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);

        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);

        return ucenterMember;
    }
}

package com.pokaboo.msm.service;

/**
 * @author pokaboo
 */
public interface MsmService {
    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    boolean sendMsg(String phone);
}

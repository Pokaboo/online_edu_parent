package com.pokaboo.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author pokaboo
 * 常量读取工具类
 * @Value读取application.properties里的配置内容
 * 用spring的 InitializingBean 的 afterPropertiesSet 来初始化配置信息，这个方法将在所有的属性被初始化后调用。
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {


    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {

        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;

    }
}

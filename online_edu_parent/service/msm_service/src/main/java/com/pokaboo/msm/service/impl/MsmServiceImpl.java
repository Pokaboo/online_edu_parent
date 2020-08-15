package com.pokaboo.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.pokaboo.msm.service.MsmService;
import com.pokaboo.msm.utils.ConstantPropertiesUtil;
import com.pokaboo.msm.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pokaboo
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @Override
    public boolean sendMsg(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        Map<String, Object> params = new HashMap<>();
        String code = RandomUtil.getSixBitRandom();
        params.put("code", code);

        DefaultProfile profile =
                DefaultProfile.getProfile("default", ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "myedu学习网站");
        request.putQueryParameter("TemplateCode", "SMS_199196378");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}

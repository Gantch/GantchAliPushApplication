package com.gantch.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushMessageToAndroidRequest;
import com.aliyuncs.push.model.v20160801.PushMessageToAndroidResponse;
import com.gantch.bean.Config;
import com.gantch.bean.MaoyanDeviceBean;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("api/v1/aliPush")
public class PushController {


    @Autowired
    private Config config;

    public MaoyanDeviceBean maoyanDeviceBean;

    protected static String region;
    protected static long appKey;
    protected static String deviceIds;
    protected static String deviceId;
    protected static String accounts;
    protected static String account;
    protected static String aliases;
    protected static String alias;
    protected static String tag;
    protected static String tagExpression;

    protected static DefaultAcsClient client;

    /**
     * 推送消息接口
     * @param Info
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/aliPushToAndroid/", method = RequestMethod.POST)
    @ResponseBody
    public String pushAndroid(@RequestBody String Info) throws Exception {
        JsonObject info = new JsonParser().parse(Info).getAsJsonObject();
        String devName = info.get("devName").getAsString();
        String devId = info.get("devId").getAsString();
        String devDID = info.get("devDID").getAsString();
        String context = info.get("context").getAsString();
        String type = info.get("type").getAsString();

        region = config.getRegionId();
        appKey = config.getAppKey();
        deviceIds = config.getDeviceIds();
        deviceId = config.getDeviceId();
        accounts = config.getAccounts();
        account = config.getAccount();
        aliases = config.getAliases();
        alias = config.getAlias();
        tag = config.getTag();
        tagExpression = config.getTagExpression();

        IClientProfile profile = DefaultProfile.getProfile(region, config.getAccessKeyId(), config.getAccessKeySecret());
        client = new DefaultAcsClient(profile);

        PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
        //安全性比较高的内容建议使用HTTPS
        androidRequest.setProtocol(ProtocolType.HTTPS);
        //内容较大的请求，使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("ALL");
        androidRequest.setTargetValue("ALL");
        androidRequest.setTitle("66666666666");
        androidRequest.setBody(devId);

        PushMessageToAndroidResponse pushMessageToAndroidResponse = client.getAcsResponse(androidRequest);
        System.out.printf("RequestId: %s, MessageId: %s\n",
                pushMessageToAndroidResponse.getRequestId(), pushMessageToAndroidResponse.getMessageId());

        return "success";
    }
}

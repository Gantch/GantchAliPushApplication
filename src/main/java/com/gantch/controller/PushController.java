package com.gantch.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushMessageToAndroidRequest;
import com.aliyuncs.push.model.v20160801.PushMessageToAndroidResponse;
import com.gantch.bean.Config;
import com.gantch.bean.Device;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("api/v1/aliPush")
public class PushController {


    @Autowired
    private Config config;

    public Device maoyanDeviceBean;

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
    DateTime dateTime = new DateTime();

    protected static DefaultAcsClient client;

    /**
     * 推送消息接口
     * @param Info
     * @return String success
     * @throws Exception
     */
    @RequestMapping(value = "/aliPushToAndroid/", method = RequestMethod.POST)
    @ResponseBody
    public String pushAndroid(@RequestBody String Info) throws Exception {
        JsonObject info = new JsonParser().parse(Info).getAsJsonObject();
        if (info.get("devId") == null){
            return "error";
        }
        if (info.get("devName") == null){
            return "error";
        }

        String devId = info.get("devId").getAsString();
        String devName = info.get("devName").getAsString();
        String devDID = info.get("devDID").getAsString();
        String context = info.get("context").getAsString();
        String type = info.get("type").getAsString();
        System.out.println("DEVICE_ID:"+devId+" "
                    +"DEVICE_NAME:"+devName+ " "
                    +"DEVICE_DID:"+devDID+ " "
                    +"DEVICE_CONTEXT:"+context+ " "
                    +"DEVICE_TYPE:"+type+ " ");
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
        androidRequest.setTitle(context);
        androidRequest.setBody(devId);


        PushMessageToAndroidResponse pushMessageToAndroidResponse = client.getAcsResponse(androidRequest);
        System.out.printf(dateTime+" "+"RequestId: %s, MessageId: %s\n",
                pushMessageToAndroidResponse.getRequestId(), pushMessageToAndroidResponse.getMessageId());

        return "success";
    }
}

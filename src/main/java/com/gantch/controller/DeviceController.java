package com.gantch.controller;

import com.gantch.bean.Device;
import com.gantch.service.DeviceService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/maoyan")
public class DeviceController {

    @Autowired
    public DeviceService deviceService;

    /**
     * 获取猫眼设备接口
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/getDevice/{customerId}",method = RequestMethod.GET)
    @ResponseBody
    public Device getMaoYanDevice(@PathVariable("customerId")Integer customerId){
        Device device=deviceService.getDevice(customerId);
        System.out.println(device);
        if(device == null){
            return null;
        }
        return device;
    }

    /**
     * 添加猫眼设备接口
     * @param Info
     * @return
     */
    @RequestMapping(value ="/addDevice",method = RequestMethod.POST)
    @ResponseBody
    public String addMaoYanDevice(@RequestBody String Info){
        JsonObject info = new JsonParser().parse(Info).getAsJsonObject();
        if (info.get("customerId") == null){
            return "error";
        }
        if (info.get("deviceId") == null){
            return "error";
        }
        Integer customerId = info.get("customerId").getAsInt();
        String deviceId = info.get("deviceId").getAsString();
        Boolean verify=deviceService.addDevice(customerId,deviceId);
        if (verify==true){
            System.out.println("添加成功");
            return "success";
        }else{
            System.out.println("添加失败");
            return "fail";
        }
    }

}

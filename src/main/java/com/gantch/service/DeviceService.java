package com.gantch.service;

import com.gantch.bean.Device;
import com.gantch.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    public Device getDevice(Integer customerId){
        return deviceMapper.getDevice(customerId);
    }

    public Boolean addDevice(Integer customerId,String deviceId){
        try{
            deviceMapper.addDevice(customerId,deviceId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

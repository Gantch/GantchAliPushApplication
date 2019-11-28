package com.gantch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaoyanDeviceBean {
    public String deviceName;
    public String deviceId;
    public String type;
    public Date time;
}

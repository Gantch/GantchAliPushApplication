package com.gantch.mapper;


import com.gantch.bean.Device;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DeviceMapper {

    @Select("SELECT * FROM maoyandevice WHERE customerId = #{customerId}")
    Device getDevice(@Param("customerId")Integer customerId);

    @Insert("INSERT INTO maoyandevice(customerId,deviceId) VALUES(#{customerId},#{deviceId})")
    void addDevice(@Param("customerId")Integer customerId,@Param("deviceId")String deviceId);

    @Update("UPDATE maoyandevice SET deviceId")
    void updateDevice(@Param("deviceId")String deviceId);

}

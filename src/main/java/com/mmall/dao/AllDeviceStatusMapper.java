package com.mmall.dao;

import com.mmall.pojo.AllDeviceStatus;
import com.mmall.pojo.DeviceStatus;

import java.util.List;

public interface AllDeviceStatusMapper {

    DeviceStatus selectByPrimaryKey(Integer id);

    int insert(AllDeviceStatus allDeviceStatus);

    int update(DeviceStatus device);

    List<AllDeviceStatus> selectByDeviceId(Integer device_id);


}
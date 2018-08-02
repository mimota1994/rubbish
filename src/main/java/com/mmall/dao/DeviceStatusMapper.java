package com.mmall.dao;

import com.mmall.pojo.DeviceStatus;

public interface DeviceStatusMapper {

    DeviceStatus selectByPrimaryKey(Integer id);

    int insert(DeviceStatus device);

    int update(DeviceStatus device);


}
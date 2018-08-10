package com.mmall.service;

import com.mmall.pojo.DeviceStatus;

public interface IAllDeviceStatusService {


    void addAllDeviceStatus(Object msg);

    Float evaluateDeviceStatusByDeviceId(Integer device_id);

}

package com.mmall.service;

import com.mmall.pojo.Device;
import com.mmall.pojo.DeviceStatus;

public interface IDeviceStatusService {

    void updateDeviceStatus(Object msg);

    void addDeviceStatus(Integer Id);

    DeviceStatus checkDeviceStatus(Integer Id);
}

package com.mmall.service;

import com.mmall.pojo.Device;

public interface IDeviceService {

    void updateDevice(Object msg);

    void addDevice(Device device);

    Device checkDevice(Integer Id);
}

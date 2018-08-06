package com.mmall.service;

import com.mmall.vo.DeviceVo;

import java.util.List;

public interface IDeviceService {
    void addDevice(String title, String imei, String imsi, Float lon, Float lat, String apiKey);

    List<DeviceVo> checkAllDevice();
}

package com.mmall.service.impl;

import com.mmall.dao.DeviceMapper;
import com.mmall.pojo.Device;
import com.mmall.service.IDeviceService;
import com.mmall.util.HttpSendCenter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iDeviceService")
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    @Override
    public void addDevice(String title, String imei, String imsi,String apiKey) {
        // TODO: 2018-08-01 装配device
        Device device = new Device(title, imei, imsi);

        JSONObject msg = HttpSendCenter.post(apiKey,device.toUrl(),device.toJsonObject());
        int errno = (int)msg.get("errno");
        String error = (String)msg.get("error");
        JSONObject data = (JSONObject)msg.get("data");
        String str_dev_id = (String)data.get("device_id");
        int dev_id = Integer.valueOf(str_dev_id).intValue();

        device.setId(dev_id);

        deviceMapper.insert(device);

     }
}

package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.dao.DeviceMapper;
import com.mmall.dao.DeviceStatusMapper;
import com.mmall.pojo.Device;
import com.mmall.pojo.DeviceStatus;
import com.mmall.service.IDeviceService;
import com.mmall.service.IDeviceStatusService;
import com.mmall.util.HttpSendCenter;
import com.mmall.vo.DeviceVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("iDeviceService")
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceStatusMapper deviceStatusMapper;

    @Autowired
    private IDeviceStatusService iDeviceStatusService;

    @Override
    public void addDevice(String title, String imei, String imsi,Float lon ,Float lat ,String apiKey) {
        // TODO: 2018-08-01 装配device
        Device device = new Device(title, imei, imsi);
        device.setLon(lon);
        device.setLat(lat);

        JSONObject msg = HttpSendCenter.post(apiKey,device.toUrl(),device.toJsonObject());
        int errno = (int)msg.get("errno");
        String error = (String)msg.get("error");
        JSONObject data = (JSONObject)msg.get("data");
        String str_dev_id = (String)data.get("device_id");
        int dev_id = Integer.valueOf(str_dev_id).intValue();

        device.setId(dev_id);

        deviceMapper.insert(device);

        // 添加完设备之后最好直接初始化一个deviceStatus，要不查询的时候会有空指针异常
        iDeviceStatusService.addDeviceStatus(dev_id);

     }

    @Override
    public List<DeviceVo> checkAllDevice() {

        List<Device> deviceList = deviceMapper.selectAllDevice();
        //装配DeviceVo
        List<DeviceVo> deviceVoList = Lists.newArrayList();
        for(Device device : deviceList){
            DeviceVo deviceVo = assembleDeviceVo(device);
            deviceVoList.add(deviceVo);
        }
        return deviceVoList;
    }

    private DeviceVo assembleDeviceVo(Device device){
        DeviceVo deviceVo = new DeviceVo();
        int dev_id = device.getId();
        DeviceStatus deviceStatus = deviceStatusMapper.selectByPrimaryKey(dev_id);

        deviceVo.setId(dev_id);
        Float lon = device.getLon();
        Float lat = device.getLat();
        HashMap location = new HashMap();
        location.put("lon",lon);
        location.put("lat",lat);
        deviceVo.setLocation(location);
        deviceVo.setStatus(deviceStatus.getStatus());

        return deviceVo;
    }
}

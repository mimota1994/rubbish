package com.mmall.service.impl;

import com.mmall.dao.DeviceMapper;
import com.mmall.pojo.Device;
import com.mmall.service.IDeviceService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.dsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

@Controller("iDeviceService")
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public void updateDevice(Object msg) {
        Map mapMsg = (Map) msg;
        int type = (Integer)mapMsg.get("type");
        if(type == 1){
            int dev_id = (Integer)mapMsg.get("dev_id");
            String ds_id = (String)mapMsg.get("ds_id");

            String at_time = ((Long)mapMsg.get("at")).toString();
            Date time = DateTimeUtil.stampToDate(at_time);

            Double value = (Double)mapMsg.get("value");

            Device device = checkDevice(dev_id);

            String[] dsArray = dsUtil.splitDs(ds_id);

            String a = dsArray[0];
            if(a .equals("3303")){
                device.setTem(value.floatValue());
            }else{
                device.setHum(value.floatValue());
            }

            deviceMapper.update(device);
        }
        if(type == 2){
            // TODO: 2018-07-31 处理上下线消息 
        }
    }

    public void addDevice(Device device){
        int rowCount = deviceMapper.insert(device);
    }

    public Device checkDevice(Integer Id){
        Device device = deviceMapper.selectByPrimaryKey(Id);
        return device;
    }


}

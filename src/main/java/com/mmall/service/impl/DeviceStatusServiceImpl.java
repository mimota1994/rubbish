package com.mmall.service.impl;

import com.mmall.dao.DeviceStatusMapper;
import com.mmall.pojo.Device;
import com.mmall.pojo.DeviceStatus;
import com.mmall.service.IDeviceStatusService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.dsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Map;

@Controller("iDeviceStatusService")
public class DeviceStatusServiceImpl implements IDeviceStatusService {

    @Autowired
    private DeviceStatusMapper deviceStatusMapper;

    @Override
    public void updateDeviceStatus(Object msg) {
        Map mapMsg = (Map) msg;
        int type = (Integer)mapMsg.get("type");
        if(type == 1){
            int dev_id = (Integer)mapMsg.get("dev_id");
            String ds_id = (String)mapMsg.get("ds_id");

            String at_time = ((Long)mapMsg.get("at")).toString();
            Date time = DateTimeUtil.stampToDate(at_time);

            Double value = (Double)mapMsg.get("value");

            DeviceStatus deviceStatus = checkDeviceStatus(dev_id);

            String[] dsArray = dsUtil.splitDs(ds_id);

            String a = dsArray[0];
            if(a .equals("3303")){
                deviceStatus.setTem(value.floatValue());
            }else{
                deviceStatus.setHum(value.floatValue());
            }

            deviceStatusMapper.update(deviceStatus);
        }
        if(type == 2){
            // TODO: 2018-07-31 处理上下线消息
            int dev_id = (Integer)mapMsg.get("dev_id");
            int status = (Integer)mapMsg.get("status");

            DeviceStatus deviceStatus = checkDeviceStatus(dev_id);
            deviceStatus.setStatus(status);
            deviceStatusMapper.update(deviceStatus);
        }
    }

    public void addDeviceStatus(DeviceStatus deviceStatus){
        int rowCount = deviceStatusMapper.insert(deviceStatus);
    }

    public DeviceStatus checkDeviceStatus(Integer Id){
        DeviceStatus deviceStatus = deviceStatusMapper.selectByPrimaryKey(Id);
        return deviceStatus;
    }


}

package com.mmall.service.impl;

import com.mmall.dao.AllDeviceStatusMapper;
import com.mmall.dao.DeviceStatusMapper;
import com.mmall.pojo.AllDeviceStatus;
import com.mmall.pojo.DeviceStatus;
import com.mmall.service.IAllDeviceStatusService;
import com.mmall.service.IDeviceStatusService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.dsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller("iAllDeviceStatusService")
public class AllDeviceStatusServiceImpl implements IAllDeviceStatusService {


    @Autowired
    private AllDeviceStatusMapper allDeviceStatusMapper;

    @Override
    public void addAllDeviceStatus(Object msg) {
        Map mapMsg = (Map) msg;
        // TODO: 2018-08-02 msg如果是列表的话还得再考虑
        int type = (Integer)mapMsg.get("type");
        if(type == 1){
            int dev_id = (Integer)mapMsg.get("dev_id");
            String ds_id = (String)mapMsg.get("ds_id");

            String at_time = ((Long)mapMsg.get("at")).toString();
            Date time = DateTimeUtil.stampToDate(at_time);

            Double value = (Double)mapMsg.get("value");

            AllDeviceStatus allDeviceStatus = new AllDeviceStatus();

            allDeviceStatus.setDevice_id(dev_id);

            String[] dsArray = dsUtil.splitDs(ds_id);

            String a = dsArray[0];
            if(a .equals("3303")){
                allDeviceStatus.setTem(value.floatValue());
            }else{
                allDeviceStatus.setHum(value.floatValue());
            }

            allDeviceStatusMapper.insert(allDeviceStatus);
        }
        if(type == 2) {
            // TODO: 2018-07-31 处理上下线消息
        }
    }

    @Override
    public Float evaluateDeviceStatusByDeviceId(Integer device_id) {
        List<AllDeviceStatus> allDeviceStatusList =  allDeviceStatusMapper.selectByDeviceId(device_id);
        Float result = evaluate(allDeviceStatusList);
        return result;
    }

    private Float evaluate(List<AllDeviceStatus> allDeviceStatusList){
        Float result = (float)0.0;
        // TODO: 2018-08-10 预测算法
        return result;
    }

//    public void addDeviceStatus(Integer Id){
//        DeviceStatus deviceStatus = new DeviceStatus();
//        deviceStatus.setStatus(0);
//        deviceStatus.setDeviceId(Id);
//
//        int rowCount = deviceStatusMapper.insert(deviceStatus);
//    }

//    public DeviceStatus checkDeviceStatus(Integer Id){
//        DeviceStatus deviceStatus = deviceStatusMapper.selectByPrimaryKey(Id);
//        return deviceStatus;
//    }

}

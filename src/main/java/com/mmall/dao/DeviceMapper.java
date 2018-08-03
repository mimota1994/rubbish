package com.mmall.dao;

import com.mmall.pojo.Device;

import java.util.List;

public interface DeviceMapper {

    int insert(Device device);

    List<Device> selectAllDevice();
}

package com.mmall.dao;

import com.mmall.pojo.Device;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DeviceMapper {

    Device selectByPrimaryKey(Integer id);

    int insert(Device device);

    int update(Device device);


}
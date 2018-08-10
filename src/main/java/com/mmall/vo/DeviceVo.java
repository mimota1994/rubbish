package com.mmall.vo;

import org.json.JSONObject;

import java.util.HashMap;

public class DeviceVo {

    private Integer deviceId;

    private HashMap location;

    private Integer status;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public HashMap getLocation() {
        return location;
    }

    public void setLocation(HashMap location) {
        this.location = location;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

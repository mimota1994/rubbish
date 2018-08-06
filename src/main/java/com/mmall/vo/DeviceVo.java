package com.mmall.vo;

import org.json.JSONObject;

import java.util.HashMap;

public class DeviceVo {

    private Integer id;

    private HashMap location;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

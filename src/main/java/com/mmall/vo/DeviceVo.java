package com.mmall.vo;

import org.json.JSONObject;

public class DeviceVo {

    private Integer id;

    private JSONObject location;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JSONObject getLocation() {
        return location;
    }

    public void setLocation(JSONObject location) {
        this.location = location;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

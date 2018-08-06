package com.mmall.pojo;

import java.util.Date;

public class DeviceStatus {
    private Integer id;

    private Float tem;

    private Float hum;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public DeviceStatus(Integer id, Float tem, Float hum, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.tem = tem;
        this.hum = hum;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DeviceStatus() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getTem() {
        return tem;
    }

    public void setTem(float tem) {
        this.tem = tem;
    }

    public float getHum() {
        return hum;
    }

    public void setHum(float hum) {
        this.hum = hum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
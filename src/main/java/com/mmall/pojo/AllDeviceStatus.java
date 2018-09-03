package com.mmall.pojo;

import org.joda.time.DateTime;

import java.util.Date;

public class AllDeviceStatus {
    private Integer id;
    private Integer device_id;
    private Float tem;
    private Float hum;
    private Date createTime;
    private Date updateTime;

    public AllDeviceStatus(Integer id, Integer device_id, Float tem, Float hum, Date createTime, Date updateTime) {
        this.id = id;
        this.device_id = device_id;
        this.tem = tem;
        this.hum = hum;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public AllDeviceStatus(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Float getTem() {
        return tem;
    }

    public void setTem(Float tem) {
        this.tem = tem;
    }

    public Float getHum() {
        return hum;
    }

    public void setHum(Float hum) {
        this.hum = hum;
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

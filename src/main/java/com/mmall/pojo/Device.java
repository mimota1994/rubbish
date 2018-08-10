package com.mmall.pojo;

import com.mmall.util.config.Config;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuocongbin
 * date 2018/3/16
 */
public class Device {
    // 设备imei号，平台唯一，必填参数
    protected String imei;
    // ISPO标准中的Object ID
    protected Integer objId;
    // ISPO标准中的Object Instance ID
    protected Integer objInstId;
    // ISPO标准中的Resource ID
    protected Integer resId;
    // 设备名称，字符和数字组成的字符串，必填参数
    private String title;
    // 设备描述信息，可填参数
    private String desc;
    // 设备标签，可填参数
//    private List<String> tags;
    // 设备接入协议，这里指定为: LWM2M，必填参数
    private String protocol;
    // 设备地理位置，格式为：{"lon": 106, "lat": 29, "ele": 370}，可填参数
//    private JSONObject location;
    private Float lon;
    private Float lat;

    private Date cteateTime;
    private Date updateTime;

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    // 设备IMSI，必填参数
    private String imsi;
    // 设备接入平台是否启用自动订阅功能，可填参数
    private Boolean obsv;
    // 其他信息，可填参数
//    private JSONObject other;

    private Integer deviceId;

    /**
     * @param title，有字符或者数字组成，必填
     * @param imei，要求在OneNET平台唯一，必填
     * @param imsi，必填
     */
    public Device(String title, String imei, String imsi) {
        this.title = title;
        this.imei = imei;
        this.imsi = imsi;
        this.protocol = "LWM2M";
    }

    public Device(Integer deviceId, String imei, Integer objId, Integer objInstId, Integer resId, String title, String desc, String protocol, Float lon, Float lat,String imsi, Boolean obsv, Date cteateTime, Date updateTime ) {
        this.imei = imei;
        this.objId = objId;
        this.objInstId = objInstId;
        this.resId = resId;
        this.title = title;
        this.desc = desc;
        this.protocol = protocol;
        this.lon = lon;
        this.lat = lat;
        this.cteateTime = cteateTime;
        this.updateTime = updateTime;
        this.imsi = imsi;
        this.obsv = obsv;
        this.deviceId = deviceId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getObjInstId() {
        return objInstId;
    }

    public void setObjInstId(Integer objInstId) {
        this.objInstId = objInstId;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public void setObsv(Boolean obsv) {
        this.obsv = obsv;
    }



    public void setDesc(String desc) {
        this.desc = desc;
    }



    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", this.title);
        if (StringUtils.isNotBlank(this.desc)) {
            jsonObject.put("desc", this.desc);
        }
        jsonObject.put("protocol", this.protocol);
        if (this.lon != null&&this.lat !=null) {
            Map<String,Float> location = new HashMap<>();
            location.put("lon",this.lon);
            location.put("lat",this.lat);
            jsonObject.put("location", location);
        }
        JSONObject authInfo = new JSONObject();
        authInfo.put(imei, imsi);
        jsonObject.put("auth_info", authInfo);
        if (this.obsv != null) {
            jsonObject.put("obsv", this.obsv);
        }
//        if (this.other != null) {
//            jsonObject.put("other", this.other);
//        }

        return jsonObject;
    }
    public String toUrl() {
        StringBuilder url = new StringBuilder(Config.getDomainName());
        url.append("/devices");
        return url.toString();
    }
}

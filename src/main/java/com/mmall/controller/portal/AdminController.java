package com.mmall.controller.portal;

import com.google.gson.JsonObject;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Device;
import com.mmall.service.IAllDeviceStatusService;
import com.mmall.service.IDeviceService;
import com.mmall.service.IDeviceStatusService;
import com.mmall.util.HttpSendCenter;
import com.mmall.vo.DeviceListVo;
import com.mmall.vo.DeviceVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.List;


@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private IDeviceService iDeviceService;

    @Autowired
    private IAllDeviceStatusService iAllDeviceStatusService;

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
    String apiKey = "C7de=t7ZiP2J8rt3wGi5nHTJ9xk=";

    @RequestMapping(value = "add_device.do")
    @ResponseBody
    public void addDevice(@RequestParam(value = "title") String title,
                        @RequestParam(value = "imei") String imei,
                        @RequestParam(value = "imsi") String imsi,
                          @RequestParam(value = "lon") Float lon,
                          @RequestParam(value = "lat") Float lat)  {

        logger.info("check: title:{} imei{} imsi:{}",title,imei,imsi);

        iDeviceService.addDevice(title,imei,imsi,lon,lat,apiKey);
        // TODO: 2018-08-02 返回什么好呢？ 

    }

    @RequestMapping(value = "check_all_device.do", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse<DeviceListVo> checkAllDevice()  {

        List<DeviceVo> deviceVoList = iDeviceService.checkAllDevice();
        DeviceListVo deviceListVo = new DeviceListVo();
        deviceListVo.setDeviceVoList(deviceVoList);
//        JSONObject msg = new JSONObject();
//        msg.put("data",deviceVoList);
        return ServiceResponse.creatBySuccess(deviceListVo);
    }

    @RequestMapping(value = "evaluate_device_status")
    @ResponseBody
    public ServiceResponse evaluateDeviceStatus(Integer device_id){
        Float result = iAllDeviceStatusService.evaluateDeviceStatusByDeviceId(device_id);
        return ServiceResponse.creatBySuccess(result);
    }

}

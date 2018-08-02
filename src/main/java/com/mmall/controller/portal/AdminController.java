package com.mmall.controller.portal;

import com.google.gson.JsonObject;
import com.mmall.pojo.Device;
import com.mmall.service.IDeviceService;
import com.mmall.service.IDeviceStatusService;
import com.mmall.util.HttpSendCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;


@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private IDeviceService iDeviceService;

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
    String apiKey = "C7de=t7ZiP2J8rt3wGi5nHTJ9xk=";

    @RequestMapping(value = "add_device.do", method = RequestMethod.GET)
    @ResponseBody
    public void addDevice(@RequestParam(value = "title") String title,
                        @RequestParam(value = "imei") String imei,
                        @RequestParam(value = "imsi") String imsi)  {

        logger.info("check: title:{} imei{} imsi:{}",title,imei,imsi);

        iDeviceService.addDevice(title,imei,imsi,apiKey);
        // TODO: 2018-08-02 返回什么好呢？ 

    }

}
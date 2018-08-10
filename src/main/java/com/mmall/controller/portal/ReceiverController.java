package com.mmall.controller.portal;

import com.mmall.service.IAllDeviceStatusService;
import com.mmall.service.IDeviceStatusService;
import com.mmall.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/receive/")
public class ReceiverController {

    private static String token ="abcdefghijkmlnopqrstuvwxyz";//用户自定义token和OneNet第三方平台配置里的token一致
    private static String aeskey ="whBx2ZwAU5LOHVimPj1MPx56QRe3OsGGWRe4dr17crV";//aeskey和OneNet第三方平台配置里的token一致

    private static Logger logger = LoggerFactory.getLogger(ReceiverController.class);

    @Autowired
    private IDeviceStatusService iDeviceStatusService;

    @Autowired
    private IAllDeviceStatusService iAllDeviceStatusService;

    @RequestMapping(value = "receive.do",method = RequestMethod.POST)
    @ResponseBody
    public String receive( @RequestBody Object body) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {


        logger.info("data receive:  body String --- " +body);
        /************************************************
         *  解析数据推送请求，非加密模式。
         *  如果是明文模式使用以下代码
         **************************************************/
        /*************明文模式  start****************/
        Util.BodyObj obj = Util.resolveBody(body, false);
        logger.info("data receive:  body Object --- " +obj);
        if (obj != null){
            boolean dataRight = Util.checkSignature(obj, token);
            // TODO: 2018-07-31 签名验证
//            if (dataRight){
            if (true){
                //存到mysql
                Object msg = obj.getMsg();
                iAllDeviceStatusService.addAllDeviceStatus(msg);
                iDeviceStatusService.updateDeviceStatus(msg);

                logger.info("data receive: content" + obj.toString());
            }else {
                logger.info("data receive: signature error");
            }

        }else {
            logger.info("data receive: body empty error");
        }
        /*************明文模式  end****************/


        /********************************************************
         *  解析数据推送请求，加密模式
         *
         *  如果是加密模式使用以下代码
         ********************************************************/
        /*************加密模式  start****************/
//        Util.BodyObj obj1 = Util.resolveBody(body, true);
//        logger.info("data receive:  body Object--- " +obj1);
//        if (obj1 != null){
//            boolean dataRight1 = Util.checkSignature(obj1, token);
//            if (dataRight1){
//                String msg = Util.decryptMsg(obj1, aeskey);
//                logger.info("data receive: content" + msg);
//            }else {
//                logger.info("data receive:  signature error " );
//            }
//        }else {
//            logger.info("data receive: body empty error" );
//        }
        /*************加密模式  end****************/
        return "ok";
    }

    @RequestMapping(value = "receive.do", method = RequestMethod.GET)
    @ResponseBody
    public String check(@RequestParam(value = "msg") String msg,
                        @RequestParam(value = "nonce") String nonce,
                        @RequestParam(value = "signature") String signature) throws UnsupportedEncodingException {

        logger.info("url&token check: msg:{} nonce{} signature:{}",msg,nonce,signature);
//        if (Util.checkToken(msg,nonce,signature,token)){
        if(true){
            return msg;
        }else {
            return "error";
        }

    }


}

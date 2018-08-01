package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by huangzhigang on 18-5-28.
 */
@Controller
@RequestMapping(value = "/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;
  
    @RequestMapping(value = "save.do")
    @ResponseBody
    public ServiceResponse addProduct(HttpSession session, Product product){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"you need login");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        }
        return ServiceResponse.creatByErrorMessage("no right to add product");
    }

    @RequestMapping(value = "set_sale_status.do")
    @ResponseBody
    public ServiceResponse setSaleStatus(HttpSession session,int productId,int status){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"you need login");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.setSaleStatus(productId,status);
        }
        return ServiceResponse.creatByErrorMessage("no right to set sale status");
    }


    @RequestMapping(value = "get_product_detail.do")
    @ResponseBody
    public ServiceResponse getProductdetail(HttpSession session, int productId){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"you need login");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.manageProductDetail(productId);
        }
        return ServiceResponse.creatByErrorMessage("no right to set sale status");
    }

    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServiceResponse getProductList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSizae",defaultValue = "10") int pageSize){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"you need login");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.manageProductList(pageNum,pageSize);
        }
        return ServiceResponse.creatByErrorMessage("no right to get product list");
    }

    @RequestMapping(value = "search_product.do")
    @ResponseBody
    public ServiceResponse searchProduct(HttpSession session, String productName,Integer productId,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"you need login");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }
        return ServiceResponse.creatByErrorMessage("no right to search product");
    }

    @RequestMapping(value = "upload.do")
    @ResponseBody
    public ServiceResponse upload(HttpSession session,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"you need login");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);

            return ServiceResponse.creatBySuccess(fileMap);
        }
        return ServiceResponse.creatByErrorMessage("no right to upload file");
    }

    @RequestMapping(value = "richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg","please login as admin");
            return resultMap;
        }
        //richtext has request on response value,we use simditor,so we should response as simditor require
        if(iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","upload error");
                return resultMap;
            }

            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            resultMap.put("success",true);
            resultMap.put("msg","upload successfully");
            resultMap.put("file_path",url);

            response.addHeader("Access-Control-Allow-Headers","X-File-Name");

            return resultMap;
        }
        resultMap.put("success",false);
        resultMap.put("msg","you are not an admin");
        return resultMap;
    }



}

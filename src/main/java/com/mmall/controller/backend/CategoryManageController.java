package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.NamedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhigang on 18-5-27.
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServiceResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0")int parentId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.addCategory(categoryName,parentId);
        }else{
            return ServiceResponse.creatByErrorMessage("need right to operate");
        }
    }

    @RequestMapping(value = "set_category_name.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse setCategoryName(HttpSession session,String categoryName,int categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.setCategoryName(categoryName,categoryId);
        }else{
            return ServiceResponse.creatByErrorMessage("need right to operate");
        }
    }

    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServiceResponse<List<Category>> getCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0")int categoryId){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"please login first");
        }
        return iCategoryService.getCategory(categoryId);
    }

    @RequestMapping(value = "get_deep_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<List<Integer>> getDeepCategory(HttpSession session,int categoryId){

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.selectCategoryAndChildById(categoryId);
        }else{
            return ServiceResponse.creatByErrorMessage("no right to query");
        }
    }


    


}

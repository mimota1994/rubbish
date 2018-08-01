package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;
import org.omg.CORBA.Object;

import javax.servlet.http.HttpSession;

/**
 * Created by huangzhigang on 18-5-24.
 */
public interface IUserService {

    ServiceResponse<User> login(String username, String password);

    ServiceResponse<String> register(User user);

    ServiceResponse<String> checkValid(String str,String Type);

    ServiceResponse<String> selectQuestion(String username);

    ServiceResponse<String> checkAnswer(String username,String question,String answer);

    ServiceResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServiceResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServiceResponse<User> updateInformation(User user);

    ServiceResponse<User> getInformation(Integer userId);

    ServiceResponse checkAdminRole(User user);

}

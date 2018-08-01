package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServiceResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by huangzhigang on 18-5-24.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;


    @Override
    public ServiceResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServiceResponse.creatByErrorMessage("user is not exist");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,md5Password);
        if(user == null){
            return ServiceResponse.creatByErrorMessage("password is wrong");
        }

        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServiceResponse.creatBySuccess("login success",user);
    }

    @Override
    public ServiceResponse<String> register(User user) {
        ServiceResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        validResponse=this.checkValid(user.getEmail(),Const.EMIAL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5 encryption
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);//numbers of lines that take effect

        if(resultCount == 0){
            return ServiceResponse.creatByErrorMessage("register fail");
        }
        return ServiceResponse.creatBySuccessMessage("register success");

    }

    @Override
    public ServiceResponse<String> checkValid(String str, String type) {
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(type)){
            //start to verify
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount >0){
                    return ServiceResponse.creatByErrorMessage("username is exist");
                }
            }
            if(Const.EMIAL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount >0){
                    return ServiceResponse.creatByErrorMessage("email is exist");
                }
            }
        }else{
            return ServiceResponse.creatByErrorMessage("parameter is wrong");
        }
        return ServiceResponse.creatBySuccessMessage("verify is success");
    }

    @Override
    public ServiceResponse<String> selectQuestion(String username) {

        ServiceResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //user is not exist
            return ServiceResponse.creatByErrorMessage("user is not exist");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(question)){
            return ServiceResponse.creatBySuccess(question);
        }
        return ServiceResponse.creatByErrorMessage("the question is empty");
    }


    @Override
    public ServiceResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            //the question and answer belong to this user ,and it is right
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServiceResponse.creatBySuccess(forgetToken);
        }
        return ServiceResponse.creatByErrorMessage("the answer of the question is not right");
    }

    @Override
    public ServiceResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
            return ServiceResponse.creatBySuccessMessage("parameter is wrong, token should be transferred");
        }
        ServiceResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //user is not exist
            return ServiceResponse.creatByErrorMessage("user is not exist");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(org.apache.commons.lang3.StringUtils.isBlank(token)){
            return ServiceResponse.creatByErrorMessage("token is invalid or out of expire");
        }

        if(org.apache.commons.lang3.StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);

            if(rowCount>0){
                return ServiceResponse.creatBySuccessMessage("modify the password successfully");
            }
        }else{
            return ServiceResponse.creatByErrorMessage("token is not right,please get the token of reset the password again");
        }
        return ServiceResponse.creatByErrorMessage("reset password error");
    }

    @Override
    public ServiceResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        //to avoid horizontal override,we should verify the old password of this user
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServiceResponse.creatByErrorMessage("the old password is wrong");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServiceResponse.creatBySuccessMessage("reset password successfully");
        }
        return ServiceResponse.creatByErrorMessage("reset password error");


    }

    @Override
    public ServiceResponse<User> updateInformation(User user) {
        //username can not be updated
        //email should be verify whether new email is exist and if the email is exist, it should not belongs to the current user
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount >0){
            return ServiceResponse.creatByErrorMessage("email is exist,please replace the email");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount >0){
            return ServiceResponse.creatBySuccess("update personal information successfully",updateUser);
        }
        return ServiceResponse.creatByErrorMessage("update personal information error");
    }

    @Override
    public ServiceResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServiceResponse.creatByErrorMessage("can not find current user");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServiceResponse.creatBySuccess(user);
    }

    /**
     * check user is an admin or not
     * @param user
     * @return
     */
    @Override
    public ServiceResponse checkAdminRole(User user) {
        if(user != null && user.getRole() == Const.Role.ROLE_ADMIN){
            return  ServiceResponse.creatBySuccess();
        }
        return ServiceResponse.creatByError();
    }

}

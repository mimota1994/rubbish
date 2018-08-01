package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by huangzhigang on 18-5-24.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//ensure when json serial ,if the object is null ,key will disappear
public class ServiceResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServiceResponse(int status){
        this.status = status;
    }
    private ServiceResponse(int status,T data){
        this.status = status;
        this.data=data;
    }
    private ServiceResponse(int status,T data,String msg){
        this.status = status;
        this.data=data;
        this.msg=msg;
    }
    private ServiceResponse(int status,String msg){
        this.status = status;
        this.msg=msg;
    }

    @JsonIgnore
    //let it get out of json serialize result
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }
    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }

    public static <T> ServiceResponse<T> creatBySuccess(){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> creatBySuccessMessage(String msg){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServiceResponse<T> creatBySuccess(T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServiceResponse<T> creatBySuccess(String msg,T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),data,msg);
    }

    public static <T> ServiceResponse<T> creatByError(){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServiceResponse<T> creatByErrorMessage(String errorMessage){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ServiceResponse<T> creatByErrorCodeMessage(int errorCode ,String errorMessage){
        return new ServiceResponse<T>(errorCode,errorMessage);
    }


}

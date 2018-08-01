package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServiceResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhigang on 18-6-7.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServiceResponse add(Integer userId,Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if(rowCount >0){
            Map result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return ServiceResponse.creatBySuccess("create address successfully",result);
        }
        return ServiceResponse.creatByErrorMessage("create address error");
    }

    public ServiceResponse del(Integer userId,Integer shippingId){
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
        if(resultCount >0){
            return ServiceResponse.creatBySuccess("delete address successfully");
        }
        return ServiceResponse.creatByErrorMessage("delete address error");
    }

    public ServiceResponse update(Integer userId,Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount > 0) {
            return ServiceResponse.creatBySuccessMessage("update address successfully");
        }
        return ServiceResponse.creatByErrorMessage("update address error");
    }

    public ServiceResponse select(Integer userId,Integer shippingId){
        Shipping shipping = shippingMapper.selectByIdAndUserId(shippingId,userId);
        if(shipping != null){
            return ServiceResponse.creatBySuccess(shipping);
        }
        return ServiceResponse.creatByErrorMessage("you have no such shipping");
    }

    public ServiceResponse list(Integer userId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageResult = new PageInfo(shippingList);
        return ServiceResponse.creatBySuccess(pageResult);
    }


}

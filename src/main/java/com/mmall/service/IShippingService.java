package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Shipping;

/**
 * Created by huangzhigang on 18-6-7.
 */
public interface IShippingService {

    ServiceResponse add(Integer userId,Shipping shipping);

    ServiceResponse del(Integer userId,Integer shippingId);

    ServiceResponse update(Integer userId,Shipping shipping);

    ServiceResponse select(Integer userId,Integer shippingId);

    ServiceResponse list(Integer userId,Integer pageNum,Integer pageSize);
}

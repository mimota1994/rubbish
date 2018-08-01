package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.vo.CartVo;

/**
 * Created by huangzhigang on 18-6-5.
 */
public interface ICartService {

    ServiceResponse<CartVo> add();

    ServiceResponse<CartVo> list(Integer userId);

    ServiceResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    ServiceResponse<CartVo> deleteProduct(Integer userId, Integer productId);

    ServiceResponse<CartVo> select(Integer userId,Integer productId);

    ServiceResponse<CartVo> unSelect(Integer userId,Integer productId);

    ServiceResponse<Integer> getCartProductCount(Integer userId);

    ServiceResponse<CartVo> selectAll(Integer userId);

    ServiceResponse<CartVo> unSelectAll(Integer userId);
}

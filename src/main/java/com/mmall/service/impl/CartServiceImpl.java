package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.DeviceStatusMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.DeviceStatus;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huangzhigang on 18-6-5.
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DeviceStatusMapper deviceMapper;

    public ServiceResponse<CartVo> add() {

        DeviceStatus device = deviceMapper.selectByPrimaryKey(100);



        return ServiceResponse.creatBySuccess();
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");
        for(Cart cartItem:cartList){
            CartProductVo cartProductVo = new CartProductVo();
            cartProductVo.setId(cartItem.getId());
            cartProductVo.setUserId(userId);
            cartProductVo.setProductId(cartItem.getProductId());

            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            if(product != null){
                cartProductVo.setProductMainImage(product.getMainImage());
                cartProductVo.setProductName(product.getName());
                cartProductVo.setProductSubtitle(product.getSubtitle());
                cartProductVo.setProductStatus(product.getStatus());
                cartProductVo.setProductPrice(product.getPrice());
                cartProductVo.setProductStock(product.getStock());
                //judge stock
                int buyLimitCount = 0;
                if(product.getStock() >= cartItem.getQuantity()){
                    buyLimitCount = cartItem.getQuantity();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                }else{
                    buyLimitCount = product.getStock();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                    //update effective cart quantity
                    Cart cartForQuantity = new Cart();
                    cartForQuantity.setId(cartItem.getId());
                    cartForQuantity.setQuantity(buyLimitCount);
                    cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                }
                cartProductVo.setQuantity(buyLimitCount);
                //compute the total price
                cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                cartProductVo.setProductChecked(cartItem.getChecked());

            }
            //// TODO: 18-6-6 if product is not exist
            if(cartItem.getChecked() == Const.Cart.CHECKED){
                cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),BigDecimalUtil.mul(cartProductVo.getProductPrice().doubleValue(),cartProductVo.getQuantity().doubleValue()).doubleValue());
            }
            cartProductVoList.add(cartProductVo);
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }

    public ServiceResponse<CartVo> list(Integer userId){
        if(userId == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServiceResponse.creatBySuccess(cartVo);
    }

    public ServiceResponse<CartVo> update(Integer userId,Integer productId,Integer count){
        if(productId == null || count ==null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        cart.setQuantity(count);
        cartMapper.updateByPrimaryKey(cart);
        CartVo cartVo = getCartVoLimit(userId);
        return ServiceResponse.creatBySuccess(cartVo);
    }

    public ServiceResponse<CartVo> deleteProduct(Integer userId, Integer productId){
        if(productId == null){
            ServiceResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        cartMapper.deleteByPrimaryKey(cart.getId());
        return ServiceResponse.creatBySuccess(getCartVoLimit(userId));
    }

    public ServiceResponse<CartVo> select(Integer userId,Integer productId){
        if(productId == null){
            ServiceResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        cart.setChecked(Const.Cart.CHECKED);
        cartMapper.updateByPrimaryKey(cart);
        return ServiceResponse.creatBySuccess(getCartVoLimit(userId));
    }

    public ServiceResponse<CartVo> unSelect(Integer userId,Integer productId){
        if(productId == null){
            ServiceResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        cart.setChecked(Const.Cart.UN_CHECKED);
        cartMapper.updateByPrimaryKey(cart);
        return ServiceResponse.creatBySuccess(getCartVoLimit(userId));
    }

    public ServiceResponse<Integer> getCartProductCount(Integer userId){
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        if(cartList == null){
            return ServiceResponse.creatByErrorCodeMessage(10,"error");
        }
        return ServiceResponse.creatBySuccess(cartList.size());
    }

    public ServiceResponse<CartVo> selectAll(Integer userId){
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        for(Cart cartItem:cartList){
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.updateByPrimaryKey(cartItem);
        }
        CartVo cartVo = getCartVoLimit(userId);
        return ServiceResponse.creatBySuccess(cartVo);
    }

    public ServiceResponse<CartVo> unSelectAll(Integer userId){
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        for(Cart cartItem:cartList){
            cartItem.setChecked(Const.Cart.UN_CHECKED);
            cartMapper.updateByPrimaryKey(cartItem);
        }
        CartVo cartVo = getCartVoLimit(userId);
        return ServiceResponse.creatBySuccess(cartVo);
    }

}


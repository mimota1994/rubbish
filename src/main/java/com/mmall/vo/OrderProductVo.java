package com.mmall.vo;

import com.mmall.pojo.OrderItem;
import com.mmall.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huangzhigang on 18-6-7.
 */
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHost;
}

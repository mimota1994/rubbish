package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * Created by huangzhigang on 18-5-28.
 */
public interface IProductService {

    ServiceResponse saveOrUpdateProduct(Product product);

    ServiceResponse setSaleStatus(Integer productId,Integer status);

    ServiceResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServiceResponse manageProductList(Integer pageNum,Integer pageSize);

    ServiceResponse searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    ServiceResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServiceResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,Integer pageNum,Integer pageSize,String orderBy);

}

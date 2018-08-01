package com.mmall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangzhigang on 18-5-28.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    @Override
    public ServiceResponse<PageInfo> saveOrUpdateProduct(Product product) {
        if(product != null) {
            //set the first one of sub image as main image
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }

            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount >0) {
                    return ServiceResponse.creatBySuccessMessage("update product successfully");
                }
                return ServiceResponse.creatByErrorMessage("update product error");
            }else{
                int resultCount = productMapper.insert(product);
                if (resultCount > 0) {
                    return ServiceResponse.creatBySuccessMessage("add product successfully");
                }
                return ServiceResponse.creatByErrorMessage("add product error");
            }
        }
        return ServiceResponse.creatByErrorMessage("parameter is error");
    }

    @Override
    public ServiceResponse setSaleStatus(Integer productId, Integer status) {
        if(productId == null || status == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"parameter error");
        }
        int rowCount = productMapper.updateStatusByProductId(productId,status);
        if(rowCount >0){
            return ServiceResponse.creatBySuccessMessage("set sale status successfully");
        }
        return ServiceResponse.creatByErrorMessage("set sale status error");
    }

    @Override
    public ServiceResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if(productId == null){
            return ServiceResponse.creatByErrorMessage("parameter error");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServiceResponse.creatByErrorMessage("no product");
        }
        ProductDetailVo productDetailVo = assembleProductDetail(product);
        return ServiceResponse.creatBySuccess(productDetailVo);
    }

    @Override
    public ServiceResponse<PageInfo> manageProductList(Integer pageNum, Integer pageSize) {
        //startPage--start
        PageHelper.startPage(pageNum,pageSize);
        //sql query logic
        List<Product> productList = productMapper.selectList();

        List<ProductListVo> productListVoList = new ArrayList<>();
        for(Product product:productList){
            productListVoList.add(assembleProductList(product));
        }
        //pageHelper --close
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServiceResponse.creatBySuccess(pageResult);
    }

    @Override
    public ServiceResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }

        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);

        List<ProductListVo> productListVoList = new ArrayList<>();
        for(Product product:productList){
            productListVoList.add(assembleProductList(product));
        }

        //pageHelper --close
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServiceResponse.creatBySuccess(pageResult);

    }

    @Override
    public ServiceResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if(productId == null){
            return ServiceResponse.creatByErrorMessage("parameter error");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServiceResponse.creatByErrorMessage("no product");
        }
        if(product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServiceResponse.creatByErrorMessage("product is not online or delete");
        }
        ProductDetailVo productDetailVo = assembleProductDetail(product);
        return ServiceResponse.creatBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetail(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        //imageHost

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//root node
        }else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        //parent category id

        Date creatTime = product.getCreateTime();
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(creatTime));
        //create date time

        Date updateTime = product.getUpdateTime();
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(updateTime));
        //update time

        return productDetailVo;
    }

    private ProductListVo assembleProductList(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setPrice(product.getPrice());
        productListVo.setName(product.getName());
        productListVo.setStatus(product.getStatus());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        return productListVo;
    }

    public ServiceResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,Integer pageNum,Integer pageSize,String orderBy){
        if(StringUtils.isBlank(keyword) && categoryId == null){
            return ServiceResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();

        if(categoryId !=null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){
                //no such category and no keyword,so we return a empty result set ,and do not return error
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServiceResponse.creatBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildById(category.getId()).getData();

        }
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        //order parse
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+""+orderByArray[1]);
            }
        }
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product:productList){
            ProductListVo productListVo = assembleProductList(product);
            productListVoList.add(productListVo);
        }

        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServiceResponse.creatBySuccess(pageInfo);
    }
}

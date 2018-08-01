package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServiceResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangzhigang on 18-6-3.
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "detail.do")
    @ResponseBody
    public ServiceResponse<ProductDetailVo> detail(int productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServiceResponse<PageInfo> list(@RequestParam(value = "keyword",required = false) String keyword, @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                          @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                          @RequestParam(value = "orderBy",defaultValue = "") String orderBy){

        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }
}

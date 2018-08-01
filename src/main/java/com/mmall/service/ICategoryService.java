package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by huangzhigang on 18-5-27.
 */
public interface ICategoryService {

    ServiceResponse addCategory(String categoryName,Integer parentId);

    ServiceResponse setCategoryName(String categoryName,Integer categoryId);

    ServiceResponse<List<Category>> getCategory(int categoryId);

    ServiceResponse<List<Integer>> selectCategoryAndChildById(Integer categoryId);
}

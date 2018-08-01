package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by huangzhigang on 18-5-25.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMIAL = "email";
    public static final String USERNAME = "username";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{
        int CHECKED = 1;//cart is choosed
        int UN_CHECKED = 0;

        int EMPTY_CART =0;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface Role{
        int ROLE_CUSTOMER = 0; //common user
        int ROLE_ADMIN = 1; //admin user
    }

    public enum ProductStatusEnum{
        ON_SALE(1,"online");

        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;

        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

            }
}

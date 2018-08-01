package com.mmall.test;

import com.mmall.util.Util;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class UtilTest {
    public static void main(String[] args) throws Exception {
        HashMap body =new HashMap();
        body.put("msg","ok");

        Util.BodyObj obj = Util.resolveBody(body, false);
    }
}

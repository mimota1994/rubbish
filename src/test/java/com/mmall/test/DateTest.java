package com.mmall.test;

import com.mmall.util.DateTimeUtil;
import org.joda.time.DateTime;

import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        Date a= new Date();
        System.out.println(a);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date b = new Date();

        System.out.println(a.getTime());
        System.out.println(b.getTime()-a.getTime());
    }
}

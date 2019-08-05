package com.example.demo.util;

/**
 * @program: demo
 * @description:
 * @author: xiaoye
 * @create: 2019-08-05 17:11
 **/
public class StringUtils {

    public static String lowerFirst(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}

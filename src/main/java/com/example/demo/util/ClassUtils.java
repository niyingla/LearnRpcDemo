package com.example.demo.util;

import com.example.demo.rpc.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author pikaqiu
 */
@Slf4j
public class ClassUtils {
    /**
     * 获取参数类型
     * @param param
     * @return
     */
    public static Class[] getClassType(Object[] param) {
        Class[] classType = null;
        if (param != null && param.length > 0) {
            classType = new Class[param.length];
            for (int i = 0; i < param.length; i++) {
                Object o = param[i];
                classType[i] = o.getClass();
            }
        }
        return classType;
    }

}

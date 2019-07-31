package com.example.demo.util;

import com.example.demo.dto.CompareDto;
import com.example.demo.rpc.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author pikaqiu
 */
public class ClassUtils {
    /**
     * 获取参数类型
     * @param param
     * @return
     */
    public static Class [] getClassType(Object[] param) {
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

    /**
     * 获取接口代理
     * @param intefaceClass
     * @param <T>
     * @return
     */
    public static  <T> T getInterfaceInfo(Class<T> intefaceClass) {

        Class[] interfaceClassArray = new Class[]{intefaceClass};

        T server = (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),interfaceClassArray , new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //判断是否是接口自定义方法
                Method[] declaredMethods = intefaceClass.getDeclaredMethods();
                if (Arrays.asList(declaredMethods).indexOf(method) < 0) {
                    return null;
                 }
                return RpcClient.sendRpcRequest(method.getDeclaringClass().getPackage().getName(),intefaceClass, method.getName(), args);
            }
        });
        return server;
    }
}

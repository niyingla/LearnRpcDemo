package com.example.demo.rpc;

import com.example.demo.dto.CompareDto;
import com.example.demo.util.ClassUtils;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author pikaqiu
 */
@Component

public class FreamWork {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 测试方法
     * @param compareDto
     * @return
     */
    public CompareDto testRpc(CompareDto compareDto) {
        compareDto.setType(compareDto.getType() + "3333");
        return compareDto;
    }

    /**
     *  通过反射调用目标方法
     * @param classPathStr
     * @param methodStr
     * @param param
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object methodInvoke(String classPathStr,String methodStr,Object ... param) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        //获取类实例
        Class clazz = loader.loadClass(classPathStr);
        Object contextBean = applicationContext.getBean(clazz);
        //获取参数类型
        Class[] classType = ClassUtils.getClassType(param);
        Method method = clazz.getMethod(methodStr, classType);
        //反射执行方法
        Object invokeResult = method.invoke(contextBean, param);
        return invokeResult;

    }

}

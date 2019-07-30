package com.example.demo.rpc;

import com.example.demo.dto.CompareDto;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author tangsg
 */
@Service
public class FreamWork {

    @Autowired
    private ApplicationContext applicationContext;

    public CompareDto testRpc(CompareDto compareDto) {
        compareDto.setType(compareDto.getType() + "3333");
        return compareDto;
    }

    /**
     *  通过反射调用目标方法
     * @param classPath
     * @param methodStr
     * @param param
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object methodInvoke(String classPath,String methodStr,Object ... param) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        //获取类实例
        Class clazz = loader.loadClass(classPath);
        Object contextBean = applicationContext.getBean(clazz);
        //获取参数类型
        Class[] classType = getClassType(param);
        Method method = clazz.getMethod(methodStr, classType);
        //反射执行方法
        Object invokeResult = method.invoke(contextBean, param);
        return invokeResult;

    }

    /**
     * 获取参数类型
     * @param param
     * @return
     */
    public Class [] getClassType(Object[] param) {
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
     * 将对象转成字节
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] getObjectByte(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(byteArrayOutputStream);
        os.writeObject(object);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        os.flush();
        os.close();
        byteArrayOutputStream.close();
        return bytes;
    }


    /**
     * 将字节转成对象
     * @param bytes
     * @return
     * @throws IOException
     */
    public static Object getObjectByByte(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream os = new ObjectInputStream(byteInputStream);
        Object readObject = os.readObject();
        byteInputStream.close();
        os.close();
        return readObject;
    }

}

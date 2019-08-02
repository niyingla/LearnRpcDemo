package com.example.demo.com;

import com.example.demo.rpc.RpcClient;
import com.example.demo.util.Addresser;
import com.example.demo.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author tangsg
 */
@Slf4j
@Component
public class ProxyFactory {
    /**
     * 获取接口代理
     * @param intefaceClass
     * @param <T>
     * @return
     */
    public  <T> T getInterfaceInfo(Class<T> intefaceClass) {

        Class[] interfaceClassArray = new Class[]{intefaceClass};

        T server = (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),interfaceClassArray , new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //判断是否是接口自定义方法
                Method[] declaredMethods = intefaceClass.getDeclaredMethods();
                if (Arrays.asList(declaredMethods).indexOf(method) < 0) {
                    return method.invoke(proxy,args);
                }
                return RpcClient.sendRpcRequest(method.getDeclaringClass().getPackage().getName(),intefaceClass, method.getName(), args);
            }

            @Override
            public String toString(){
                Long address = null;
                try {
                    address = Addresser.addressOf(ProxyFactory.this);
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("获取内存地址错误");
                }
                return "$proxy"+intefaceClass.getClass().getSimpleName()+"@"+address;
            }
        });
        return server;
    }
}

package com.example.demo.com;

import com.example.demo.rpc.RpcClient;
import com.example.demo.util.Addresser;
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
                    //todo 后期优化
                    switch (method.getName()){
                        case "toString":
                            return toString();
                        case "equals":
                            return equals(args);
                    }
                }
                return RpcClient.sendRpcRequest(method.getDeclaringClass().getPackage().getName(),intefaceClass, method.getName(), args);
            }

            @Override
            public boolean equals(Object obj) {
                return toString().equals(obj.toString());
            }

            @Override
            public String toString() {
                return "proxy$"+intefaceClass.getName() + "@" + Integer.toHexString(hashCode());
            }

            @Override
            public int hashCode() {
                Long address = null;
                try {
                     address = Addresser.addressOf(this);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return address.intValue();
            }

        });
        return server;
    }
}

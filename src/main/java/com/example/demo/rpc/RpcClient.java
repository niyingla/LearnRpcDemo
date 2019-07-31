package com.example.demo.rpc;

import com.example.demo.dto.CompareDto;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.ClassUtils;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 远程调用服务客户端类
 *
 * @author pikaqiu
 */
@Component
public class RpcClient {


    public static Object sendRpcRequest(String classPath,String method,Object[] args){
    //todo 发送远程请求
    String param = "----包路径是: " + classPath + "----方法名是：" + method + "----参数是：" + args;
    System.out.println(param);

    //模拟返回
    CompareDto compareDto = new CompareDto();
    compareDto.setType(param);
    return compareDto;}
}


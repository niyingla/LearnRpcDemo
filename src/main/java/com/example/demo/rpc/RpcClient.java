package com.example.demo.rpc;

import com.example.demo.annotation.RpcServerCase;
import com.example.demo.dto.CompareDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 远程调用服务客户端类
 *
 * @author pikaqiu
 */
@Slf4j
@Component
public class RpcClient {


    public static Object sendRpcRequest(String classPath,Class intefaceClass,String method,Object[] args){
        //参数对象转换成能字节  远程调用
        RpcServerCase rpcServerCase = (RpcServerCase) intefaceClass.getAnnotation(RpcServerCase.class);
        if(rpcServerCase != null){
           log.info(rpcServerCase.serverName());
        }
        //todo 发送远程请求
        log.info("发送远程请求，请求类：{} 方法：{} 参数：{}",classPath,method,args);
        //模拟返回
        CompareDto compareDto = new CompareDto();
        compareDto.setType("22222");
        return compareDto;
    }


}


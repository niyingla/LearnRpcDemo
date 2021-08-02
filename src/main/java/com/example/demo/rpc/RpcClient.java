package com.example.demo.rpc;

import com.example.demo.annotation.RpcServerCase;
import com.example.demo.dto.RpcRequestDto;
import com.example.demo.util.ChannelUtils;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 远程调用服务客户端类
 *
 * @author pikaqiu
 */
@Slf4j
@Component
public class RpcClient {

    @Autowired
    private RpcServerPool _rpcServerPool;

    private static RpcServerPool rpcServerPool;

    @PostConstruct
    public void init() {
        RpcClient.rpcServerPool = _rpcServerPool;
    }

    public static Object sendRpcRequest(String classPath, Class interfaceClass, String method, Object[] args) {
        //参数对象转换成能字节  远程调用
        RpcServerCase rpcServerCase = (RpcServerCase) interfaceClass.getAnnotation(RpcServerCase.class);
        if (rpcServerCase != null) {
            log.info("发起远程请求 请求目标服务：{}",rpcServerCase.serverName());
        }
        RpcRequestDto rpcRequestDto = new RpcRequestDto(System.currentTimeMillis() + "", classPath, method, args);
        ChannelFuture channel = rpcServerPool.getChannelByServerName(rpcServerCase.serverName());

        return ChannelUtils.sendChannelRpcRequest(channel, rpcRequestDto);
    }

}


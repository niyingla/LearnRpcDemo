package com.example.demo.rpc;

import com.example.demo.dto.RpcServerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pikaqiu
 */
@Component
@Slf4j
public class RpcServerPool {

    private final Map<String,RpcServerDto> serverDtoMap = new HashMap<>();

    // todo channelMap

    /**
     * 初始化所有连接
     */
    @PostConstruct
    public void initAllConnect(){

        RpcServerBuild rpcServerPoolBuild = new RpcServerBuild();
        rpcServerPoolBuild.serverAdd("user", "127.0.0.1", "7001")
                .serverAdd("user", "127.0.0.1", "7002");

        for (String serverName : serverDtoMap.keySet()) {
            RpcServerDto rpcServerDto = serverDtoMap.get(serverName);
            for (RpcServerDto.Example example : rpcServerDto.getExamples()) {
                //todo 循环创建连接
                log.info("创建连接 服务: {}：ip: {} ,port: {}", serverName, example.getPort());
                //保存连接
            }
        }

    }

    /**
     * 获取一个连接
     *
     * @return
     */
    public Object getChannelByServerName(String serverName) {

        return null;
    }

    public class RpcServerBuild{

          public RpcServerBuild serverAdd(String serverName,String ip,String port){

              RpcServerDto serverDto = serverDtoMap.get(serverName);

              if (serverDto == null) {
                  serverDto = new RpcServerDto(serverName);
              }
              serverDto.addExample(ip,port);
              serverDtoMap.put(serverName, serverDto);

              return this;
          }

    }

}

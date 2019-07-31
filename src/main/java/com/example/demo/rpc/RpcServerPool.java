package com.example.demo.rpc;

import com.example.demo.dto.RpcServerDto;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author tangsg
 */
@Component
public class RpcServerPool {

    private List<RpcServerDto> serverDtoList;

    /**
     * 初始化所有连接
     */

    @PostConstruct
    public void initAllConnect(){

    }

    /**
     * 获取一个连接
     *
     * @return
     */
    public Object getChannelByServerName(String serverName) {

        return null;
    }
}

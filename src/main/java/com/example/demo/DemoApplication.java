package com.example.demo;

import com.example.demo.netty.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args)throws Exception {
        SpringApplication.run(DemoApplication.class, args);
        NettyClient nettyServer = new NettyClient();
        nettyServer.initClient();
    }



}

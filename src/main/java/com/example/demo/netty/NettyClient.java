package com.example.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pikaqiu
 */
public class NettyClient {

    private Bootstrap b = new Bootstrap();
    private EventLoopGroup group = new NioEventLoopGroup();
    private List<ChannelFuture> channelFutures = new ArrayList<>();


    /**
     * 初始化客户端
     * @return
     */
    public NettyClient initClient() {
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        sc.pipeline().addLast(new ClienHeartBeattHandler());
                    }
                });
        return this;
    }


    /**
     * 创建连接池内连接
     * @param count
     * @param ip
     * @param port
     * @return
     */
    public NettyClient createConnect(int count, String ip, int port)  {
        for (int i = 0; i < count; i++) {
            Runnable runnable = () -> {
                try {
                    ChannelFuture cf = b.connect(ip, port);
                    channelFutures.add(cf);
                    cf.sync();
                    cf.channel().closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(runnable).start();

        }
        return this;

    }

    /**
     * 随机获取一个连接
     * @return
     */
    public ChannelFuture getChannelFuture() {
        return channelFutures.get((int) (Math.random() * (channelFutures.size())));
    }

    /**
     * 关闭连接
     */
    public void close() {
        group.shutdownGracefully();
    }

}

package com.example.demo.netty;

import com.example.demo.dto.RpcRequestDto;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author xiaoye
 */
public class ClienHeartBeattHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof RpcRequestDto) {
            RpcRequestDto rpcRequestDto = (RpcRequestDto) msg;
            FutureResult.putResult(rpcRequestDto.getRequestId(), rpcRequestDto.getResult());
        }
    }
}

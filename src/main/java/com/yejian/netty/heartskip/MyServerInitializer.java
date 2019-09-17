package com.yejian.netty.heartskip;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyServerInitializer
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/9/17 17:20
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline= ch.pipeline();

        pipeline.addLast(new IdleStateHandler(
                5,7,3, TimeUnit.SECONDS));//空闲检测的handler

        pipeline.addLast(new MyServerHandler());
    }
}

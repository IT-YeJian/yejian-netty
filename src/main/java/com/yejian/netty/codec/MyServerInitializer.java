package com.yejian.netty.codec;

import com.yejian.netty.socket.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @ClassName MyServerInitializer
 * @Description 一旦建立连接initChannel就会被调用
 * @Author jian.ye
 * @Date 2019/9/17 11:14
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyServerHandler());

    }
}

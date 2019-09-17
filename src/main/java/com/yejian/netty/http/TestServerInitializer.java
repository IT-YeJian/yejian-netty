package com.yejian.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName TestServerInitializer
 * @Description 初始化器
 * @Author jian.ye
 * @Date 2019/9/16 20:07
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    //ChannelPipeline 就是一个管道 ，一个管道可以有多个channelHandler 相当于一个拦截器
    //初始化管道
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());


    }
}

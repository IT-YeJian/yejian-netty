package com.yejian.netty.heartskip;

import com.yejian.netty.http.TestHttpServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName MyServer
 * @Description 读写检查
 * @Author jian.ye
 * @Date 2019/9/17 17:18
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {

        //事件循环组 这里面就是一个死循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //boss负责获取连接 并将连接的处理交给work处理
        //如果只有一个线程组 也能 完成一边接受 一边处理 但不推荐
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //一个 启动服务端的类
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            /**
             * NioServerSocketChannel 用到了这个管道 childHandler为我们自己的处理请求的处理器 {@link TestHttpServerHandler}
             */
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//针对 boos来说的
                    .childHandler(new MyServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}

package com.yejian.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName TestServer
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/9/16 19:58
 */
public class TestServer {

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
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}

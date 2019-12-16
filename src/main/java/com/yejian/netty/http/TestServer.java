package com.yejian.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 这是一个http程序
 * @ClassName TestServer
 * @Description Channel本身可以看做一个连接
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
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)//.handler(LoggingHandler) 这里定义的话和boss那个关联做处理的 比如LoggingHandler
                    .childHandler(new TestServerInitializer());//childHandler 是丢给work做处理的
            /**
             * 调用sync才是真正确保bind这个操作是完成了的
             */
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            //获取到 channelFuture，channel调用closeFuture 关闭的话， 表示服务器端要等待关闭之后
            //流程才能往下走,因此调用sync 确保操作完成
            //一般来说不显示调用closeChannel的话 程序就停在这儿了
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}

package com.yejian.netty.my.chapter02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName EchoServer
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/27 19:39
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)throws Exception {
        if (args.length != 1) {
            System.err.println("Usage : " + EchoServer.class.getSimpleName() + " <port>");
        }
        int port =8899; //Integer.parseInt(args[0]);
        new EchoServer(port).start(); //调用服务器的start() 方法
    }

    public void start()throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //创建事件循环组 实际是线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //2创建ServerBootStrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class) //指定所使用的NIO传输Channel
                    .localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>() { //添加一个EchoServerHandler到子Channel的ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(serverHandler);//EchoServerHandler标注为@Shareable 所以我们可以总是使用同样的实例
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind().sync();//异步地绑定服务器 调用sync方法阻塞等待直到绑定完成
            channelFuture.channel().closeFuture().sync();//获取channel的closeFuture 并且阻塞当前线程直到它完成

        } finally {
            System.out.println("发生error");
            group.shutdownGracefully().sync();//关闭EventLoopGroup释放所有的资源
        }
    }
}

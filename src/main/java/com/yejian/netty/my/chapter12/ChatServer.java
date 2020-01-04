package com.yejian.netty.my.chapter12;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * @ClassName ChatServer
 * @Description 引导服务器
 * @Author jian.ye
 * @Date 2020/1/3 14:58
 */
public class ChatServer {

    //创建 DefaultChannelGroup，其将保存所有已经连接的 WebSocket Channel
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    private final EventLoopGroup group = new NioEventLoopGroup();

    private Channel channel;

    public static void main(String[] args)throws Exception {
        int port = 8899;
        final ChatServer endpoint = new ChatServer();
        ChannelFuture future = endpoint.start(
                new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }

    public ChannelFuture start(InetSocketAddress address) {
        //引导服务器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup));
        ChannelFuture channelFuture = serverBootstrap.bind(address);
        channelFuture.syncUninterruptibly();
         channel = channelFuture.channel();
        return channelFuture;
    }

    //创建 ChatServerInitializer
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup channelGroup) {
        return new ChatServerInitializer(channelGroup);
    }
    //处理服务器关闭，并释放所有的资源
    void destroy(){
        if (channel != null) {
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }


}

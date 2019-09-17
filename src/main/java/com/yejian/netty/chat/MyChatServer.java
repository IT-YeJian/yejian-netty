package com.yejian.netty.chat;

import com.yejian.netty.socket.MyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName MyChatServer
 * @Description A B C 分别和服务器建立连接
 * 若 第一个客户和服务端建立好连接后
 * 紧接着第二个和服务端建立连接
 * 那么这个时候 对于之前已经建立好的连接
 * 打印出 谁线 失去连接后谁下线
 * 同时通知其他客户端谁上线 下线
 *
 * A已上线
 * B 上线 告诉A ,B上线
 * C 上线 告诉A B, C上线
 *
 * A发消息
 * A收到自己的消息
 * B收到A的消息
 * C收到A的消息
 *
 * A下线
 * 告诉B ,A下线
 * 告诉C, A下线
 * @Author jian.ye
 * @Date 2019/9/17 14:16
 */
public class MyChatServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap  = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyChatServerInitializer());

            //sync必须加上表示 一直等待
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}

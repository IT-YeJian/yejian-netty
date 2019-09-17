package com.yejian.netty.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ClassName MyClientHandler
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/9/17 11:47
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client recieve from server msg: "+ msg);
        ctx.writeAndFlush("from client: "+ LocalDateTime.now());

    }

    /**
     * 避免双方都在等待 读取数据
     * 通道已经连接好后 让客户端向服务端发送消息
     * 触发服务的read0,并响应客户端 触发客户端的read0
     * 这样就可以了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("from client'greet");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

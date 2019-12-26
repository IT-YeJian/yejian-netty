package com.yejian.netty.stickpackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @ClassName MyClientHandler
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/9/17 11:47
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte [] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        String message = new String(bytes,Charset.forName("utf-8"));

        System.out.println("客户端接受到的消息内容 : "+message);
        System.out.println("客户端接受到的消息数量 : "+(++count));
    }

    /**
     * 避免双方都在等待 读取数据
     * 通道已经连接好后 让客户端向服务端发送消息
     * 触发服务的read0,并响应客户端 触发客户端的read0
     * 这样就可以了
     *
     *
     * 连接处于活动的状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("sent from client ", Charset.forName("utf-8"));
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

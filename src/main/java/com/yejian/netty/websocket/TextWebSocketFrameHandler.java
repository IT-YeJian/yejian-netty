package com.yejian.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @ClassName TextWebSocketFrameHandler
 * @Description 用于处理文本的数据传输
 * @Author jian.ye
 * @Date 2019/9/18 16:32
 */
public class TextWebSocketFrameHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到消息："+msg.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间 "+ LocalDateTime.now()));


    }

    /**
     * 连接建立
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler Added " +ctx.channel().id().asLongText());//每一个channel都有一个全局唯一值
    }

    /**
     * 连接关闭
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler Removed " +ctx.channel().id().asLongText());//每一个channel都有一个全局唯一值
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        cause.printStackTrace();
        ctx.close();
    }
}

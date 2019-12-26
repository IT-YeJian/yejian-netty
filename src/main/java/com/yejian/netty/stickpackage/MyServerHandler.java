package com.yejian.netty.stickpackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @ClassName MyServerHandler
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/9/17 11:22
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {


    private int count;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte [] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, Charset.forName("utf-8"));

        System.out.println("服务端接受到的消息内容: "+message);
        System.out.println("服务端接受到的消息数量"+(++count));

        ByteBuf respBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(),Charset.forName("utf-8"));
        ctx.writeAndFlush(respBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
}

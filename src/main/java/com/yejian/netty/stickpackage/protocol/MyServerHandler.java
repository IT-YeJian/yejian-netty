package com.yejian.netty.stickpackage.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @ClassName MyServerHandler
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/26 16:57
 */
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
            int length = msg.getLength();
            byte [] content = msg.getContent();

        System.out.println("服务端接收到的数据: ");
        System.out.println("服务端接收到的长度: "+length);
        System.out.println("服务端接收到的内容: "+new String(content, Charset.forName("utf-8")));

        System.out.println("服务端接收到的消息数量 "+(++count));

        String response = UUID.randomUUID().toString();
        int responseLength = response.getBytes("utf-8").length;

        byte[]responseContent = response.getBytes("utf-8");

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setContent(responseContent);
        personProtocol.setLength(responseLength);
        ctx.writeAndFlush(personProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

package com.yejian.netty.stickpackage.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName MyPersonEncoder
 * @Description 编码器
 * @Author jian.ye
 * @Date 2019/12/26 16:54
 */
public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyPersonEncoder encoder invoked");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}

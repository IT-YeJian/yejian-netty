package com.yejian.netty.stickpackage.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @ClassName MyPersonDecoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/26 16:49
 */
public class MyPersonDecoder extends ReplayingDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyPersonDecoder decode invoked");

        int length = in.readInt();
        byte [] content = new byte[length];
        in.readBytes(content);

        PersonProtocol personProtocol =new PersonProtocol();
        personProtocol.setLength(length);
        personProtocol.setContent(content);

        out.add(personProtocol);
    }
}

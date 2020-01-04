package com.yejian.netty.my.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName CharToByteEncoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 21:34
 */
public class CharToByteEncoder extends MessageToByteEncoder<Character> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Character msg, ByteBuf out) throws Exception {
        out.writeChar(msg);//将Character解码为char 并将其写入到出站ByteBuf中
    }
}

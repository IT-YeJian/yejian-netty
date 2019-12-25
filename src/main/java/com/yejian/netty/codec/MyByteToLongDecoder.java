package com.yejian.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName MyByteToLongDecoder
 * @Description 客户端与服务端都使用同一个解码器
 * @Author jian.ye
 * @Date 2019/12/20 8:59 下午
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode invoked");
        System.out.println(in.readableBytes());
        if(in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}

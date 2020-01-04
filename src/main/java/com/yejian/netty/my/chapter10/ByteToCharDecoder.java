package com.yejian.netty.my.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName ByteToCharDecoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 21:33
 */
public class ByteToCharDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes()>=2){
            out.add(in.readChar());//将一个或者多个character对象添加到传出的list中
        }
    }
}

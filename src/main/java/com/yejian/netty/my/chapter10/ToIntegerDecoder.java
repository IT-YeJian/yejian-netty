package com.yejian.netty.my.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName ToIntegerDecoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 20:00
 */
//扩展ByteToMessageDecoder类 以字节解码为特定的格式
public class ToIntegerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //检测是否至少有4字节可读
        if(in.readableBytes()>=4){
            //从入站ByteBuf中读取一个int 并将其添加到解码消息的List中
            out.add(in.readInt());
        }
    }
}

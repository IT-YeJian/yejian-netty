package com.yejian.netty.my.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @ClassName SafeByteToMessageDecoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 20:19
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
    static final int MAX_FRAME_SIZE = 1024 ;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        if(readableBytes>MAX_FRAME_SIZE){
            in.skipBytes(readableBytes);//跳过所有的可读字节 抛出异常并通知channelHandler
            throw new TooLongFrameException("Frame too big !");
        }
        // TODO: 2019/12/31
    }
}

package com.yejian.netty.my.chapter09;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @ClassName FrameChunkDecoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 16:05
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {

    private final int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize){
        this.maxFrameSize=maxFrameSize;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        if(readableBytes>maxFrameSize){
            //丢弃这个字节
            //如果该帧太大 则丢弃它并抛出一个TooLongFrameException
            in.clear();
            throw new TooLongFrameException();
        }
        //否则 从ByteBuf中读取一个新的帧
        ByteBuf buf = in.readBytes(readableBytes);
        //将该帧添加到解码 区区一个新的帧消息的list中
        out.add(buf);
    }
}

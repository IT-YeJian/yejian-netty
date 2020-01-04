package com.yejian.netty.my.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @ClassName ToIntegerDecoder2
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 20:07
 * public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder
 * 类型参数 S 指定了用于状态管理的类型，其中 Void 代表不需要状态管理
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {

    /**
     *
     * @param ctx
     * @param in 传入的 ByteBuf 是 ReplayingDecoderByteBuf
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readInt());
    }
}

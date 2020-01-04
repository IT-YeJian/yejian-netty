package com.yejian.netty.my.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @ClassName LengthBasedInitializer
 * @Description TODO
 * @Author jian.ye
 * @Date 2020/1/2 19:33
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //使用 LengthFieldBasedFrameDecoder 解码将帧长度编码到帧起始的前 8 个字节中的消息
        pipeline.addLast(new LengthFieldBasedFrameDecoder(64*1024,0,8));
        //添加 FrameHandler 以处理每个帧
        pipeline.addLast(new FrameHandler());
    }
    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        }
    }
}

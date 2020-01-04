package com.yejian.netty.my.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @ClassName LineBasedHandlerInitializer
 * @Description 处理由行尾符分隔的帧
 * @Author jian.ye
 * @Date 2020/1/2 19:09
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {




    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //该LineBasedFrame将提取的帧转发给下一个ChannelInboundHandler
        pipeline.addLast(new LineBasedFrameDecoder(64*1024));
        //添加FrameHadnler
        pipeline.addLast(new FrameHandler());
    }

    static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        }
    }
}

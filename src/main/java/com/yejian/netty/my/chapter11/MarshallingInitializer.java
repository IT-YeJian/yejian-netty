package com.yejian.netty.my.chapter11;

import io.netty.channel.*;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import java.io.Serializable;


/**
 * @ClassName MarshallingInitializer
 * @Description TODO
 * @Author jian.ye
 * @Date 2020/1/2 20:23
 */
public class MarshallingInitializer extends ChannelInitializer<Channel> {

    private final MarshallerProvider marshallerProvider;
    private final UnmarshallerProvider unmarshallerProvider;

    public MarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        //添加 MarshallingDecoder 以将 ByteBuf 转换为 POJO
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
        //添加 MarshallingEncoder 以将POJO 转换为 ByteBuf
        pipeline.addLast(new MarshallingEncoder(marshallerProvider));
        //添加 ObjectHandler，以处理普通的实现了Serializable 接口的 POJO
        pipeline.addLast(new ObjectHandler());
    }
    
    final class ObjectHandler extends SimpleChannelInboundHandler<Serializable> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {
            // TODO: 2020/1/2
        }
    }
}

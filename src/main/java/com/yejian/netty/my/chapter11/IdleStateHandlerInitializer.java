package com.yejian.netty.my.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName IdleStateHandlerInitializer
 * @Description  发送心跳 通常的发送心跳消息到远程节点的方法时，如果在 60 秒之内没有接收或者发送任何的数据，
 * 我们将如何得到通知；如果没有响应，则连接会被关闭
 * @Author jian.ye
 * @Date 2020/1/2 17:28
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel>{

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //(1) IdleStateHandler 将在被触发时发送一个IdleStateEvent 事件
        pipeline.addLast(new IdleStateHandler(0,0,60, TimeUnit.SECONDS));
        //将一个 HeartbeatHandler 添加到ChannelPipeline中
        pipeline.addLast(new HeartbeatHandler());
    }

    //实现 userEventTriggered() 方法以发送心跳消息
    static final class HeartbeatHandler extends ChannelInboundHandlerAdapter{


        //发送到远程节点的心跳消息
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
         "HEARTBEAT", CharsetUtil.ISO_8859_1));
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                //发送心跳信息 并在发送失败时候关闭该链接
            if(evt instanceof IdleStateEvent){
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else{
                //不是 IdleStateEvent 事件，所以将它传递给下一个 ChannelInboundHandler
                super.userEventTriggered(ctx,evt);
            }
        }
    }
}

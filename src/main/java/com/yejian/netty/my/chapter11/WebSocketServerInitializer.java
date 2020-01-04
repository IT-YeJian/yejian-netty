package com.yejian.netty.my.chapter11;

import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @ClassName WebSocketServerInitializer
 * @Description 在服务器端支持 WebSocket
 * @Author jian.ye
 * @Date 2020/1/2 17:07
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {




    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec()
                //为握手提供聚合的httpRequest
                ,new HttpObjectAggregator(65536)
                //如果被请求的端点是 /websocket 则处理该升级握手
        ,new WebSocketServerProtocolHandler("/wevsocket")
                //TextFrameHandler 处理TextWebSocketFrame
        ,new TextFrameHandler()
                //BinaryFrameHandler 处理BinaryWebFrame
        ,new BinaryFrameHandler()
                //ContinuationFrameHandler 处理ContinuationWebFrame
        ,new ContinuationFrameHandler());
    }

    public  static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        }
    }
    public static final class BinaryFrameHandler extends
            SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 BinaryWebSocketFrame msg) throws Exception {
            // Handle binary frame
        }
    }

    public static final class ContinuationFrameHandler extends
            SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 ContinuationWebSocketFrame msg) throws Exception {
            // Handle continuation frame
        }
    }
}

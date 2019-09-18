package com.yejian.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName WebSocketChannelInitializer
 * @Description WebSocket是基于http所以需要http编解码器
 * HttpObjectAggregator 用的比较多
 *
 * netty采取了分块或分段的方式
 * 比如请求1000 被分成10段 每段100
 * 这样每一次channelRead0的时候只能读到100
 *
 *HttpObjectAggregator则是把这10段聚合到了一起
 * 作为完整 请求 /响应
 * @Author jian.ye
 * @Date 2019/9/18 16:09
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler()); //以块的方式去写的方式
        pipeline.addLast(new HttpObjectAggregator(8192));
        //websocket 协议-> ws://server:port/context_path
        //                              括号内的/ws是 context_path
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}

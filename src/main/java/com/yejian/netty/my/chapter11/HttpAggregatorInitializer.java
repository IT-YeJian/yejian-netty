package com.yejian.netty.my.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName HttpAggregatorInitializer
 * @Description 由于 HTTP 的请求和响应可能由许多部分组成，因此你需要聚合它们以形成完整的消息
 * 自动聚合 HTTP 的消息片段
 * @Author jian.ye
 * @Date 2020/1/2 16:20
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(isClient){
            //如果是客户端，则添加 HttpClientCodec
            pipeline.addLast("codec",new HttpClientCodec());
        }else {
            //如果是服务器，则添加 HttpServerCodec
            pipeline.addLast("codec", new HttpServerCodec());
        }

        //将最大的消息为512KB的HttpObjectAggregator 添加到 ChannelPipeline
        pipeline.addLast("aggregator",
                new HttpObjectAggregator(512 * 1024));
    }
}

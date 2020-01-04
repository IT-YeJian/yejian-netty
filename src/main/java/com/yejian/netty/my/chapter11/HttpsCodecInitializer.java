package com.yejian.netty.my.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @ClassName HttpsCodecInitializer
 * @Description TODO
 * @Author jian.ye
 * @Date 2020/1/2 16:37
 */
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {


    private final SslContext context;
    private final boolean isClient;

    public HttpsCodecInitializer(SslContext context, boolean isClient) {
        this.context = context;
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine sslEngine = context.newEngine(ch.alloc());
        //将 SslHandler 添加到  ChannelPipeline 中以使用 HTTPS
        pipeline.addFirst("ssl",new SslHandler(sslEngine));

        if(isClient){
            pipeline.addLast("codec",new HttpClientCodec());
        }else{
            pipeline.addLast("codec",new HttpServerCodec());
        }
    }
}

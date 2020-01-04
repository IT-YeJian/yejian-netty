package com.yejian.netty.my.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @ClassName SslChannelInitializer
 * @Description 添加 SSL/TLS 支持
 * @Author jian.ye
 * @Date 2020/1/2 15:47
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext sslContext;

    private final boolean startTls;

    /**
     *
     * @param sslContext 传入要使用的 SslContext
     * @param startTls  //如果设置为 true，第一个写入的消息将不会被加密（客户端应该设置为 true）
     */
    public SslChannelInitializer(SslContext sslContext, boolean startTls) {
        this.sslContext = sslContext;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        //对于每个SslHandler实例 都是用CHannel的ByteBufAllocator 从 SslContext 获取一个新的 SSLEngine
        SSLEngine sslEngine = sslContext.newEngine(ch.alloc());
        //将SslHandler 作为第一个ChannelHander添加到pipeLine中
        ch.pipeline().addFirst("ssl",new SslHandler(sslEngine,startTls));
    }
}

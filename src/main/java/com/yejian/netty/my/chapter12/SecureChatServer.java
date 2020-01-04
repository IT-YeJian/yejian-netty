package com.yejian.netty.my.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;

/**
 * @ClassName SecureChatServer
 * @Description 向 ChatServer 添加加密
 * @Author jian.ye
 * @Date 2020/1/3 15:57
 */
public class SecureChatServer extends ChatServer{
    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }
    @Override
    protected ChannelInitializer<Channel> createInitializer(
            ChannelGroup group) {
        //返回之前创建的 SecureChatServerInitializer 以启用加密
        return new SecureChatServerInitializer(group, context);
    }
   
    public static void main(String[] args) throws Exception {
        int port = 8899;
        SelfSignedCertificate certificate = new SelfSignedCertificate();
        SslContext context = SslContext.newServerContext(certificate.certificate(), certificate.privateKey());
        final SecureChatServer endpoint = new SecureChatServer(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();


    }
}

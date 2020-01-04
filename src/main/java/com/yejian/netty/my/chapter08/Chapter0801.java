package com.yejian.netty.my.chapter08;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioDatagramChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @ClassName Chapter0801
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 10:12
 */
public class Chapter0801 {

    public static void main(String[] args) {

    }

    //引导一个客户端
    public void bootstrap() throws Exception {
        //设置 EventLoopGroup 提供用于处理channel 事件的eventLoop
        EventLoopGroup group = new NioEventLoopGroup();
        //创建一个BootStrap类的实例以创建和连接新的客户端Channel
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)//指定要使用的channel实现
                //设置用于channel事件和数据的SimpleChannelInboundHandler
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });

        ChannelFuture channelFuture = bootstrap
                .connect(new InetSocketAddress("www.manning.com", 80)).sync();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.err.println("Connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }


}

class BootstrapServer {

    public void bootstrap() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        //创建Server BootStrap
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //设置EventLoopGroup 其提供了用于处理channel事件的EventLoop

        serverBootstrap.group(eventLoopGroup)
                //指定要使用的channel实现
                .channel(NioServerSocketChannel.class)
                //设置用于处理已被接受的子channel的I/O及数据的ChannelInboundHandler
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        //通过配置好的ServerBootStrap的实例绑定该channel
        ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8080)).sync();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

    }
}

/**
 * 假设你的服务器正在处理一个客户端的请求，这个请求需要它充当第三方系统的客户端。当
 * 一个应用程序（如一个代理服务器）必须要和组织现有的系统（如 Web 服务或者数据库）集成
 * 时，就可能发生这种情况。在这种情况下，将需要从已经被接受的子 Channel 中引导一个客户
 * 端 Channel
 * <p>
 * 一个更好的解决方案是：通过将已被接受的子Channel 的 EventLoop 传递给 Bootstrap
 * 的 group()方法来共享该 EventLoop
 * 因为分配给 EventLoop 的所有 Channel 都使用同一
 * 个线程，所以这避免了额外的线程创建，以及前面所提到的相关的上下文切换
 */
class BootstrapSharingEventLoopGroup {

    public void bootstrap() {

        //创建ServerBootStrap 以创建 ServerSocketChannel 并绑定它
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture channelFuture;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(ctx.channel().eventLoop())
                                .channel(NioSocketChannel.class)
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        System.out.println("Received data");
                                    }
                                });
                        channelFuture = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        if (channelFuture.isDone()) {
                            //当连接完成时，执行一些数据操作（如代理）
                            // do something with the data
                        }
                    }
                });
        final ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8080));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

    }

}

class BootstrapWithInitializer {
    public void bootstrap() throws InterruptedException {
        //创建 ServerBootstrap 以创建和绑定新的 Channel
        ServerBootstrap bootstrap = new ServerBootstrap();
        //设置 EventLoopGroup，其将提供用以处理 Channel 事件的 EventLoop
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                //指定 Channel 的实现
                .channel(NioServerSocketChannel.class)
                //注册一个 ChannelInitializerImpl 的实例来设置 ChannelPipeline
                .childHandler(new ChannelInitializerImpl());
        //绑定到地址
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.sync();
    }

    final class ChannelInitializerImpl extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        }
    }
}

class BootstrapClientWithOptionsAndAttrs {
    public void bootstrap() {
        //创建一个AttributeKey 以标识该属性
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
        //创建一个BootSteap 类的实例以创建客户端channel 并连接它们
        Bootstrap bootstrap = new Bootstrap();
        //设置 EventLoopGroup，其提供了用以处理 Channel 事件的 EventLoop
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        //使用  AttributeKey 检索属性以及它的值
                        Integer idValue = ctx.channel().attr(id).get();
                        // TODO: 2019/12/31
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });

        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        //存储该id属性
        bootstrap.attr(id, 123456);
        //使用配置好的BootStrap实例连接到远程主机
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        channelFuture.syncUninterruptibly();

    }
}

class BootstrapDatagramChannel {
    public void bootstrap() {
        //床架一个Bootstrap的实例以创建和绑定新的数据报Channel
        Bootstrap bootstrap = new Bootstrap();
        //设置EventLoopGroup 其提供了用以处理Channel事件的EventLoop
        bootstrap.group(new OioEventLoopGroup()).channel(
                //指定 Channel 的实现
                OioDatagramChannel.class
        ).handler(//设置用以处理 Channel 的 I/O 以及数据的 ChannelInboundHandler
                new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        // TODO: 2019/12/31
                    }
                }
        );
        //调用bind 方法 因为该协议是无连接的
        ChannelFuture future  = bootstrap.bind(new InetSocketAddress(0));
        future .addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Channel bound");
                } else {
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

    }
}
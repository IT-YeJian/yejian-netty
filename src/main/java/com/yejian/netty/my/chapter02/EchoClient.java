package com.yejian.netty.my.chapter02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @ClassName EchoClient
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/27 20:11
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host,int port){
        this.host=host;
        this.port = port;
    }
    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建BootStrap
            Bootstrap bootstrap  = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect().sync();//连接到远程节点 阻塞等待直到连接完成
            channelFuture.channel().closeFuture().sync();//阻塞 直到channel关闭
        }finally {
            System.out.println("发生error");
            group.shutdownGracefully().sync();//关闭线程池并且释放所有的资源
        }
    }

    public static void main(String[] args) throws Exception {
       // args[0]="localhost";
     //   args[1]="8899";
      //  if(args.length!=2){
      //      System.err.println("Usage: "+EchoClient.class.getSimpleName()
     //       +"<host><port>");
      //     return;
     //   }

        String host="localhost";//args[0];
        int port =8899;// Integer.parseInt(args[1]);
        new EchoClient(host,port).start();
    }
}

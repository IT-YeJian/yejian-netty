package com.yejian.netty.stickpackage;





import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @ClassName MyClientInitializer
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/9/17 11:45
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {



    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyClientHandler());
    }
}

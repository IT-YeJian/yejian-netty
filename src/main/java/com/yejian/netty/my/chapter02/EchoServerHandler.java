package com.yejian.netty.my.chapter02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @ClassName EchoServerHandler
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/27 19:23
 */
//标志一个channelHandler可以被多个channel安全的共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        ByteBuf in = (ByteBuf)msg;
        System.out.println("server received : "+in.toString(Charset.forName("utf-8")));

        ctx.write(in);//将接受到的消息发送给客户端 而不冲刷出站消息
        // write()操作是异步的，直
        //到 channelRead()方法返回后可能仍然没有完成
        //为此，EchoServerHandler
        //扩展了 ChannelInboundHandlerAdapter
        // ，其在这个时间点上不会释放消息
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接被建立----");
    }

    //
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将缓冲的消息冲刷到远程节点 并且关闭该channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     *   个 Channel 都拥有一个与之相关联的 ChannelPipeline，其持有一个 ChannelHandler 的
     *         //实例链。在默认的情况下，ChannelHandler 会把对它的方法的调用转发给链中的下一个 Channel-
     *         //Handler。因此，如果 exceptionCaught()方法没有被该链中的某处实现，那么所接收的异常将会被
     *         //传递到 ChannelPipeline 的尾端并被记录。
     *         //为此，你的应用程序应该提供至少有一个实现了
     *         //exceptionCaught()方法的 ChannelHandler。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//关闭该channel
    }
}

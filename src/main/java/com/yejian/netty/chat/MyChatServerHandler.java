package com.yejian.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @ClassName MyChatServerHandler
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/9/17 14:22
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //组里面放的是 建立好连接的 channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch->{
            if(channel != ch){
                ch.writeAndFlush(channel.remoteAddress()+" " +
                        "发送的消息: "+msg+"\n");
            }else{
                ch.writeAndFlush("[自己]:  "+msg + "\n");
            }
        });
    }


    /**
     * 连接建立 不带消息msg
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //获取channel对象
        Channel channel = ctx.channel();
        //实现通知所有的之前的channel
        channelGroup.writeAndFlush("[服务器] - "+channel.remoteAddress()+" 加入\n");
        channelGroup.add(channel);
    }

    /**
     * 连接断掉
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器] - "+channel.remoteAddress()+" 离开\n");
        //channelGroup.remove(channel); netty会自动的移除掉已经连接断掉的channel
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+" 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+" 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

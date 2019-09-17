package com.yejian.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @ClassName TestHttpServerHandler
 * @Description 自定义处理器  Inbound 就是进来的请求 Outbound就是出去
 * 浏览器打印 http请求无状态 keepAlived 有时间
 * 基于1.0  短连接  请求发过来之后服务器就把连接关闭掉
 * 基于1.1 keepAlived 有时间 长连接 时间一过 服务器主动断开连接
 *
 * 判断客户端配置的 keepAlived的时间内 没有新的请求过来 服务器主动关闭
 * handler addded
 * channel register
 * channel active
 * 请求方法名: GET
 * channel Inactive
 * channel Unregistered
 * handler Removed
 * @Author jian.ye
 * @Date 2019/9/16 20:12
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端发过来的请求 并向客户端做出响应的方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println(msg.getClass());


        System.out.println(ctx.channel().remoteAddress());

        if(msg instanceof HttpRequest){
            //向客户端响应的内容
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("请求方法名: "+httpRequest.method().name());

            URI uri =new URI(httpRequest.uri());
            //请求路由 需要考虑
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求favicon.ico");
                return;
            }

            ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            ctx.writeAndFlush(response);

            //关闭请求连接
           ctx.channel().close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel register");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler addded");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Unregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler Removed");
        super.handlerRemoved(ctx);
    }
}

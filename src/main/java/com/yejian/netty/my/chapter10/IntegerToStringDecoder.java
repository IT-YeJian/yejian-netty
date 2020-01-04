package com.yejian.netty.my.chapter10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @ClassName IntegerToStringDecoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 20:11
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        //将Integer消息黄钻换为它的string 表示 并将其添加到输出的list中
        out.add(String.valueOf(msg));
    }
}

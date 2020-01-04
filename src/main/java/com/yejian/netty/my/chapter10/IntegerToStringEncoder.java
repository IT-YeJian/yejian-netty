package com.yejian.netty.my.chapter10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @ClassName IntegerToStringEncoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 21:01
 */
public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        //将 Integer 转换为 String，
        //并将其添加到 List 中
        out.add(String.valueOf(msg));

    }
}

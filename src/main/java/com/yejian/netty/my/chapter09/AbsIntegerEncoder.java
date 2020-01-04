package com.yejian.netty.my.chapter09;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @ClassName AbsIntegerEncoder
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 15:47
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {


    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        //检测是否有足够的字节用来编码
        while (msg.readableBytes()>=4){
            //从输入的ByteBuf中读取下一个整数 并且计算其绝对值
            int value = Math.abs(msg.readInt());
            //将该整数写入到编码消息list中
            out.add(value);
        }
    }
}

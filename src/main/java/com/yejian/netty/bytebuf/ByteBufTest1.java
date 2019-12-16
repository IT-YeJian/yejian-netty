package com.yejian.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @ClassName ByteBufTest1
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/1 10:46 上午
 */
public class ByteBufTest1 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("叶hello world", Charset.forName("utf-8"));
        System.out.println(byteBuf.isDirect());
        if (byteBuf.hasArray()) { //判断是否是堆上的缓冲
            byte[] bytes = byteBuf.array();
            System.out.println(new String(bytes, Charset.forName("utf-8")));
            System.out.println(byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            int len = byteBuf.readableBytes();

            //byteBuf.readByte();
            System.out.println(len);

            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char) byteBuf.getByte(i));
            }
            System.out.println(byteBuf.readableBytes());


            System.out.println(byteBuf.getCharSequence
                    (0,4,Charset.forName("utf-8")));
        }
    }
}

package com.yejian.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName ByteBufTest0
 * @Description ByteBuf
 * @Author jian.ye
 * @Date 2019/11/24 4:54 下午
 */
public class ByteBufTest0 {

    public static void main(String[] args) {
        //非池化 用完就销毁 长度为10
        ByteBuf byteBuf = Unpooled.buffer(10);
        for (int i=0;i<10;i++){
            byteBuf.writeByte(i);
        }
        for (int i=0;i<byteBuf.capacity();i++){
            System.out.println(byteBuf.getByte(i));
        }
    }
}

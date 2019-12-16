package com.yejian.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

/**
 * @ClassName ByteBufTest2
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/1 12:22 下午
 */
public class ByteBufTest2 {

    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf= Unpooled.compositeBuffer();

        ByteBuf heapBuf = Unpooled.buffer(10);
        ByteBuf directBuf = Unpooled.directBuffer(8);

        compositeByteBuf.addComponents(heapBuf,directBuf);

       // compositeByteBuf.removeComponent(0);

        Iterator<ByteBuf> iterable = compositeByteBuf.iterator();
        while (iterable.hasNext()){
            System.out.println(iterable.next());
        }

        compositeByteBuf.forEach(System.out::println);
    }
}

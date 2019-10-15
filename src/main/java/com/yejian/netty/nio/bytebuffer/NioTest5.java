package com.yejian.netty.nio.bytebuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NioTest4
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/14 17:31
 */
public class NioTest5 {

    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(15);
        buffer.putLong(500000L);
        buffer.putDouble(15.32);
        buffer.putChar('好');
        buffer.putShort((short)2);
        buffer.putChar('我');

        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }
}

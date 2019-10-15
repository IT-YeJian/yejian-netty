package com.yejian.netty.nio.bytebuffer;


import java.nio.ByteBuffer;

/**
 * @ClassName NioTest7
 * @Description 只读buffer 只能读 不能写
 * 但不能讲一个只读buffer转换为一个读写buffer
 * @Author jian.ye
 * @Date 2019/10/14 17:31
 */
public class NioTest7 {

    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.getClass());


        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        ByteBuffer readonlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readonlyBuffer.getClass());


      //  readonlyBuffer.position(0);
      //  readonlyBuffer.put((byte) 2);

    }
}

package com.yejian.netty.nio.bytebuffer;


import java.nio.ByteBuffer;

/**
 * @ClassName NioTest4
 * @Description Slice Buffer 与原有的buffer共享相同的底层数组
 *
 * @Author jian.ye
 * @Date 2019/10/14 17:31
 */
public class NioTest6 {

    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);


        for (int i=0;i<buffer.capacity();i++){
            buffer.put((byte)i);
        }

        buffer.position(2);
        buffer.limit(6);

        ByteBuffer newByteBuffer = buffer.slice();

        for (int i=0;i<newByteBuffer.capacity();i++){
            byte b = newByteBuffer.get(i);
            b*=2;
            newByteBuffer.put(i,b);
        }
        System.out.println(buffer==newByteBuffer);


        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

    }
}

package com.yejian.netty.nio.bytebuffer;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @ClassName NioTest1
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/12 10:58
 */
public class NioTest1 {

    public static void main(String[] args) {
        //分配 int 缓存区
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < 5; i++) {
            //随机数生成 建议SecureRandom 提供了更为随机
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        System.out.println("before flip limit: "+buffer.limit());


        buffer.flip(); //实现读写状态的切换
        System.out.println("after flip limit: "+buffer.limit());

        System.out.println("enter loop ...");
        while (buffer.hasRemaining()){
            System.out.println("----------------------");
            System.out.println("pos "+buffer.position());
            System.out.println("limit "+buffer.limit());
            System.out.println("capacity "+buffer.capacity());
            System.out.println(buffer.get());
        }
        buffer.clear();
       // buffer.flip(); //实现读写状态的切换
        while (buffer.hasRemaining()){
            System.out.println("----------------------");
            System.out.println("pos "+buffer.position());
            System.out.println("limit "+buffer.limit());
            System.out.println("capacity "+buffer.capacity());
            System.out.println(buffer.get());
        }
        System.out.println(buffer);
    }
}

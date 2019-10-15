package com.yejian.netty.nio.bytebuffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

/**
 * @ClassName NioTest1
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/12 10:58
 */
public class NioTest2 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("E:\\yejian\\yejian-netty\\NIOTest2.txt");
        //获取流的通道对象
        FileChannel fileChannel = fileInputStream.getChannel();

        //不管读取 写入 buffer一定要有的
        ByteBuffer buffer =ByteBuffer.allocate(512);
        //往buffer里面写
        //磁盘
        //buffer  ->   channel    ->      程序buffer
        //
        // \____________________________/
        fileChannel.read(buffer);

        //再把buffer中的数据 读取到程序当中 定义操作反转
        buffer.flip();

        while (buffer.hasRemaining()){
            byte b = buffer.get();
            System.out.println("character: "+(char)b);
        }
        fileInputStream.close();
    }
}

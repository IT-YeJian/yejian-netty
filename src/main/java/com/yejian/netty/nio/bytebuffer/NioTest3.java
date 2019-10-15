package com.yejian.netty.nio.bytebuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NioTest1
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/12 10:58
 */
public class NioTest3 {

    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream= new FileOutputStream("E:\\yejian\\yejian-netty\\NIOTest3.txt");
        //获取流的通道对象
        FileChannel fileChannel = fileOutputStream.getChannel();

        //不管读取 写入 buffer一定要有的
        ByteBuffer buffer =ByteBuffer.allocate(512);

        byte [] message ="hello world yejain".getBytes();

        for (int i=0;i<message.length;i++){
            buffer.put(message[i]);
        }


        //往channel里面写
        //
        //程序                            磁盘
        //buffer  ->   channel   ->      buffer
        //
        // \____________________________/
        buffer.flip();
        fileChannel.write(buffer);
        fileOutputStream.close();

    }
}

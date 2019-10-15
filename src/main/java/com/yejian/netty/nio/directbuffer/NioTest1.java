package com.yejian.netty.nio.directbuffer;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.nio.ByteBuffer;

import java.nio.channels.FileChannel;

/**
 * @ClassName NioTest1
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/15 16:38
 */
public class NioTest1 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("E:\\yejian\\yejian-netty\\input2.txt");
        FileOutputStream outputStream = new FileOutputStream("E:\\yejian\\yejian-netty\\output2.txt");


        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();


        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        while (true){
            buffer.clear();

            int read = inputChannel.read(buffer);

            System.out.println("read : " + read);

            if(-1==read){
                break;
            }
            buffer.flip();

            outputChannel.write(buffer);
        }
        inputChannel.close();
        outputChannel.close();
    }
}

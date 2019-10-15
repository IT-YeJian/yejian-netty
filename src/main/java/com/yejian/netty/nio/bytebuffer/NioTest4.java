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
public class NioTest4 {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("E:\\yejian\\yejian-netty\\input.txt");
        FileOutputStream outputStream = new FileOutputStream("E:\\yejian\\yejian-netty\\output.txt");


        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();


        ByteBuffer buffer = ByteBuffer.allocate(4);

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

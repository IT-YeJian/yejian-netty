package com.yejian.netty.nio.directbuffer;


import java.io.RandomAccessFile;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NioTest2
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/15 16:38
 */
public class NioTest2 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile  = new RandomAccessFile("E:\\yejian\\yejian-netty\\MappedByteBuffer.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,
                0,5);
        mappedByteBuffer.put(0,(byte)'a');
        mappedByteBuffer.put(3,(byte)'b');

        randomAccessFile.close();

    }
}

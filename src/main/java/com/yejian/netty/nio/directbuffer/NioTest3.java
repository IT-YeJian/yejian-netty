package com.yejian.netty.nio.directbuffer;


import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @ClassName NioTest3
 * @Description 文件锁
 * @Author jian.ye
 * @Date 2019/10/16 10:50
 */
public class NioTest3 {

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("FileLock.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        FileLock fileLock = fileChannel.lock(3,6,true);

        System.out.println("valid :" +fileLock.isValid());
        System.out.println("lock type:" +fileLock.isShared());


        fileLock.release();
        fileChannel.close();

    }
}

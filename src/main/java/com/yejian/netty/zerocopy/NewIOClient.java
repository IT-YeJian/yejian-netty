package com.yejian.netty.zerocopy;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NewIOClient
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/24 16:56
 */
public class NewIOClient {

    public static void main(String[] args) throws IOException {
        String fileName = "E:\\devTools\\jdk-8u152-linux-x64.tar.gz";
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));

        socketChannel.configureBlocking(true);//一直写完后 才返回

        //获取文件通道
        FileChannel fileChannel = new FileInputStream(new File(fileName)).getChannel();

        long startTime = System.currentTimeMillis();

        //返回实际传输的字节
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("send 总子节数: "+transferCount+" ,耗时"+(System.currentTimeMillis()-startTime));

    }
}

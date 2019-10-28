package com.yejian.netty.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NewIOServer
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/23 15:43
 */
public class NewIOServer {

    public static void main(String[] args) throws Exception {
        InetSocketAddress socketAddress = new InetSocketAddress(8899);
        //服务端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(socketAddress);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);//这里改成阻塞 因为这里没有使用selector做选择

            int readCount = 0;

            while (-1 != readCount) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                byteBuffer.rewind();
            }
        }
    }
}

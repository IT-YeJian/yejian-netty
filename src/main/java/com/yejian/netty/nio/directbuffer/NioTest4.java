package com.yejian.netty.nio.directbuffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @ClassName NioTest4
 * @Description 关于buffer的Scattering与Gathering
 * @Author jian.ye
 * @Date 2019/10/16 10:55
 */
public class NioTest4 {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.bind(address);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] byteBuffers = new ByteBuffer[3];

        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {

            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long r = socketChannel.read(byteBuffers);
                bytesRead += r;
                System.out.println("bytesRead " + bytesRead);

                Arrays.asList(byteBuffers).stream().map(buffer -> "pos:" + buffer.position() + " limit :" + buffer.limit())
                        .forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.flip();
            });

            long bytesWritten = 0;
            while (bytesWritten < messageLength) {
                long r = socketChannel.write(byteBuffers);
                bytesWritten += r;
            }

            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.clear();
            });

            System.out.println("byteRead : " + bytesRead + " , " +
                    "bytesWritten " + bytesWritten + " ,messageLength " + messageLength);
        }


    }
}

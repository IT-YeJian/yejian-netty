package com.yejian.netty.nio.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NioClientDemo
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/21 15:08
 */
public class NioClientDemo {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();

            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

            while (true) {
                int number = selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isConnectable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();

                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(512);

                            writeBuffer.put((LocalDateTime.now() + " success").getBytes());
                            writeBuffer.flip();
                            channel.write(writeBuffer);


                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.execute(() -> {

                                while (true) {

                                    try {
                                        writeBuffer.clear();
                                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        String sendMessage = bufferedReader.readLine();
                                        writeBuffer.put(sendMessage.getBytes());
                                        writeBuffer.flip();
                                        channel.write(writeBuffer);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        //注册读取 来自服务端的返回数据
                        channel.register(selector, SelectionKey.OP_READ);

                    } else if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        int read = channel.read(byteBuffer);

                        if (read > 0) {
                            System.out.println(new String(byteBuffer.array(), 0, read));
                        }
                    }
                }
                selectionKeys.clear();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

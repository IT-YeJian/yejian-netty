package com.yejian.netty.nio.chat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName NioServerDemo
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/21 10:30
 */
public class NioServerDemo {
    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();

        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocket.bind(address);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            int select = selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                final SocketChannel socketChannel;

                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                        socketChannel = channel.accept();

                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        String key = UUID.randomUUID() + "";

                        clientMap.put(key, socketChannel);

                    } else if (selectionKey.isReadable()) {
                        socketChannel = (SocketChannel) selectionKey.channel();

                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        int read = socketChannel.read(byteBuffer);

                        if (read > 0) {
                            byteBuffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            String recMessage = String.valueOf(charset.decode(byteBuffer).array());

                            System.out.println(socketChannel + " :" + recMessage);

                            String senderKey = "";

                            for (Map.Entry<String, SocketChannel> maps : clientMap.entrySet()) {
                                if (socketChannel == maps.getValue()) {
                                    senderKey = maps.getKey();
                                    break;
                                }
                            }
                            for (Map.Entry<String, SocketChannel> maps : clientMap.entrySet()) {
                                SocketChannel value = maps.getValue();

                                ByteBuffer buffer = ByteBuffer.allocate(512);


                                buffer.put(("senderKey " + senderKey + " :" +recMessage).getBytes());

                                buffer.flip();
                                value.write(buffer);
                            }
                        }
                    }
                    selectionKeys.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

        }


































































    /*    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        serverSocket.bind(inetSocketAddress);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            int numbers = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                final SocketChannel channel;

                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();

                        channel = serverSocketChannel1.accept();
                        channel.configureBlocking(false);
                        channel.register(selector,SelectionKey.OP_READ);

                        //将这个SocketChannel 放入map  方便消息分发
                        String key = "[" + UUID.randomUUID().toString() + "]";
                        clientMap.put(key, channel);
                    }else if(selectionKey.isReadable()) {
                        channel = (SocketChannel) selectionKey.channel();

                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

                        int read = channel.read(byteBuffer);

                        if (read > 0) {
                            byteBuffer.flip();
                            Charset charset = Charset.forName("UTF-8");
                            String recMessage = String.valueOf(charset.decode(byteBuffer).array(), 0, read);
                            System.out.println(channel + " :" + recMessage);
                            //分发消息给所有的客户端
                            String senderKey = null;


                            for (Map.Entry<String, SocketChannel> maps : clientMap.entrySet()) {
                                SocketChannel value = maps.getValue();
                                if (value == channel) {
                                    senderKey = maps.getKey();
                                    break;
                                }
                            }
                            for (Map.Entry<String, SocketChannel> maps : clientMap.entrySet()) {
                                SocketChannel channel1 = maps.getValue();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((senderKey + " :" + recMessage.getBytes()).getBytes());
                                writeBuffer.flip();
                                channel1.write(writeBuffer);
                            }
                        }

                        System.out.println(new String(byteBuffer.array(), 0, read));
                    }

                    selectionKeys.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }*/

    }
}

package com.yejian.netty.nio.chat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NioClient
 * @Description 客户端向服务器端发送数据
 * @Author jian.ye
 * @Date 2019/10/18 17:20
 */
public class NioClient {

    public static void main(String[] args) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            //注册连接
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));


            while (true) {

                int numbers = selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isConnectable()) {//已经建立好了连接
                        //获取与之关联的SocketChannel对象
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        if (clientChannel.isConnectionPending()) {
                            clientChannel.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + " success").getBytes());
                            writeBuffer.flip();
                            clientChannel.write(writeBuffer);

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
                                        clientChannel.write(writeBuffer);
                                    } catch (Exception e) {

                                    }
                                }
                            });
                        }
                        //注册读取 来自服务端的返回数据
                        clientChannel.register(selector,SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel)selectionKey.channel();
                        ByteBuffer readBuffer  =ByteBuffer.allocate(1024);

                        int count = client.read(readBuffer);
                        if(count>0){
                            String recMessage = new String(readBuffer.array(),0,count);
                            System.out.println(recMessage);
                        }
                    }
                }
                selectionKeys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package com.yejian.netty.nio.directbuffer;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName NioTest5
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/16 16:31
 */
public class NioTest5 {

    public static void main(String[] args) throws Exception {
        int[] ports = new int[5];
        for (int i = 0; i < 5; i++) {
            ports[i] = 5000 + i;
        }

        Selector selector = Selector.open();

        System.out.println(SelectorProvider.provider().getClass());

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //配置是否阻塞 一定要设置为false 表示不阻塞 true表示阻塞
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress inetSocketAddress =
                    new InetSocketAddress(ports[i]);
            serverSocket.bind(inetSocketAddress);
            //将当前selector注册到channel上面 注册感兴趣的事件
            SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口 " + ports[i]);
        }

        while (true) {
            int numbers = selector.select();
            System.out.println("numbers " + numbers);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            System.out.println("selectionKeys:" + selectionKeys);
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) { //有没请求连接进来
                    //获取selector上注册的channel
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);
                    //当处理完一个selectionKey,应该将其从集合selectionKey集合上移除
                    // 不删除掉还会重复监听个事件
                    iterator.remove();
                    System.out.println("获得客户端连接：" + socketChannel);
                } else if (selectionKey.isReadable()) {//有没数据进来
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int byteRead = 0;
                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

                        int read = socketChannel.read(byteBuffer);
                        if (read <= 0) {
                            break;
                        }

                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        byteRead += read;
                    }
                    System.out.println("读取 : " + byteRead + " ,来自于: " + socketChannel);
                    iterator.remove();
                }
            }
        }


    }
}

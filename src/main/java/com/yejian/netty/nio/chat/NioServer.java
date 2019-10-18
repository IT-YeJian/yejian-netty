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
 * @ClassName NioServer
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/18 14:08
 */
public class NioServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception {

        /***==================创建的模板======================================**/
        //1.第一步构造服务端一个通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.配置非阻塞
        serverSocketChannel.configureBlocking(false);
        //3.获取服务端的对象ServerSocket
        ServerSocket serverSocket = serverSocketChannel.socket();
        //4 绑定端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        serverSocket.bind(inetSocketAddress);

        /***=======================================================**/

        //创建selcetor对象
        Selector selector = Selector.open();
        //服务器段关注连接这个事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {//死循环
            int numbers = selector.select();//返回 已经准备好的事件数量
            System.out.println("numbers is " + numbers);
            //返回注册的已经发生的所有事件 和 channel对象
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                final SocketChannel client;
                try {
                    //判断事件类型
                    if (selectionKey.isAcceptable()) {//表示客户端向服务端发起了连接
                        //获取发生事件对应的通道
                        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();

                        client = channel.accept();//接受这个连接,可以跟客户端交互了

                        //将这个连接也注册到selector上
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);

                        //将这个SocketChannel 放入map  方便消息分发
                        String key = "[" + UUID.randomUUID().toString() + "]";
                        clientMap.put(key, client);
                    } else if (selectionKey.isReadable()) { //表示数据可读的了
                        //获取通道 这里SocketChannel？ 因为 上面SocketChannel注册了read
                        client = (SocketChannel) selectionKey.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);


                        int count = client.read(buffer);

                        if (count > 0) {
                            //写到所有的客户端
                            buffer.flip();

                            Charset charset = Charset.forName("utf-8");
                            //将字节数组转为CharBuffer 再调用array转为字符数组
                            String receivedMessage = String.valueOf(charset.decode(buffer).array());
                            System.out.println(client + " :" + receivedMessage);

                            //分发消息给所有的客户端
                            String senderKey = null;

                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (entry.getValue() == client) {
                                    senderKey = entry.getKey();
                                    break;
                                }
                            }

                            //向所有服务端保存的client 发送消息
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                SocketChannel value = entry.getValue();

                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

                                writeBuffer.put((senderKey + " : " + receivedMessage).getBytes());
                                writeBuffer.flip();
                                value.write(writeBuffer);
                            }
                        }
                    }
                    //当处理完一个selectionKey,应该将其从集合selectionKey集合上移除
                    // 不删除掉还会重复监听个事件
                    selectionKeys.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

        }

    }
}

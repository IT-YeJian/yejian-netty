package com.yejian.netty.zerocopy;

import java.io.DataInputStream;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName OldIOServer
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/23 14:52
 */
public class OldIOServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) {
            Socket socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try {

                byte[] bytes = new byte[4096];

                while (true) {//获取对端发的数据
                    int readCount = dataInputStream.read(bytes, 0, bytes.length);
                    if (readCount == -1) {
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }

    }
}

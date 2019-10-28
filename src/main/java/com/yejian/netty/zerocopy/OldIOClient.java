package com.yejian.netty.zerocopy;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @ClassName OldIOClient
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/10/23 15:02
 */
public class OldIOClient {

    public static void main(String[] args) throws Exception{

        Socket client = new Socket("localhost",8899);

        String fileName = "E:\\devTools\\jdk-8u152-linux-x64.tar.gz";

        InputStream inputStream = new FileInputStream(new File(fileName));

        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());

        byte [] buffer = new byte[4096];

        long readCount;
        long total=0;

        long startTime=System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer))>=0){
            total+=readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("send total byte is :"+total+" 耗时: "+(System.currentTimeMillis()-startTime));

        dataOutputStream.close();
        client.close();
        inputStream.close();
    }
}

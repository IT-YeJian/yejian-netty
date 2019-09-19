package com.yejian.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @ClassName ProtoBufTest
 * @Description 读写对象
 * @Author jian.ye
 * @Date 2019/9/19 15:36
 */
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("zhangsan").setAge(12).setAddress("北京").build();

        byte [] student2ByteArray=student.toByteArray();

        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());

    }
}

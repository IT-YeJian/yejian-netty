package com.yejian.netty.protobuf;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;


/**
 * @ClassName TestClientHandler
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/9/25 18:04
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        System.out.println("channel Active");
        MyDataInfo.MyMessage myMessage = null;
        if (0 == randomInt) {
            MyDataInfo.Person person = MyDataInfo.Person.newBuilder()
                    .setName("张三")
                    .setAge(20)
                    .setAddress("北京").build();

            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.Person)
                    .setPerson(person).build();

        } else if (1 == randomInt) {
            MyDataInfo.Dog dog = MyDataInfo.Dog.newBuilder().setName("dog").setAge(12).build();
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.Dog)
                    .setDog(dog).build();
        } else {
            MyDataInfo.Cat cat = MyDataInfo.Cat.newBuilder().setCity("shanghai").setName("cat").build();
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.Cat)
                    .setCat(cat).build();
        }
        ctx.channel().writeAndFlush(myMessage);

    }
}

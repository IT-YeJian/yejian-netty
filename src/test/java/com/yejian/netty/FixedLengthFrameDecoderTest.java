package com.yejian.netty;

import com.yejian.netty.my.chapter09.FixedLengthFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @ClassName FixedLengthFrameDecoderTest
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 14:51
 */
public class FixedLengthFrameDecoderTest {

    //使用了注解@Test 标注，因此 JUnit 将会执行该方法
    @Test
    public void testFramesDecoded(){
        //创建一个ByteBuf 并存储 9 字节
        ByteBuf byteBuf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            byteBuf.writeByte(i);
        }
        ByteBuf input = byteBuf.duplicate();
        //创建一个EmbeddedChannel，并添加一个FixedLengthFrameDecoder，其将以 3 字节的帧长度被测试
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        //write bytes
        //将数据写EmbeddedChannel
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        //标记channel 为已完成状态
        assertTrue(embeddedChannel.finish());

        //read message
        //读取所生成的消息 并且验证是否有3帧 其中 每一帧 都为3字节
        ByteBuf buf = (ByteBuf)embeddedChannel.readInbound();
        assertEquals(byteBuf.readSlice(3),buf);
        buf.release();

        buf = (ByteBuf)embeddedChannel.readInbound();
        assertEquals(byteBuf.readSlice(3),buf);
        buf.release();

        buf = (ByteBuf)embeddedChannel.readInbound();
        assertEquals(byteBuf.readSlice(3),buf);
        buf.release();

        assertNull(embeddedChannel.readInbound());
        byteBuf.release();
    }


    @Test
    public void testFramesDecoded2(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel= new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        //返回false 因为没有一个完整的可供读取的帧
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));

        assertTrue(channel.finish());

        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();

    }
}

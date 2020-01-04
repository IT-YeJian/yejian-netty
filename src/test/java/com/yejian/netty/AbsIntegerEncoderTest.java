package com.yejian.netty;

import com.yejian.netty.my.chapter09.AbsIntegerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * @ClassName AbsIntegerEncoderTest
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 15:51
 */
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded(){
        //创建一个ByteBuf 并且写入9个负整数
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i*-1);
        }

       //(2) 创建一个EmbeddedChannel，并安装一个要测试的 AbsIntegerEncoder
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());

        //(3) 写入 ByteBuf，并断言调用 readOutbound()方法将会产生数据
        assertTrue(channel.writeOutbound(buf));
        //4 将该channel 标记为已完成状态
        assertTrue(channel.finish());

        // read bytes
        //(5) 读取所产生的消息，并断言它们包含了对应的绝对值
        for (int i = 1; i < 10; i++) {
            assertEquals(i,(int)channel.readOutbound());
        }
        assertNull(channel.readOutbound());
    }
}

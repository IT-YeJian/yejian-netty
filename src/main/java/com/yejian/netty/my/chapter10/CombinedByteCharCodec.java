package com.yejian.netty.my.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @ClassName CombinedByteCharCodec
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/31 21:36
 */
public class CombinedByteCharCodec extends
        CombinedChannelDuplexHandler<ByteToCharDecoder,CharToByteEncoder> {

    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(),new CharToByteEncoder());
    }
}

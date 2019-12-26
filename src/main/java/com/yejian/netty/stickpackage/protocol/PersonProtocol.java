package com.yejian.netty.stickpackage.protocol;

import lombok.Data;

/**
 * @ClassName PersonProtocol
 * @Description 自定义协议
 * @Author jian.ye
 * @Date 2019/12/26 16:46
 */
@Data
public class PersonProtocol {

    private int length;

    private byte[] content;
}

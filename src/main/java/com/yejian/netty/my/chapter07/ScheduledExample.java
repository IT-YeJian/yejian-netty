package com.yejian.netty.my.chapter07;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;


import java.util.concurrent.*;

/**
 * @ClassName ScheduledExample
 * @Description TODO
 * @Author jian.ye
 * @Date 2019/12/30 20:33
 */
public class ScheduledExample {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void main(String[] args) {

    }

    // 使用 ScheduledExecutorService 调度任务
    public static void schedule() {
        //创建一个其线程池具有10个线程的ScheduledExecutorService
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> schedule = scheduledExecutorService.schedule(new Runnable() {
            //创建一个Runnable 以供调度稍后执行
            @Override
            public void run() {
                System.out.println("Now it is 60 seconds later");
            }//从现在开始的60s之后执行
        }, 60, TimeUnit.SECONDS);
        //调度完成 就关闭  ScheduledExecutorService 释放资源
        scheduledExecutorService.shutdown();
    }

    public static void scheduleViaEventLoop() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        io.netty.util.concurrent.ScheduledFuture<?> scheduledFuture = channel
                .eventLoop().schedule(new Runnable() {
            //创建一个Runnable 以供调度稍后执行
            @Override
            public void run() {
                System.out.println("60 seconds later");
            }//从现在开始的60s之后执行
        }, 60, TimeUnit.SECONDS);
    }

    /**
     * 使用Eventloop调度周期性的任务
     */
    public static void scheduleFixedViaEventLoop() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        io.netty.util.concurrent.ScheduledFuture<?> scheduledFuture = channel.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //将一直运行 直到scheduledFuture被取消 future.cancel(false);
                System.out.println("Run ervery 60 seconds");
            }
            //调度在60秒之后 并且以后每隔60秒运行
        }, 60, 60, TimeUnit.SECONDS);
    }
}

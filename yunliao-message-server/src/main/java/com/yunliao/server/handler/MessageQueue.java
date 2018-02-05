package com.yunliao.server.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class MessageQueue {

    private static BlockingQueue<Object> mesaageQueue  = new LinkedBlockingQueue<Object>();
    public static Object poll(){
        return  mesaageQueue.poll();
    }

    public static void push(Object message) throws InterruptedException{
        mesaageQueue.put(message);
    }
}

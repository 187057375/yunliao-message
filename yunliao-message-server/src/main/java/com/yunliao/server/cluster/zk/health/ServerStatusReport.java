package com.yunliao.server.cluster.zk.health;

import com.yunliao.server.handler.MessageQueue;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ServerStatusReport extends Thread{


    public void run() {
       do{
           System.out.println(MessageQueue.size());
       }while (true);
    }
}

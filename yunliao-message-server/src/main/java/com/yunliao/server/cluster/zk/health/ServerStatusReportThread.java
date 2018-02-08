package com.yunliao.server.cluster.zk.health;

import com.yunliao.server.cluster.zk.Zookeeper;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ServerStatusReportThread extends Thread{


    public void run() {
        int i =0;
        do{
           try {
               ServerMeta serverMeta = ServerOperation.getData(Zookeeper.YUNLIAO_ZK_PAHT);
               serverMeta.setMessageQueueSize(i);
               System.out.println(i);
               i++;
               ServerOperation.setData(serverMeta,Zookeeper.YUNLIAO_ZK_PAHT);
               Thread.sleep(3000);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }while (true);
    }

    public static void main(String[] args) throws Exception {
        ServerStatusReportThread serverStatusReportThread = new ServerStatusReportThread();
        serverStatusReportThread.start();
    }
}

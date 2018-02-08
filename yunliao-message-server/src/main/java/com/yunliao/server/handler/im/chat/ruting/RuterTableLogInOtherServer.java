package com.yunliao.server.handler.im.chat.ruting;

import com.yunliao.server.Application;
import com.yunliao.server.cluster.transport.SendClusterMessage;
import com.yunliao.server.cluster.transport.message.ClusterMessage;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class RuterTableLogInOtherServer {

    public static void findServer(ClusterMessage clusterMessage) throws Exception {

        //查找//消息接收人 toId 所在ip
        String serverIp = UserServerIpTableRedis.getServerIpByUserId(clusterMessage.getToId());
        //TODO
        if (serverIp != null) {
            System.out.println("RuterTableLogInOtherServer上查找用户：" + clusterMessage.getToId());
            clusterMessage.setToIp(serverIp);
            clusterMessage.setToPort(Application.clusterPort);
            SendClusterMessage.send(clusterMessage);
        } else {//没有查询到，
            // TODO: 18/2/6
            //1,放入数据库，上线时候一次查询
            System.out.println("RuterTableLogInOtherServer 上没有查找到用户：" + clusterMessage.getToId());
            throw new Exception("RuterTableLogInOtherServer上用户没有登录,请实现离线消息的处理......");
        }
    }

    public static void main(String[] args) throws Exception {
        ClusterMessage clusterMessage = new ClusterMessage();
        clusterMessage.setToIp("172.28.1.12");
        clusterMessage.setToPort(9001);
        SendClusterMessage.send(clusterMessage);
    }
}

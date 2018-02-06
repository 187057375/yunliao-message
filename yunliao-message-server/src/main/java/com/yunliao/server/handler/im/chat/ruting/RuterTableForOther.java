package com.yunliao.server.handler.im.chat.ruting;

import com.yunliao.server.cluster.transport.SendClusterMessage;
import com.yunliao.server.cluster.transport.message.ClusterMessage;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class RuterTableForOther {

    public static void findServer(ClusterMessage clusterMessage) throws Exception {

        if (true) {
            System.out.println(clusterMessage.getToId());
            clusterMessage.setToIp("");
            clusterMessage.setToPort(8888);
            SendClusterMessage.send(clusterMessage);
        } else {//没有查询到，
            // TODO: 18/2/6
            //1,放入数据库，上线时候一次查询
            throw  new Exception("没有找到目标,离线消息");
        }
    }
}

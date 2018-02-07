package com.yunliao.server.cluster.transport;


import com.yunliao.server.cluster.client.ClusterClient;
import com.yunliao.server.cluster.transport.message.ClusterMessage;

/**
 * 发送集群消息
 */
public class SendClusterMessage {
    public static void send(ClusterMessage clusterMessage) throws Exception{
        ClusterClient.send(clusterMessage.getToIp(), clusterMessage.getToPort(),clusterMessage);
//        String msg = JSON.toJSONString(clusterMessage);
//        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes("UTF-8"));
//        channel.writeAndFlush(buf);
    }
}

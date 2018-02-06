package com.yunliao.server.cluster.transport;


import com.alibaba.fastjson.JSON;
import com.yunliao.server.cluster.client.ClusterClient;
import com.yunliao.server.cluster.transport.message.ClusterMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * 发送集群消息
 */
public class SendClusterMessage {
    public static void send(ClusterMessage clusterMessage) throws Exception{
        Channel channel = ClusterClient.getClusterClient(clusterMessage.getToIp(), clusterMessage.getToPort());
        String msg = JSON.toJSONString(clusterMessage);
        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes("UTF-8"));
        channel.writeAndFlush(buf);
    }
}

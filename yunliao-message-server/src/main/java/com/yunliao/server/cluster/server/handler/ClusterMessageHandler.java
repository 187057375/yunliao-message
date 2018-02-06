package com.yunliao.server.cluster.server.handler;

import com.yunliao.server.cluster.transport.message.ClusterMessage;
import com.yunliao.server.handler.im.chat.MessageHandlerChat;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/6
 *          Copyright 2018 by PreTang
 */
public class ClusterMessageHandler {

    public static void process(ClusterMessage clusterMessage) throws  Exception{
       int type = clusterMessage.getType();
       if(type == ClusterMessage.TYPE_CHAT){
           MessageHandlerChat chat = new MessageHandlerChat();
           chat.process(clusterMessage.getMessage());
       }else{
           throw new Exception("未知消息");
       }

    }

}

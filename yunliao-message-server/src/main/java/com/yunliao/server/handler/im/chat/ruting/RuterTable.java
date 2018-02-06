package com.yunliao.server.handler.im.chat.ruting;

import com.alibaba.fastjson.JSON;
import com.yunliao.server.cluster.transport.message.ClusterMessage;
import com.yunliao.server.handler.Message;
import com.yunliao.server.handler.im.UserSession;
import com.yunliao.server.handler.im.chat.send.SendMessage;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class RuterTable {
    //存放 用户key-UserSession
    public static ConcurrentHashMap userSessioinMap = new ConcurrentHashMap<String, Object>();
    //存放 chanelkey-UserSession
    public static ConcurrentHashMap chanelSessioinMap = new ConcurrentHashMap<String, Object>();

    public static boolean redisRuteTable = false;

    public static void searchAndSetRuter(Message message) throws Exception {
        UserSession fromSession = (UserSession) chanelSessioinMap.get(message.getFromChanel());
        if(fromSession != null){
            String body = new String(message.getBody());
            ChatMessage chatMessage = JSON.parseObject(body, ChatMessage.class);
            //本地查找
            UserSession toSession = (UserSession) userSessioinMap.get(chatMessage.getToId());
            if (toSession != null) {
                message.setToChanelId(toSession.getChanelId());
                message.setToId(chatMessage.getToId());
                message.setFromId(chatMessage.getFromId());
                SendMessage.send(message);
            } else {//本地没有查询到，去redis查，或者主路由查（还没有实现）
                ClusterMessage clusterMessage = new ClusterMessage();
                clusterMessage.setMessage(message);
                clusterMessage.setType(ClusterMessage.TYPE_CHAT);
                clusterMessage.setToId(chatMessage.getToId());
                RuterTableForOther.findServer(clusterMessage);
            }
        }else{
            throw  new Exception(message.getFromChanel()+"没有登录");
        }

    }
}

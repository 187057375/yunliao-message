package com.yunliao.server.handler.im.chat.ruting;

import com.alibaba.fastjson.JSON;
import com.yunliao.server.handler.Message;
import com.yunliao.server.handler.im.UserSession;
import com.yunliao.server.handler.im.chat.ChatMessage;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class RuterTableLocal {
    //存放 用户key-session
    public static ConcurrentHashMap userSessioinMap = new ConcurrentHashMap<String, Object>();
    //存放 chanelkey-session
    public static ConcurrentHashMap chanelSessioinMap = new ConcurrentHashMap<String, Object>();

    public static boolean redisRuteTable = false;

    public static void searchAndSetRuter(Message message) throws Exception {
        UserSession fromSession = (UserSession) chanelSessioinMap.get(message.getFromChanel());
        if(fromSession != null){
            String body = new String(message.getBody());
            ChatMessage chatMessage = JSON.parseObject(body, ChatMessage.class);
            UserSession toSession = (UserSession) userSessioinMap.get(chatMessage.getToId());
            if (toSession != null) {
                message.setLocalServer(true);
                message.setToChanelId(toSession.getChanelId());
                message.setRuterIp(toSession.getServerIp());
                message.setRuterPort(toSession.getServerPort());

                message.setToId(chatMessage.getToId());
                message.setFromId(chatMessage.getFromId());


            } else {
                //todo 广播寻址
                //消息回去继续等待发送？是否需要延长几秒再发？
                //MessageQueue.push(message.getRaw());
                throw  new Exception("没有找到目标");
            }
        }else{
            throw  new Exception(message.getFromChanel()+"没有登录");
        }

    }
}

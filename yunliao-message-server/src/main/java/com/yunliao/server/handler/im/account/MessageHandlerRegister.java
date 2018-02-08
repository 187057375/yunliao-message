package com.yunliao.server.handler.im.account;

import com.yunliao.server.Application;
import com.yunliao.server.handler.Message;
import com.yunliao.server.handler.MessageHandler;
import com.yunliao.server.handler.im.chat.ruting.RuterTable;
import com.yunliao.server.handler.im.UserSession;
import com.yunliao.server.util.redis.RedisUtil;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class MessageHandlerRegister extends MessageHandler{
    /**
     * 用户注册
     * @param message
     * @throws Exception
     */
    public void process(Message message) throws Exception {
        System.out.println("注册消息："+new String(message.getBody()));
        UserSession userSession = new UserSession();
        String registerKey =new String(message.getBody());
        userSession.setUserId(registerKey);
        userSession.setServerIp(Application.serverIp);
        userSession.setChanelId(message.getFromChanel());

        //@todo 注册成功后，账户写入数据库、集群环境下广播自己位置、返回客户端信息（据具体协议）
        RuterTable.userSessioinMap.put(registerKey, userSession);
        RuterTable.chanelSessioinMap.put(message.getFromChanel(), userSession);
        RedisUtil.setString(userSession.getUserId(),userSession.getServerIp());
        message = null;
    }
}

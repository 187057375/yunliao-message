package com.yunliao.server.handler.im.chat.ruting;

import com.yunliao.server.handler.im.UserSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class RuterTableMaster {
    public  static ConcurrentHashMap clientMap = new ConcurrentHashMap<String,Object>();

    public  static UserSession findSession(String sessionKey){
        // TODO: 18/2/2
        //从远程服务器上取路由信息,广播该key的信息，希望的到回应
        return  null;
    }

}

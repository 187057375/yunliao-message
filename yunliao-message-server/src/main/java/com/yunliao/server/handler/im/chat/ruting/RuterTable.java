package com.yunliao.server.handler.im.chat.ruting;

import com.yunliao.server.handler.Message;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class RuterTable {

    public static ConcurrentHashMap clientMap = new ConcurrentHashMap<String, Object>();
    public static boolean redisRuteTable = false;

    public static void searchAndSetRuter(Message message) throws Exception {
        if (redisRuteTable) {//redis路由表查询
            throw new Exception("just now not support redis rute table");
        } else { //本机路由表查找
            RuterTableLocal.searchAndSetRuter(message);
        }
    }
}

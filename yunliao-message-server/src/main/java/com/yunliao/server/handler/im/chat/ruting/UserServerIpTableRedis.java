package com.yunliao.server.handler.im.chat.ruting;

import com.yunliao.server.util.redis.RedisUtil;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class UserServerIpTableRedis {
    public static String getServerIpByUserId(String userKey){
       return RedisUtil.getString(userKey);
    }
}

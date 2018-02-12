package com.yunliao.admin.util.redis;

import com.yunliao.admin.util.ConfigUtil;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;


public class RedisSupport {
    protected static JedisPool pool = new JedisPool(new JedisPoolConfig(), ConfigUtil.getProperty("yunliao.admin.redis.ip"), Integer.valueOf(ConfigUtil.getProperty("yunliao.admin.redis.port")));
    // 3 month 60 * 60 * 24 * 30 * 3
    protected final static int defaultExpireSecond = 7776000;
    public static boolean isAlive = true;

    protected static Queue<String> needDelQueue = new LinkedTransferQueue();
    protected static Queue<String> needDelPatternsQueue = new LinkedTransferQueue();
    protected static RedisService redisService;
    static {
        try {
            RedisService redisServiceTemp = new RedisServiceImpl();
            InvocationHandler redisUtilProxy = new RedisUtilProxy(redisServiceTemp);
            redisService = (RedisService) Proxy.newProxyInstance(redisUtilProxy.getClass().getClassLoader(),
                    redisServiceTemp.getClass().getInterfaces(), redisUtilProxy);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}

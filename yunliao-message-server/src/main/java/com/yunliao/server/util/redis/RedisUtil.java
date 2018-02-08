package com.yunliao.server.util.redis;

import java.io.Serializable;
import java.net.InetAddress;

public class RedisUtil extends RedisSupport {


    public static String setString(String key, String value, Integer seconds) {
        return redisService.setString(key, value, seconds);
    }

    public static String setString(String key, String value) {
        return redisService.setString(key, value, null);
    }


    public static String getString(String key) {
        return redisService.getString(key);
    }


    public static String setObject(String key, Serializable object, Integer seconds) {
        return redisService.setObject(key, object, seconds);
    }

    public static Long delByKey(String key) {
        return redisService.delByKey(key);
    }


    public static Long delByKeys(String keyPattern) {
        return new RedisServiceImpl().delByKeys(keyPattern);
    }

    public static Object getObject(String key) {
        return redisService.getObject(key);
    }

    public static void main(String[] args) throws Exception {
        RedisUtil.setString("1", "22222", 70000);
        System.out.println(RedisUtil.getString("1"));
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }
}

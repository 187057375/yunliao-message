package com.yunliao.server.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class RedisServiceImpl implements RedisService {

    public String setString(String key, String value, Integer seconds) {
        Jedis jedis = RedisSupport.pool.getResource();
        try {
            return seconds == null ? jedis.setex(key, RedisSupport.defaultExpireSecond, value) : jedis.setex(key,
                    seconds, value);
        } catch (JedisConnectionException e) {
            RedisSupport.isAlive = false;
            RedisSupport.needDelQueue.add(key);
            return null;
        }
    }

    public String getString(String key) {
        Jedis jedis = RedisSupport.pool.getResource();
        try {
            return jedis.get(key);
        } catch (JedisConnectionException e) {
            RedisSupport.isAlive = false;
            RedisSupport.needDelQueue.add(key);
            return null;
        }
    }

    public String setObject(String key, Serializable object, Integer seconds) {
        Jedis jedis = RedisSupport.pool.getResource();
        try {
            return seconds == null ? jedis.setex(key.getBytes(), RedisSupport.defaultExpireSecond,
                    SerializeUtil.serialize(object)) : jedis.setex(key.getBytes(), seconds,
                    SerializeUtil.serialize(object));
        } catch (JedisConnectionException e) {
            RedisSupport.isAlive = false;
            RedisSupport.needDelQueue.add(key);
            return null;
        }
    }

    public Long delByKey(String key) {
        Jedis jedis = RedisSupport.pool.getResource();
        try {
            Long retLong = jedis.del(key.getBytes());
            retLong += jedis.del(key);
            return retLong;
        } catch (JedisConnectionException e) {
            RedisSupport.isAlive = false;
            RedisSupport.needDelQueue.add(key);
            return null;
        }
    }

    public Long delByKeys(String keyPattern) {
        Jedis jedis = RedisSupport.pool.getResource();
        try {
            Set<String> keySet = jedis.keys(keyPattern);
            Iterator<String> iterator = keySet.iterator();

            String[] keys = new String[keySet.size()];
            byte[][] keysByte = new byte[keySet.size()][];
            for (int i = 0; i < keySet.size(); i++) {
                String string = iterator.next();
                keys[i] = string;
                keysByte[i] = string.getBytes();
            }
            if (keys.length > 0) {
                Long retLong = jedis.del(keys);
                retLong += jedis.del(keysByte);
                return retLong;
            }
            return 0L;
        } catch (JedisConnectionException e) {
            RedisSupport.isAlive = false;
            RedisSupport.needDelPatternsQueue.add(keyPattern);
            return null;
        }
    }

    public Object getObject(String key) {
        Jedis jedis = RedisSupport.pool.getResource();
        try {
            return SerializeUtil.unserialize(jedis.get(key.getBytes()));
        } catch (JedisConnectionException e) {
            RedisSupport.isAlive = false;
            RedisSupport.needDelQueue.add(key);
            return null;
        }
    }
}

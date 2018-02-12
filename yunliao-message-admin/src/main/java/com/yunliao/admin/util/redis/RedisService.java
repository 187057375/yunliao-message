package com.yunliao.admin.util.redis;

import java.io.Serializable;

/**
 * Redis服务类
 *
 * @author sunjianliang
 * @version V1.0 创建时间：2014年11月13日 下午5:01:24
 *          Copyright 2014 by PreTang
 */
public interface RedisService {
    public String setString(String key, String value, Integer seconds);

    public String getString(String key);

    public String setObject(String key, Serializable object, Integer seconds);

    public Long delByKey(String key);

    public Long delByKeys(String keyPattern);

    public Object getObject(String key);
}

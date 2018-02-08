package com.yunliao.server.util.redis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class RedisUtilProxy implements InvocationHandler {
    private Object subject;

    public RedisUtilProxy(Object subject) {
        this.subject = subject;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (RedisSupport.isAlive == false) {
            RedisSupport.needDelQueue.add((String) args[0]);
            return null;
        }

        Object retObj = method.invoke(subject, args);
        return retObj;
    }

}

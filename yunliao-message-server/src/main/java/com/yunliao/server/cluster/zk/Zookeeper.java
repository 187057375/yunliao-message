package com.yunliao.server.cluster.zk;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 * Copyright 2018 by PreTang
 */

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Zookeeper {
    public static String zkConnString = "127.0.0.1:2181";
    public static CuratorFramework client = null;
    static {
        client = CuratorFrameworkFactory.newClient(zkConnString,  new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

}

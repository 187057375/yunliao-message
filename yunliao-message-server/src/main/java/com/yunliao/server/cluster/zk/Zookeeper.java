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
    public static String YUNLIAO_ZK_BASEPAHT="/yunliao";
    public static String YUNLIAO_ZK_PAHT="/yunliao"+"/127.0.0.1"+9000;
    public static CuratorFramework newClient() {
        String connectionString = "127.0.0.1:2181";
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }
}

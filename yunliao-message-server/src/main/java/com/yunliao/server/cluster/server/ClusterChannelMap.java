package com.yunliao.server.cluster.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/6
 *          Copyright 2018 by PreTang
 */
public class ClusterChannelMap {
    public  static ConcurrentHashMap clusterChanelMap = new ConcurrentHashMap<String,Object>();

}

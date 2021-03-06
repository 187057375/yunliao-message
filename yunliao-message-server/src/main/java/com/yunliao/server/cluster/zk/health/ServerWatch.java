package com.yunliao.server.cluster.zk.health;

import com.alibaba.fastjson.JSON;
import com.yunliao.server.cluster.zk.Zookeeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ServerWatch extends Thread{

    public  static  Map serverMap  =  new HashMap<String,ServerMeta>();

    public void run() {
        System.out.println("ServerWatch启动");
        try {
            CuratorFramework client = Zookeeper.newClient();
            client.start();
            PathChildrenCache cache = new PathChildrenCache(client, Zookeeper.YUNLIAO_ZK_BASEPAHT, true);
            cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            cache.getListenable().addListener(new PathChildrenCacheListener() {
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    //System.out.println(event.getType());
                    byte[] data;
                    String dataStr;
                    ServerMeta serverMeta;
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            System.out.println("add " + event.getData().getPath());
                            data = event.getData().getData();
                            dataStr = new String(data);
                            serverMeta = JSON.parseObject(dataStr,ServerMeta.class);
                            serverMap.put( event.getData().getPath(),serverMeta);
                            //TODO
                            //ClusterClient.addClusterServerClient( );
                            break;
                        case CHILD_UPDATED:
                            System.out.println("update " + event.getData().getPath());
                            data = event.getData().getData();
                            dataStr = new String(data);
                            serverMeta = JSON.parseObject(dataStr,ServerMeta.class);
                            serverMap.put(event.getData().getPath(),serverMeta);
                            break;
                        case CHILD_REMOVED:
                            System.out.println("removed " + event.getData().getPath());
                            serverMap.remove(event.getData().getPath());
                            break;
                        default:
                            break;
                    }

                    //System.out.println(new String(event.getData().getData(), "UTF-8"));
                }
            });
            do {
                Set<String> keys = serverMap.keySet();
                Iterator iterator =  keys.iterator();
                while (iterator.hasNext()){
                    String  serverKey =   (String) iterator.next();
                    ServerMeta serverMeta =   (ServerMeta)serverMap.get(serverKey);
                    System.out.println("[Show] Server:"+serverMeta.getIp()+":"+serverMeta.getPort()+" queueSize="+serverMeta.getMessageQueueSize());
                }
                Thread.sleep(5000);
            }while (true);
            //Thread.sleep(Long.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        ServerWatch watch = new ServerWatch();
        watch.start();
    }

}

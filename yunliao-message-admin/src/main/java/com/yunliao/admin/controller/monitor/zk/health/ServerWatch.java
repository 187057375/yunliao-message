package com.yunliao.admin.controller.monitor.zk.health;

import com.alibaba.fastjson.JSON;
import com.yunliao.admin.controller.monitor.websocket.ChannelMap;
import com.yunliao.admin.controller.monitor.websocket.Message;
import com.yunliao.admin.controller.monitor.zk.Zookeeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.log4j.Logger;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ServerWatch extends Thread{

    private static final Logger logger = Logger.getLogger(ServerWatch.class);

    public void run() {
        //System.out.println("ServerWatch启动");
        logger.info("ServerWatch启动.");

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
                            Message messageAdd= new Message();
                            messageAdd.setMsg(serverMeta);
                            ChannelMap.sendMsg(messageAdd);
                            break;
                        case CHILD_UPDATED:
                            System.out.println("update " + event.getData().getPath());
                            data = event.getData().getData();
                            dataStr = new String(data);
                            serverMeta = JSON.parseObject(dataStr,ServerMeta.class);
                            Message messageUpdate = new Message();
                            messageUpdate.setMsg(serverMeta);
                            ChannelMap.sendMsg(messageUpdate);
                            break;
                        case CHILD_REMOVED:
                            System.out.println("removed " + event.getData().getPath());
                            break;
                        default:
                            break;
                    }
                    //System.out.println(new String(event.getData().getData(), "UTF-8"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        ServerWatch watch = new ServerWatch();
        watch.start();
    }

}

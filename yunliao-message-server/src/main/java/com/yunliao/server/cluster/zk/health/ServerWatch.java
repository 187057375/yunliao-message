package com.yunliao.server.cluster.zk.health;

import com.yunliao.server.cluster.zk.Zookeeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ServerWatch {




    public static void main(String[] args) throws Exception {

        PathChildrenCache cache = new PathChildrenCache(Zookeeper.client, "/", true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("add " + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("update " + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("removed " + event.getData().getPath());
                        break;
                    default:
                        break;
                }
                System.out.println(event.getType());
                //System.out.println(new String(event.getData().getData(), "UTF-8"));
            }
        });
        Thread.sleep(Long.MAX_VALUE);
    }

}

package com.yunliao.admin.controller.monitor.zk.health;

import com.alibaba.fastjson.JSON;
import com.yunliao.admin.controller.monitor.zk.Zookeeper;
import org.apache.curator.framework.CuratorFramework;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ServerOperation {

    public static CuratorFramework client = Zookeeper.newClient();

    static {
        client.start();
    }


    public static List<String> list(String path) throws Exception {
        List<String> watched = client.getChildren().watched().forPath(path);
        System.out.println(watched);
        return watched;
    }


    public static ServerMeta getData(String path) throws Exception {
        String dataStr = new String(client.getData().forPath(path), "UTF-8");
        ServerMeta serverMeta = JSON.parseObject(dataStr, ServerMeta.class);
        return serverMeta;
    }

    public static List<ServerMeta> listServerMeta(String path) throws Exception {
        List<ServerMeta> list = new ArrayList<ServerMeta>();
        List<String> watched = client.getChildren().watched().forPath(path);
        for (String serverName : watched) {
            list.add(getData(serverName));
        }
        return list;
    }

}

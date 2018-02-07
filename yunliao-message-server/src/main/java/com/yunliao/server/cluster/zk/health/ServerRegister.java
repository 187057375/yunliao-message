package com.yunliao.server.cluster.zk.health;

import com.yunliao.server.cluster.zk.Zookeeper;

import java.net.InetAddress;
import java.util.List;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ServerRegister {


    public void register() throws Exception {
       /* Map map = new HashMap();
        map.put("host.name", InetAddress.getLocalHost().getCanonicalHostName());
        map.put("java.version", System.getProperty("java.version", "<NA>"));
        map.put("java.vendor", System.getProperty("java.vendor", "<NA>"));
        map.put("java.home", System.getProperty("java.home", "<NA>"));
        map.put("java.class.path", System.getProperty("java.class.path", "<NA>"));
        map.put("java.library.path", System.getProperty("java.library.path", "<NA>"));
        map.put("java.io.tmpdir", System.getProperty("java.io.tmpdir", "<NA>"));
        map.put("java.compiler", System.getProperty("java.compiler", "<NA>"));
        map.put("os.name", System.getProperty("os.name", "<NA>"));
        map.put("os.arch", System.getProperty("os.arch", "<NA>"));
        map.put("os.version", System.getProperty("os.version", "<NA>"));
        map.put("user.name", System.getProperty("user.name", "<NA>"));
        map.put("user.home", System.getProperty("user.home", "<NA>"));
        map.put("user.dir", System.getProperty("user.dir", "<NA>"));
        Set<String> keys = map.keySet();
        Iterator iterator =  keys.iterator();
        while (iterator.hasNext()){
          String key =   (String) iterator.next();
            System.out.println(key +"="+map.get(key));
        }
        */
        /*if (Zookeeper.client.checkExists().forPath("/yunliaomessage/server") != null) {
            System.out.println("已经存在");
        }*/
        Zookeeper.client.create().creatingParentsIfNeeded().forPath("/yunliaomessage/ggggg",  InetAddress.getLocalHost().getCanonicalHostName().getBytes());
       // Zookeeper.client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/yunliaomessage/server",  InetAddress.getLocalHost().getCanonicalHostName().getBytes());
       // Zookeeper.client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/yunliaomessage/im", "123456".getBytes());

    }
    public void list() throws Exception{
        List<String> watched = Zookeeper.client.getChildren().watched().forPath("/yunliaomessage");
        System.out.println(watched);
    }
    public void data() throws Exception{
        System.out.println(new String(Zookeeper.client.getData().forPath("/yunliaomessage/ggggg"),"UTF-8"));
    }
    public static void main(String[] args) throws Exception {
        ServerRegister serverRegister = new ServerRegister();
        //serverRegister.register();
        //serverRegister.list();
        serverRegister.data();
    }
}

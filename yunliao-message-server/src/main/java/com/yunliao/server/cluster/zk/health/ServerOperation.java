package com.yunliao.server.cluster.zk.health;

import com.alibaba.fastjson.JSON;
import com.yunliao.server.cluster.zk.Zookeeper;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetAddress;
import java.util.List;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ServerOperation {

    public  static  CuratorFramework client = Zookeeper.newClient();
    static {
        client.start();
    }


    public  static  void register(String path,String ip,String hostName) throws Exception {
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


        ServerMeta serverMeta  =  new ServerMeta();
        serverMeta.setIp(ip);
        serverMeta.setHostName(hostName);
        String data = JSON.toJSONString(serverMeta);
        client.create().creatingParentsIfNeeded().forPath(path,data.getBytes("UTF-8"));

    }


    public  static  void list(String path) throws Exception{
        //CuratorFramework client = Zookeeper.newClient();
        //client.start();
        List<String> watched =  client.getChildren().watched().forPath(path);
        System.out.println(watched);
    }


    public static  ServerMeta getData(String path) throws Exception{
        String dataStr = new String(client.getData().forPath(path),"UTF-8");
        ServerMeta serverMeta = JSON.parseObject(dataStr,ServerMeta.class);
        return serverMeta;
    }

    public static  void setData( ServerMeta serverMeta,String path) throws Exception{
        String data = JSON.toJSONString(serverMeta);
        client.setData().forPath(path,data.getBytes("UTF-8"));
    }


    public static void main(String[] args) throws Exception {
        //ServerOperation.register(Zookeeper.YUNLIAO_ZK_PAHT,"127.0.0.1",9000, InetAddress.getLocalHost().getCanonicalHostName());
        //ServerOperation.list(Zookeeper.YUNLIAO_ZK_BASEPAHT );
        //ServerMeta serverMeta = ServerOperation.getData(Zookeeper.YUNLIAO_ZK_PAHT);
        //System.out.println(JSON.toJSONString(serverMeta));

        ServerOperation.register("/yunliao/127.0.0.2:9000","127.0.0.2", InetAddress.getLocalHost().getCanonicalHostName());

    }
}

package com.yunliao.server.cluster.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.data.Stat;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/7
 *          Copyright 2018 by PreTang
 */
public class ZKDemo
{
    private static final String PATH = "/crud";

    public static void main(String[] args) {
        CuratorFramework client = Zookeeper.newClient();
        client.start();
        try {
            client.create().forPath(PATH, "I love messi".getBytes());

            byte[] bs = client.getData().forPath(PATH);
            System.out.println("新建的节点，data为:" + new String(bs));

            client.setData().forPath(PATH, "I love football".getBytes());

            // 由于是在background模式下获取的data，此时的bs可能为null
            byte[] bs2 = client.getData().watched().inBackground().forPath(PATH);
            System.out.println("修改后的data为" + new String(bs2 != null ? bs2 : new byte[0]));

            client.delete().forPath(PATH);
            Stat stat = client.checkExists().forPath(PATH);
            // Stat就是对zonde所有属性的一个映射， stat=null表示节点不存在！
            System.out.println(stat);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }

}

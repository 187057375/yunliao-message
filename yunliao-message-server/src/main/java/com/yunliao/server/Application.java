package com.yunliao.server;


import com.yunliao.server.cluster.server.TcpClusterServer;
import com.yunliao.server.cluster.zk.Zookeeper;
import com.yunliao.server.cluster.zk.health.ServerOperation;
import com.yunliao.server.cluster.zk.health.ServerStatusReportThread;
import com.yunliao.server.cluster.zk.health.ServerWatch;
import com.yunliao.server.handler.MessageQueueProcessServer;
import com.yunliao.server.listen.tcp.TcpServer;
import com.yunliao.server.listen.websocket.WebsocketServer;
import com.yunliao.server.util.ConfigUtil;
import org.apache.log4j.Logger;

import java.net.InetAddress;


public class Application  {

    private static final Logger logger = Logger.getLogger(Application.class);
    public static  String serverIp;
    public static  int clusterPort=9001;//集群服务器通信端口

    public static void start() throws Exception{
        //本机服务器ip
        Application.serverIp  = InetAddress.getLocalHost().getHostAddress();

        //1启动TCP监听服务
        try{
            TcpServer.start(Integer.valueOf(ConfigUtil.getProperty("yunliao.server.port.tcp")));
        } catch (Exception e){
            logger.error("启动TCP服务失败", e);
        }
        //2启动websocket监听服务
        try{
            WebsocketServer.start(Integer.valueOf(ConfigUtil.getProperty("yunliao.server.port.websocket")));
        } catch (Exception e){
            logger.error("启动websocket服务失败", e);
        }
        //3启动消息解析服务队列线程
        try{
            MessageQueueProcessServer.start(1);
        } catch (Exception e){
            logger.error("启动消息解析服务失败", e);
        }

        //集群模式
        try {
            String supportCluster = ConfigUtil.getProperty("yunliao.cluster");
            if("true".equals(supportCluster)){
                //启动集群通信端口
                try{
                    TcpClusterServer.start(clusterPort);
                } catch (Exception e){
                    logger.error("启动集群通信端口", e);
                }
                //启动zookeeper监听
                ServerWatch watch = new ServerWatch();
                watch.start();
                //向zookeeper注册
                String ip = InetAddress.getLocalHost().getCanonicalHostName();
                ServerOperation.register(Zookeeper.YUNLIAO_ZK_BASEPAHT+"/"+serverIp,serverIp, serverIp);

                //向zk报告状态
                ServerStatusReportThread serverStatusReportThread = new ServerStatusReportThread();
                serverStatusReportThread.start();
            }
        } catch (Exception e) {
            logger.error("集群启动失败", e);
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception{
        Application.start();
    }


}

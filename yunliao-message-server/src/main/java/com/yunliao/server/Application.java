package com.yunliao.server;


import com.yunliao.server.handler.MessageQueueProcessServer;
import com.yunliao.server.listen.tcp.TcpServer;
import com.yunliao.server.listen.websocket.WebsocketServer;
import org.apache.log4j.Logger;


public class Application {

    private static final Logger logger = Logger.getLogger(Application.class);

    public static void start(){
        //启动服务
        startServer();

    }

    private static void startServer(){
        //启动TCP监听服务
        try{
            TcpServer.start(9000);
        } catch (Exception e){
            logger.error("启动TCP服务失败", e);
        }
        //启动websocket监听服务
        try{
            WebsocketServer.start(8999);
        } catch (Exception e){
            logger.error("启动websocket服务失败", e);
        }
        //启动消息解析服务
        try{
            MessageQueueProcessServer.start(1);
        } catch (Exception e){
            logger.error("启动消息解析服务失败", e);
        }
    }

    public static void main(String[] args) {
        Application.start();
    }


}

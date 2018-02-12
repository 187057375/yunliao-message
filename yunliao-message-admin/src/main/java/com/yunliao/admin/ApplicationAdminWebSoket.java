package com.yunliao.admin;

import com.yunliao.admin.controller.monitor.websocket.WebsocketServer;
import com.yunliao.admin.controller.monitor.zk.health.ServerWatch;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/5
 *          Copyright 2018 by PreTang
 */
//@EnableEurekaServer
@SpringBootApplication
public class ApplicationAdminWebSoket {


    public static void main(String[] args) throws Exception {

        //new SpringApplicationBuilder(ApplicationAdminWebSoket.class).web(true).run(args);
        //启动websocket，为监控界面提供长连接
        WebsocketServer.start(9999);
        //启动zookeeper监听
        ServerWatch watch = new ServerWatch();
        watch.start();
    }
}

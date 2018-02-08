package com.yunliao.server.cluster.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

public class TcpClusterServer {
    private static final Logger logger = Logger.getLogger(TcpClusterServer.class);



    public static void start(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);// 不延迟,即刻发送消息
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);// 长连接
            bootstrap.childHandler(new TcpClusterInitializer());

            bootstrap.bind(port).sync();
            logger.info("TcpClusterServer启动成功,端口:" + port + ".");
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}

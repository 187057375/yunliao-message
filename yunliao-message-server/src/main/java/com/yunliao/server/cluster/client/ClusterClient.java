package com.yunliao.server.cluster.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;


public final class ClusterClient {

    private static ConcurrentHashMap clientChanelMap = new ConcurrentHashMap<String, Channel>();

    public static synchronized Channel getClusterClient(String host, int port) throws Exception {

        Channel channel = (Channel) ClusterClient.clientChanelMap.get(host + port);
        if(channel == null){
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            public void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();
                                p.addLast(new ClusterClientHandler());
                            }
                        });
                ChannelFuture f = b.connect(host, port).sync();
                f.channel().closeFuture().sync();
                channel = f.channel();
                ClusterClient.clientChanelMap.put(host + port, f.channel());
            } finally {
                group.shutdownGracefully();
            }
        }
        return  channel;

    }
}

package com.yunliao.server.cluster.client;

import com.alibaba.fastjson.JSON;
import com.yunliao.server.cluster.transport.SendClusterMessage;
import com.yunliao.server.cluster.transport.message.ClusterMessage;
import com.yunliao.server.cluster.zk.health.ServerMeta;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;


public final class ClusterClient {

    private static ConcurrentHashMap clientChanelMap = new ConcurrentHashMap<String, Channel>();

    /**
     * 根据ip和端口直接向其他服务器发送消息
     * @param host
     * @param port
     * @param clusterMessage
     * @throws Exception
     */
    public static void send(String host, int port, ClusterMessage clusterMessage) throws Exception {

        Channel channel = (Channel) ClusterClient.clientChanelMap.get(host + ":" + port);
        if (channel == null) {
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
                channel = f.channel();
                ClusterClient.clientChanelMap.put(host + ":" + port, channel);
                String msg = JSON.toJSONString(clusterMessage);
                ByteBuf buf = f.channel().alloc().buffer();
                buf.writeBytes(msg.getBytes("UTF-8"));
                channel.writeAndFlush(buf);

                //ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes("UTF-8"));
                //channel.writeAndFlush(buf);
                //f.channel().closeFuture().sync();
            } finally {
                //group.shutdownGracefully();
            }
        } else {

            if (channel.isActive()) {
                String msg = JSON.toJSONString(clusterMessage);
                ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes("UTF-8"));
                channel.writeAndFlush(buf);
            } else {
                channel.closeFuture();
                channel = null;
                ClusterClient.clientChanelMap.remove(host + ":" + port);
                SendClusterMessage.send(clusterMessage);
            }
        }
    }

    /**
     * 集群环境下，zk监听一个新的服务器上线，创建一个客户端和他连接，从而建立服务器之间的通信
     * @param serverMeta
     * @throws Exception
     */
    public static void addClusterServerClient(ServerMeta serverMeta) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ClusterClientHandler());
                    }
                });
        ChannelFuture f = b.connect(serverMeta.getIp(), serverMeta.getPort()).sync();
        Channel channel = f.channel();
        ClusterClient.clientChanelMap.put(serverMeta.getIp() + ":" + serverMeta.getPort(), channel);
    }
}

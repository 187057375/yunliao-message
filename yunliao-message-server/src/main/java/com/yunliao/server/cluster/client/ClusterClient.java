package com.yunliao.server.cluster.client;

import com.alibaba.fastjson.JSON;
import com.yunliao.server.cluster.transport.message.ClusterMessage;
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

    public static void  send(String host, int port,ClusterMessage clusterMessage) throws Exception {

        Channel channel = (Channel) ClusterClient.clientChanelMap.get(host+":" + port);
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
                channel = f.channel();
                ClusterClient.clientChanelMap.put(host +":"+ port, channel);
                String msg = JSON.toJSONString(clusterMessage);
                ByteBuf buf = f.channel().alloc().buffer();
                buf.writeBytes(msg.getBytes("UTF-8"));
                channel.writeAndFlush(buf);

                //ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes("UTF-8"));
                //channel.writeAndFlush(buf);
                f.channel().closeFuture().sync();
            } finally {
                group.shutdownGracefully();
            }
        }else{
            String msg = JSON.toJSONString(clusterMessage);
            ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes("UTF-8"));
            channel.writeAndFlush(buf);
        }
    }
}

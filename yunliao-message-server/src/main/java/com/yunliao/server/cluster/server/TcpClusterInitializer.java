package com.yunliao.server.cluster.server;

import com.yunliao.server.listen.ChannelMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;

public class TcpClusterInitializer extends ChannelInitializer<SocketChannel> {

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        ClusterChannelMap.clusterChanelMap.put(ctx.channel().id().toString(),ctx.channel());
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast("encoder", new StringEncoder());
        p.addLast("business", new TcpClusterHandler());
    }
    private void remove(ChannelHandlerContext ctx) {
        ChannelMap.chanelMap.remove(ctx.channel().id().toString());
    }
}

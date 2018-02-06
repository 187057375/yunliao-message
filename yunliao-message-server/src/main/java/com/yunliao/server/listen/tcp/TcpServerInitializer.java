package com.yunliao.server.listen.tcp;

import com.yunliao.server.listen.ChannelMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;

public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        ChannelMap.chanelMap.put(ChannelMap.TCP+ctx.channel().id().toString(),ctx.channel());
        //System.out.println("chanel add： "+ ctx.channel().id());

    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast("encoder", new StringEncoder());
        p.addLast("business", new TcpServerHandler());
    }
    private void remove(ChannelHandlerContext ctx) {
        ChannelMap.chanelMap.remove(ctx.channel().id().toString());
    }
}

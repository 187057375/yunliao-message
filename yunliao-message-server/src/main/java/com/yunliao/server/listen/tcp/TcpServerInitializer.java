package com.yunliao.server.listen.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ConcurrentHashMap;

public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {
    public  static ConcurrentHashMap chanelMap = new ConcurrentHashMap<String,Object>();

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        chanelMap.put(ctx.channel().id().toString(),ctx.channel());
        //System.out.println("chanel add： "+ ctx.channel().id());

    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast("encoder", new StringEncoder());
        p.addLast("business", new TcpServerHandler());
    }
    private void remove(ChannelHandlerContext ctx) {
        chanelMap.remove(ctx.channel().id().toString());
    }
}

package com.yunliao.admin.controller.monitor.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        ChannelMap.chanelMap.put(ctx.channel().id().toString(),ctx.channel());
        System.out.println("chanel add： "+ ctx.channel().id());
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast("channelIdle", new IdleStateHandler(120, 30, 20, TimeUnit.SECONDS));
        p.addLast("doHeartBeat", new WebSocketHeartBeatHandler());
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1048576));
        p.addLast(new WebSocketServerCompressionHandler());
        p.addLast("business", new WebsocketServerHandler());
    }
    private void remove(ChannelHandlerContext ctx) {
        ChannelMap.chanelMap.remove(ctx.channel().id().toString());
    }
}

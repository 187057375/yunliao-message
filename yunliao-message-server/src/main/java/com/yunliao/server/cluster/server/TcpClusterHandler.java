package com.yunliao.server.cluster.server;

import com.yunliao.server.listen.ChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;


public class TcpClusterHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TcpClusterHandler.class);


    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        try {
            ByteBuf buf = (ByteBuf) obj;
            byte[] message = new byte[buf.readableBytes()];
            buf.readBytes(message,ChannelMap.channelIdLenth,buf.readableBytes());
            System.out.println(new String(message));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

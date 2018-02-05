package com.yunliao.server.listen.tcp;

import com.yunliao.server.handler.MessageQueue;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;


public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TcpServerHandler.class);


    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        //发布到队列
        try {
            //System.out.println(ctx.channel().id().toString());

            ByteBuf buf = (ByteBuf) obj;
            //在原始数据流加入当前chanelId，有没有更好的设计方式？
            byte[] message = new byte[buf.readableBytes()+8];
            byte[] chanelId = ctx.channel().id().toString().getBytes("UTF-8");
            System.arraycopy(chanelId, 0, message, 0, chanelId.length);
            buf.readBytes(message,8,buf.readableBytes());

            //String mystr = new String(message);
            //System.out.println("server received data :" + mystr);
            //ctx.channel().writeAndFlush(mystr);//写回数据，

            MessageQueue.push(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

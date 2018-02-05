package com.yunliao.server.handler.im.chat.handler;

import com.yunliao.server.handler.Message;
import com.yunliao.server.listen.tcp.TcpServerInitializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/2
 *          Copyright 2018 by PreTang
 */
public class RuterHandlerLocal {

    /**
     * 路由层面处理消息
     * @param message
     */
    public static void  process(Message message) throws Exception{
        String  chanelId =   message.getToChanelId();
        Channel channel =  (Channel)TcpServerInitializer.chanelMap.get(chanelId);
        ByteBuf buf = channel.alloc().buffer();
        buf.writeBytes(message.getBody());
        channel.writeAndFlush(buf);
        message = null;
    }
}

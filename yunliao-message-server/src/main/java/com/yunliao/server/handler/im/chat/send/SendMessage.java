package com.yunliao.server.handler.im.chat.send;

import com.yunliao.server.handler.Message;
import com.yunliao.server.listen.ChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/2
 *          Copyright 2018 by PreTang
 */
public class SendMessage {

    /**
     * 路由层面处理消息
     *
     * @param message
     */
    public static void send(Message message) throws Exception {
        String channelId = message.getToChanelId();
        String channelType = channelId.substring(0, 1);
        Channel channel = (Channel) ChannelMap.chanelMap.get(channelId);

        //ByteBuf buf = channel.alloc().buffer();
        //buf.writeBytes(message.getBody())

        ByteBuf buf = Unpooled.copiedBuffer(message.getBody());
        if (channelType.equals(ChannelMap.TCP)) {
            channel.writeAndFlush(buf);
        }
        if (channelType.equals(ChannelMap.WEBSOCKET)) {
            TextWebSocketFrame webSocketFrame = new TextWebSocketFrame(buf);
            channel.writeAndFlush(webSocketFrame);
        }
        message = null;
    }
}

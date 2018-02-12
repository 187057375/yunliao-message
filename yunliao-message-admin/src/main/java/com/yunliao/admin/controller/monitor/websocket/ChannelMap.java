package com.yunliao.admin.controller.monitor.websocket;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/6
 *          Copyright 2018 by PreTang
 */
public class ChannelMap {

    public  static ConcurrentHashMap chanelMap = new ConcurrentHashMap<String,Object>();

    public  static void sendMsg(Message message) throws Exception{
        Iterator<Map.Entry<String, Object>> iterator = chanelMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Channel channel = (Channel)entry.getValue();
            ByteBuf buf = Unpooled.copiedBuffer(JSON.toJSONString(message).getBytes("UTF-8"));
            TextWebSocketFrame webSocketFrame = new TextWebSocketFrame(buf);
            channel.writeAndFlush(webSocketFrame);
        }
    }

}

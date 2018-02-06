package com.yunliao.server.listen.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * 描述:
 * 创建人: SXF
 * 创建时间: 2017/1/23 9:19.
 * Version: 1.0.0
 * 修改人:
 * 修改时间:
 */
public class DefaultWebSocketMessageHandler {

    // 客户端响应心跳字符串
    private static final String CLIENT_ACK_HEARTBEAT = "ACK_HEARTBEAT";


    public void hand(ChannelHandlerContext ctx, WebSocketFrame frame) {
        String clientMessage = ((TextWebSocketFrame) frame).text();
        if (CLIENT_ACK_HEARTBEAT.equals(clientMessage)){// 心跳检测,不处理
            return;
        }
    }
}

package com.yunliao.server.listen.websocket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 创建人: SXF
 * 创建时间: 2017/1/22 14:46.
 * Version: 1.0.0
 * 修改人:
 * 修改时间:
 */
public class WebSocketSubscribeHandler{

    // websocket 服务的 uri
    private static final String WEBSOCKET_PATH = "/websocket";

    public WebSocketServerHandshaker hand(ChannelHandlerContext ctx, FullHttpRequest req) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri());
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        Map<String, String> paramMap = new HashMap<String,String>();
        Iterator<Map.Entry<String, List<String>>> iterator = parameters.entrySet().iterator();
        for (;iterator.hasNext();){
            Map.Entry<String, List<String>> next = iterator.next();
            paramMap.put(next.getKey(), next.getValue().get(0));
        }

        if (parameters.size() == 0 || null == paramMap.get("topic")) {
            System.err.printf("topic" + "参数不可缺省!");
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND));
            return null;
        }

        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, true);
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            ChannelFuture channelFuture = handshaker.handshake(ctx.channel(), req);


            ctx.fireChannelRead(req.retain());
        }

        String topic = paramMap.get("topic");
        return handshaker;
    }

    private static String getWebSocketLocation(FullHttpRequest req) {
        String location = req.headers().get(HttpHeaderNames.HOST) + WEBSOCKET_PATH;
        return "ws://" + location;
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ctx.channel().writeAndFlush(res);
    }
}

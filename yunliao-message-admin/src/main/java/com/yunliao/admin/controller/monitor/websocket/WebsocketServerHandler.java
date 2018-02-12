package com.yunliao.admin.controller.monitor.websocket;

import com.alibaba.fastjson.JSON;
import com.yunliao.admin.controller.monitor.zk.Zookeeper;
import com.yunliao.admin.controller.monitor.zk.health.ServerMeta;
import com.yunliao.admin.controller.monitor.zk.health.ServerOperation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.util.List;

public class WebsocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(WebsocketServerHandler.class);


    //websocket握手连接
    private WebSocketServerHandshaker handshaker;

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    /**
     * 接收消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {//握手协议
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {//处理数据
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * 处理http请求，握手协议
     * @param ctx
     * @param req
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        logger.info("--------http-------------");
        logger.info(req.toString());
        // Handle a bad request.
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.method() != HttpMethod.GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
            return;
        }

        /*if ("/favicon.ico".equals(req.uri()) || ("/".equals(req.uri()))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND));
            return;
        }*/

        //Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(req.headers().get(HttpHeaderNames.HOST), null, true);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            ChannelFuture channelFuture = handshaker.handshake(ctx.channel(), req);
            ctx.fireChannelRead(req.retain());
        }

    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception{
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }

        String clientMessage = ((TextWebSocketFrame) frame).text();
        //logger.info("--------websocket frame-------------");
        if (CLIENT_ACK_HEARTBEAT.equals(clientMessage)){// 心跳检测,不处理
            return;
        }
        logger.info(clientMessage);
        if("list".equals(clientMessage)){
            List<String> serverList = ServerOperation.list(Zookeeper.YUNLIAO_ZK_BASEPAHT);
            for (String serverPath:serverList){
                ServerMeta serverMeta = ServerOperation.getData(Zookeeper.YUNLIAO_ZK_BASEPAHT+"/"+serverPath);
                Message message = new Message();
                message.setMsg(serverMeta);
                ByteBuf buf = Unpooled.copiedBuffer(JSON.toJSONString(message).getBytes("UTF-8"));
                TextWebSocketFrame webSocketFrame = new TextWebSocketFrame(buf);
                ctx.channel().writeAndFlush(buf);
            }
        }


    }
    // 客户端响应心跳字符串
    private static final String CLIENT_ACK_HEARTBEAT = "ACK_HEARTBEAT";


    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ctx.channel().writeAndFlush(res);
    }


}

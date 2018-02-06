package com.yunliao.server.listen.websocket;

import com.yunliao.server.handler.MessageQueue;
import com.yunliao.server.listen.ChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

public class WebsocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(WebsocketServerHandler.class);


    //websocket握手连接
    private WebSocketServerHandshaker handshaker;

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        removeChannel(ctx);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        removeChannel(ctx);
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
        logger.info("--------websocket frame-------------");
        logger.info(clientMessage);

        if (CLIENT_ACK_HEARTBEAT.equals(clientMessage)){// 心跳检测,不处理
            return;
        }


        //在原始数据流加入当前chanelId，有没有更好的设计方式？
        byte[] buf =clientMessage.getBytes("UTF-8");//原始数据
        byte[] message = new byte[buf.length+ ChannelMap.channelIdLenth];//队列的消息
        byte[] chanelId = (ChannelMap.WEBSOCKET+ctx.channel().id()).getBytes("UTF-8");
        System.arraycopy(chanelId, 0, message, 0, chanelId.length);
        System.arraycopy(buf, 0, message, ChannelMap.channelIdLenth, buf.length);
        //加入消息队列
        MessageQueue.push(message);

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

    private void removeChannel(ChannelHandlerContext ctx){
    }
}

package com.yunliao.admin.controller.monitor.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;


public class WebSocketHeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) { // 长时间未响应连接,关闭
                ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {// 不做任何处理
            } else if (event.state().equals(IdleState.ALL_IDLE)) {// 心跳检测
                ByteBuf buf = Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.UTF_8);
                TextWebSocketFrame webSocketFrame = new TextWebSocketFrame(buf);
                ctx.channel().writeAndFlush(webSocketFrame);
            }
        }
    }
}

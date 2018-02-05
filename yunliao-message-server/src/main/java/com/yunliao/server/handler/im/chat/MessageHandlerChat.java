package com.yunliao.server.handler.im.chat;

import com.yunliao.server.handler.Message;
import com.yunliao.server.handler.MessageHandler;
import com.yunliao.server.handler.im.chat.handler.RuterHandler;
import com.yunliao.server.handler.im.chat.ruting.RuterTable;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class MessageHandlerChat extends MessageHandler{
    /**
     * 用户聊天
     * @param message
     * @throws Exception
     */
    public void process(Message message) throws Exception {
        RuterTable.searchAndSetRuter(message);
        RuterHandler.process(message);
    }
}

package com.yunliao.server.handler;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public abstract class   MessageHandler {

    /**
     * 消息处理
     * @param message
     * @throws Exception
     */
    public abstract  void process(Message message) throws Exception;
}

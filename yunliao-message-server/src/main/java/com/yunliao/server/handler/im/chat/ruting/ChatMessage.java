package com.yunliao.server.handler.im.chat.ruting;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/5
 *          Copyright 2018 by PreTang
 */
public class ChatMessage {

    private  String toId;
    private  String msg;
    private  String fromId;
    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}

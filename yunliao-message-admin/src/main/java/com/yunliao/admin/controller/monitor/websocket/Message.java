package com.yunliao.admin.controller.monitor.websocket;

public class Message {


    private  Object msg;
    private  String msgType;

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}

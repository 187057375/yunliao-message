package com.yunliao.server.handler.im.chat.handler;

import com.yunliao.server.handler.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/2
 *          Copyright 2018 by PreTang
 */
public class RuterHandler {

    /**
     * 路由层面处理消息
     * @param message
     */
    public static void  process(Message message) throws Exception{
        if(message.isLocalServer()){
            RuterHandlerLocal.process(message);
        }else{
            throw  new Exception("just now not suppourt cluster");
        }
    }

    public static void main(String[] args) {
        Map test = new HashMap();
        test.put(1,"2222");
        System.out.println(test.get(1));
    }
}

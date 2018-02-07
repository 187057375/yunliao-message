package com.yunliao.server.handler;


/**
 * 异步处理队列上的消息
 *
 * @author peter
 * @version V1.0 创建时间：2016年6月8日 上午10:38:35
 * Copyright 2016 by PreTang
 */

import com.yunliao.server.handler.im.account.MessageHandlerRegister;
import com.yunliao.server.handler.im.chat.MessageHandlerChat;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

public class MessageQueueProcessThread implements Runnable {

    private static final Logger logger = Logger.getLogger(MessageQueueProcessThread.class);

    public boolean run = true;


    public void run() {
        logger.info("MessageQueueProcessThread 启动:"+Thread.currentThread().getId());
        do {
            try {
                logger.info("处理数据");
                byte[] in = (byte[]) MessageQueue.poll();
                if(in == null){
                    Thread.sleep(100);
                }else{
                    logger.info("读取到消息并进行处理:"+ Hex.encodeHexString(in));
                    Message message = MessageDecode.decode(in);
                    if(message!=null){//合法的,解码成功的消息
                        if(message.getType()==MessageProtocolHeader.REGISTER){
                            MessageHandlerRegister register = new MessageHandlerRegister();
                            register.process(message);
                        }
                        if(message.getType()==MessageProtocolHeader.PERSON_MESSAGE){
                            MessageHandlerChat chat = new MessageHandlerChat();
                            chat.process(message);
                        }
                        if(message.getType()==MessageProtocolHeader.CUSTOM){
                            throw new Exception("暂时还不支持");
                        }
                    }else{

                        logger.info("非法消息:"+ Hex.encodeHexString(in));
                        logger.info("       :"+ new String(in));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (run);

    }

    /**
     * 停止线程
     *
     * @author peter 2016年5月30日
     */
    public void stop() {
        run = false;
    }

}



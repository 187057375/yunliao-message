package com.yunliao.server.handler;

import com.yunliao.server.listen.ChannelMap;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/2
 *          Copyright 2018 by PreTang
 */
public class MessageDecode {
    private static final Logger logger = Logger.getLogger(MessageDecode.class);

    public static Message decode(byte[] in) throws Exception {
        if (in.length < MessageProtocolHeader.HEADER_LENGTH+8) {
            logger.info("数据包长度小于协议头长度");
            return null;
        }
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(in));


        byte[] chanelIdByte =new byte[ChannelMap.channelIdLenth];
        input.read(chanelIdByte,0,ChannelMap.channelIdLenth);
        String chanelId =  new String(chanelIdByte);


        if (input.readByte() != MessageProtocolHeader.MAGIC) {
            logger.info("Magic不一致");
            return null;
        }

        // 开始解码
        byte sign = input.readByte();
        byte type = input.readByte();
        byte status = input.readByte();
        int bodyLength = input.readInt();     // 确认消息体长度
        if (bodyLength != (in.length-(ChannelMap.channelIdLenth+8))) {
            logger.info("消息体长度不一致");
            return null;
        }

        byte[] bodyBytes = new byte[bodyLength];
        //System.arraycopy(in, 8, bodyBytes, 0, bodyLength);
        input.read(bodyBytes);
        input.close();
        input = null;


        Message message = new Message();
        message.setFromChanel(chanelId);
        message.setSign(sign);
        message.setType(type);
        message.setStatus(status);
        message.setBody(bodyBytes);
        return message;

    }

    public static void main(String[] args) throws Exception {
        byte[] ddd = {0x00, 0x00, 0x02, 0x52, 0x53, 0x54, 0x55, 0x56};
        DataInputStream inpunt = new DataInputStream(new ByteArrayInputStream(ddd));
        System.out.println(inpunt.readByte());

        String hv = Integer.toHexString(inpunt.readInt());
        System.out.println(hv);
    }
}

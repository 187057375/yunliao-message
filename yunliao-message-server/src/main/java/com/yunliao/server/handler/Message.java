package com.yunliao.server.handler;

import java.io.Serializable;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/2
 *          Copyright 2018 by PreTang
 */
public class Message   implements Serializable {

        // 消息标志
        private byte sign;
        // 消息类型
        private byte type;
        // 响应状态
        private byte status;
        //消息体
        private byte[] body;
        //此消息接收通道
        private String fromChanel;
        private String fromId;
        //此消转发通道
        private String toId;
        private String toChanelId;


        public byte getSign() {
                return sign;
        }

        public void setSign(byte sign) {
                this.sign = sign;
        }

        public byte getType() {
                return type;
        }

        public void setType(byte type) {
                this.type = type;
        }

        public byte getStatus() {
                return status;
        }

        public void setStatus(byte status) {
                this.status = status;
        }

        public byte[] getBody() {
                return body;
        }

        public void setBody(byte[] body) {
                this.body = body;
        }

        public String getFromChanel() {
                return fromChanel;
        }

        public void setFromChanel(String fromChanel) {
                this.fromChanel = fromChanel;
        }

        public String getFromId() {
                return fromId;
        }

        public void setFromId(String fromId) {
                this.fromId = fromId;
        }

        public String getToId() {
                return toId;
        }

        public void setToId(String toId) {
                this.toId = toId;
        }

        public String getToChanelId() {
                return toChanelId;
        }

        public void setToChanelId(String toChanelId) {
                this.toChanelId = toChanelId;
        }

}

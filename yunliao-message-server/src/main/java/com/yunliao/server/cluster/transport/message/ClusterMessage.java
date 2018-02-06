package com.yunliao.server.cluster.transport.message;

import com.yunliao.server.handler.Message;

/**
 * 服务间通讯-消息基类
 * @author peter
 * @version V1.0 创建时间：18/2/2
 *          Copyright 2018 by PreTang
 */
public   class ClusterMessage  {
        public static final int TYPE_CHAT     = 10;    //聊天消息
        public static final int TYPE_RUTE     = 20;    //
        //消息类型
        private int  type;
        //消息接收人
        private String toId;
        //集群服务目标IP
        private String toIp;
        //集群服务目标端口
        private int toPort;
        //消息体
        private Message message;

        public int getType() {
                return type;
        }

        public void setType(int type) {
                this.type = type;
        }

        public String getToIp() {
                return toIp;
        }

        public void setToIp(String toIp) {
                this.toIp = toIp;
        }

        public int getToPort() {
                return toPort;
        }

        public void setToPort(int toPort) {
                this.toPort = toPort;
        }

        public Message getMessage() {
                return message;
        }

        public void setMessage(Message message) {
                this.message = message;
        }

        public String getToId() {
                return toId;
        }

        public void setToId(String toId) {
                this.toId = toId;
        }
}

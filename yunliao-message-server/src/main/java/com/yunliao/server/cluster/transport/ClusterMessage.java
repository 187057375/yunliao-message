package com.yunliao.server.cluster.transport;

import java.io.Serializable;

/**
 * 服务间通讯-消息基类
 * @author peter
 * @version V1.0 创建时间：18/2/2
 *          Copyright 2018 by PreTang
 */
public  abstract  class ClusterMessage implements Serializable {

        //消息类型
        private String  type;
        //集群服务目标IP
        private String serverIp;
        //集群服务目标端口
        private int serverPort;
        //消息体
        private Object message;

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public String getServerIp() {
                return serverIp;
        }

        public void setServerIp(String serverIp) {
                this.serverIp = serverIp;
        }

        public int getServerPort() {
                return serverPort;
        }

        public void setServerPort(int serverPort) {
                this.serverPort = serverPort;
        }

        public Object getMessage() {
                return message;
        }

        public void setMessage(Object message) {
                this.message = message;
        }
}

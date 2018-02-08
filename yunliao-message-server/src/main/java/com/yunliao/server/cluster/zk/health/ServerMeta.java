package com.yunliao.server.cluster.zk.health;

/**
 * 保存到zookeeper的服务器信息
 * @author peter
 * @version V1.0 创建时间：18/2/2
 *          Copyright 2018 by PreTang
 */
public class ServerMeta {

        private String ip;
        private int port;
        private String hostName;
        private String cpu;
        private String memery;
        private long userLoginNum;
        private long messageQueueSize;

        public String getIp() {
                return ip;
        }

        public void setIp(String ip) {
                this.ip = ip;
        }

        public int getPort() {
                return port;
        }

        public void setPort(int port) {
                this.port = port;
        }

        public String getHostName() {
                return hostName;
        }

        public void setHostName(String hostName) {
                this.hostName = hostName;
        }

        public String getCpu() {
                return cpu;
        }

        public void setCpu(String cpu) {
                this.cpu = cpu;
        }

        public String getMemery() {
                return memery;
        }

        public void setMemery(String memery) {
                this.memery = memery;
        }

        public long getUserLoginNum() {
                return userLoginNum;
        }

        public void setUserLoginNum(long userLoginNum) {
                this.userLoginNum = userLoginNum;
        }

        public long getMessageQueueSize() {
                return messageQueueSize;
        }

        public void setMessageQueueSize(long messageQueueSize) {
                this.messageQueueSize = messageQueueSize;
        }
}

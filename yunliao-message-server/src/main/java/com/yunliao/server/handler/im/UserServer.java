package com.yunliao.server.handler.im;

/**
 * 用户所在服务器
 * @author peter
 * @version V1.0 创建时间：18/2/1
 *          Copyright 2018 by PreTang
 */
public class UserServer {
    //身份唯一标识
    private String userId;
    //发送目标登录ip
    private String serverIp;
    //发送目标登录端口
    private int serverPort;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}

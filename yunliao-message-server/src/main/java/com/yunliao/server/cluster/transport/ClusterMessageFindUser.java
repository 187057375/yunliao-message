package com.yunliao.server.cluster.transport;

/**
 * 服务间通讯-查找用户消息
 * @author peter
 * @version V1.0 创建时间：18/2/6
 *          Copyright 2018 by PreTang
 */
public class ClusterMessageFindUser{

    private String userId;
    private String whereFind;
    private String whoFind;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWhereFind() {
        return whereFind;
    }

    public void setWhereFind(String whereFind) {
        this.whereFind = whereFind;
    }

    public String getWhoFind() {
        return whoFind;
    }

    public void setWhoFind(String whoFind) {
        this.whoFind = whoFind;
    }
}

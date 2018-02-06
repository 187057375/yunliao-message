package com.yunliao.server.listen;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/6
 *          Copyright 2018 by PreTang
 */
public class ChannelMap {
    public static final String TCP               = "A";    // tcp端
    public static final String WEBSOCKET         = "B";    // websocket端
    public static final int channelIdLenth         = 9;
    public  static ConcurrentHashMap chanelMap = new ConcurrentHashMap<String,Object>();

}

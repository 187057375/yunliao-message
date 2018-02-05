package com.yunliao.server.handler;

/**
 * 传输层协议头.
 *
 *                                         Protocol
 *  __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __
 * |           |           |           |           |              |                          |
 *       1           1           1           1            4               Uncertainty
 * |__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __ __|__ __ __ __ __ __ __ __ __|
 * |           |           |           |           |              |                          |
 *     Magic        Sign        Type       Status     Body Length         Body Content
 * |__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __ __|__ __ __ __ __ __ __ __ __|
 *
 * 协议头8个字节定长
 *     Magic      // 数据包的验证位，byte类型
 *     Sign       // 消息标志，请求／响应／通知，byte类型
 *     Type       // 消息类型，登录／发送消息等，byte类型
 *     Status     // 响应状态，成功／失败，byte类型
 *     BodyLength // 协议体长度，int类型
 *

 */
public class MessageProtocolHeader {

    /** 协议头长度 */
    public static final int HEADER_LENGTH = 8;
    /** Magic */
    public static final byte MAGIC =  0x66;

    /** 消息标志 */
    private byte sign;

    /** sign: 0x01 ~ 0x03 ============================= */
    public static final byte REQUEST               = 0x01;    // 请求  Client --> Server
    public static final byte RESPONSE              = 0x02;    // 响应  Server --> Client
    public static final byte NOTICE                = 0x03;    // 通知  Server --> Client  e.g.消息转发

    /** 消息类型 */
    private byte type;

    /** type: 0x11 ~ 0x23 ============================= */
    public static final byte LOGIN                 = 0x01;    // 登录
    public static final byte REGISTER              = 0x02;    // 注册
    public static final byte LOGOUT                = 0x03;    // 登出
    public static final byte RECONN                = 0x04;    // 重连
    public static final byte PERSON_MESSAGE        = 0x10;    // 发送个人消息
    public static final byte GROUP_MESSAGE         = 0x11;    // 发送讨论组消息
    public static final byte CREATE_GROUP          = 0x20;    // 创建讨论组
    public static final byte DISBAND_GROUP         = 0x21;    // 解散讨论组
    public static final byte ADD_MEMBER            = 0x22;    // 讨论组添加成员
    public static final byte REMOVE_MEMBER         = 0x24;    // 讨论组移除成员
    public static final byte ADD_FRIEND            = 0x24;    // 添加好友
    public static final byte REMOVE_FRIEND         = 0x25;    // 删除好友
    public static final byte ALL_FRIEND            = 0x26;    // 查看已添加好友
    public static final byte UPDATE_SELF_INFO      = 0x30;    // 修改个人信息
    public static final byte LOOK_SELF_INFO        = 0x31;    // 查看个人信息
    public static final byte LOOK_FRIEND_INFO      = 0x32;    // 查看好友信息
    public static final byte LOOK_GROUP_INFO       = 0x33;    // 查看自己所在讨论组信息
    public static final byte HEARTBEAT             = 0x40;    // 心跳
    public static final byte CUSTOM                = 0x60;    // 自定义

    /** 响应状态 */
    private byte status;

    /** status: 0x31 ~ 0x34 ============================= */
    public static final byte SUCCESS               = 0x01;    // 49 请求成功
    public static final byte REQUEST_ERROR         = 0x02;    // 50 请求错误
    public static final byte SERVER_BUSY           = 0x03;    // 51 服务器繁忙
    public static final byte SERVER_ERROR          = 0x04;    // 52 服务器错误

    /** 消息体长度 */
    private int bodyLength;
}

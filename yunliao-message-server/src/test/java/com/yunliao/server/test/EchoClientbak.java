package com.yunliao.server.test;

import com.alibaba.fastjson.JSON;
import com.yunliao.server.handler.im.chat.ChatMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;


public final class EchoClientbak {
 
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "9000"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                    // p.addLast("encoder", new StringEncoder());
                     p.addLast(new EchoClientHandlerBak());
                 }
             });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){

                String test = in.readLine();
                System.out.println(test);
                if(test.startsWith("register")){
                    ByteArrayOutputStream byteOut =  new ByteArrayOutputStream();
                    DataOutputStream outputStream = new DataOutputStream(byteOut);
                    outputStream.writeByte(0x66);//魔数
                    outputStream.writeByte(0x01);//消息标志，请求
                    outputStream.writeByte(0x02);//消息类型, 注册
                    outputStream.writeByte(0x01);//请求成功
                    outputStream.writeInt("13055556666".getBytes("UTF-8").length);
                    outputStream.write("13055556666".getBytes("UTF-8"));
                    ByteBuf buf = f.channel().alloc().buffer();
                    buf.writeBytes(byteOut.toByteArray());
                    f.channel().writeAndFlush(buf);
                }
                if(test.startsWith("send")){
                    ByteArrayOutputStream byteOut =  new ByteArrayOutputStream();
                    DataOutputStream outputStream = new DataOutputStream(byteOut);
                    outputStream.writeByte(0x66);//写入魔数
                    outputStream.writeByte(0x01);//消息标志，请求
                    outputStream.writeByte(0x10);//消息类型,发送个人消息
                    outputStream.writeByte(0x01);//请求成功

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setFromId("13055556666");
                    chatMessage.setToId("19988880000");
                    chatMessage.setMsg("hello啊");
                    String msg = JSON.toJSONString(chatMessage);
                    outputStream.writeInt(msg.getBytes("UTF-8").length);
                    outputStream.write(msg.getBytes("UTF-8"));
                    ByteBuf buf = f.channel().alloc().buffer();
                    buf.writeBytes(byteOut.toByteArray());
                    f.channel().writeAndFlush(buf);
                }

            }
            //f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}

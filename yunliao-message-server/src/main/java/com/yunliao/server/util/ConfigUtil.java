package com.yunliao.server.util;

import java.io.*;
import java.util.Properties;

/**
 * 读取配置文件
 */
public class ConfigUtil {
    private static Properties prop = new Properties();
    static {
        try {
            if (prop.isEmpty()) {
                java.io.InputStream resource = ConfigUtil.class.getClassLoader().getResourceAsStream("yunliao.properties");
                if (resource == null){
                    resource = ConfigUtil.class.getClassLoader().getResourceAsStream("/yunliao.properties");
                }
                if (resource != null){
                    prop.load(resource);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key) {
        return  prop.getProperty(key);
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(ConfigUtil.getProperty("yunliao.server.port"));
    }
}

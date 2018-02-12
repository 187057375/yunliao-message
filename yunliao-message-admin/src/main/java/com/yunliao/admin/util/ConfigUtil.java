package com.yunliao.admin.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 读取配置文件
 */
public class ConfigUtil {
    private static Properties prop = new Properties();
    static {
        try {
            if (prop.isEmpty()) {
                InputStream resource = ConfigUtil.class.getClassLoader().getResourceAsStream("yunliao-admin.properties");
                if (resource == null){
                    resource = ConfigUtil.class.getClassLoader().getResourceAsStream("/yunliao-admin.properties");
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

package com.yunliao.admin.util.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;


public class SerializeUtil {
    private static final Log LOG = LogFactory.getLog(SerializeUtil.class);

    public static byte[] serialize(Serializable object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;

        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (Exception e) {
            LOG.error("serialize fail", e);
        }

        return null;
    }

    public static Object unserialize(byte[] bytes) {
        if(null != bytes){
            ByteArrayInputStream bais = null;
            try {
                // 反序列化
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("unserialize fail", e);
            }
        }
        return null;
    }
}

package com.zhizu.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class MyPropertiesUtil {

    private static final Logger logger = Logger.getLogger(MyPropertiesUtil.class);
    private static ConcurrentHashMap<String, Properties> PROPS_HOLDER = new ConcurrentHashMap<>();

    public static Properties loadProps(String file) {
        Properties properties = PROPS_HOLDER.get(file);
        try {
            if (properties == null) {
                properties = new Properties();
                InputStream inStream = null;
                try {
                    inStream = MyPropertiesUtil.class.getResourceAsStream(file);
                    if (inStream != null) {
                        properties.load(inStream);
                    }
                    PROPS_HOLDER.putIfAbsent(file, properties);
                } finally {
                    if (inStream != null) {
                        inStream.close();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("load[" + file + "] error", e);
        }
        return properties;
    }

    public static Properties getProperties(String file) {
        return loadProps(file);
    }

    public static String getProperty(String file, String key, String defaultValue) {
        Properties properties = loadProps(file);
        return properties.getProperty(key, defaultValue);
    }

}

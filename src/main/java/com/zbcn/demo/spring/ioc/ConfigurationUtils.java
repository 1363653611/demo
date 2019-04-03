package com.zbcn.demo.spring.ioc;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取配置文件工具类
 */
public class ConfigurationUtils {

    private  static Properties properties;
    static {
        properties = getBeanScanPath(null);
    }

    public static Properties getBeanScanPath(String propertiesPath){
        if(StringUtils.isBlank(propertiesPath)){
            propertiesPath = "/spring/beanScan.properties";
        }
        Properties properties = new Properties();

        InputStream resourceAsStream =
                ConfigurationUtils.class.getResourceAsStream(propertiesPath);

        System.out.println("正在加载配置文件" + propertiesPath);

        try {
            properties.load(resourceAsStream);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(resourceAsStream != null){
                    resourceAsStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return properties;
        }
    }

    public static Object getPropertiesByKey(String propertiesKey) {
        if (properties.size() > 0) {
            return properties.get(propertiesKey);
        }
        return null;
    }

    public static Properties getProperties(){
        return properties;
    }


}

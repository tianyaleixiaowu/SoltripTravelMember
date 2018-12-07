package com.s.t.m.project.util;

import com.s.t.m.common.utils.PropertiesLoader;

/**
 * 获取配置工具
 */
public class ConfigHelper {

    private static final String PROPPATH = "/config.properties";

    public static String getAppConfig(String key){
        PropertiesLoader loader = new PropertiesLoader(PROPPATH);
        String str = loader.getProperty(key);
        return str;
    }
}

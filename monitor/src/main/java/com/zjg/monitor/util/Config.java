package com.zjg.monitor.util;

import lombok.Data;

/**
 * 获得配置信息
 * @Author zhangjingao3
 * @Date 2020/3/26 17:00
 */
public class Config {

    /**
     * 应用名，默认为项目名，可通过继承修改
     */
    public static String appName;

    protected static void setAppName(){
        String appProjectDir = System.getProperty("user.dir");
        String[] dirPaths = appProjectDir.split("/");
        appName = dirPaths[dirPaths.length - 1];
    }

    public static String getAppName () {
        return appName;
    }

}

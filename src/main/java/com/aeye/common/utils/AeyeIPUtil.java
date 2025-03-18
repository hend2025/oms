package com.aeye.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shenxingping
 * @date 2021/09/08
 */
public class AeyeIPUtil {

    private static final Pattern IP_PATTERN = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
    private static final Pattern IP_PORT_PATTERN = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?):[0-9]{2,5}");

    public static String hostRewrite(String oldHost, String newHost){
        return IP_PATTERN.matcher(oldHost).replaceAll(newHost);
    }

    public static String hostRewriteFirst(String oldHost, String newHost){
        return IP_PATTERN.matcher(oldHost).replaceFirst(newHost);
    }

    /**
     * 字符串查找IP,仅支持单个IP查找
     * @param ipStr
     * @return
     */
    public static String findIp(String ipStr){
        Matcher matcher = IP_PATTERN.matcher(ipStr);
        if(matcher.find()){
            String group = matcher.group();
            return group;
        }
        return null;
    }
    public static String findIpPort(String ipStr){
        Matcher matcher = IP_PORT_PATTERN.matcher(ipStr);
        if(matcher.find()){
            String group = matcher.group();
            return group;
        }
        return null;
    }

    public static String hostPortRewrite(String oldHost, String newHost){
        return IP_PORT_PATTERN.matcher(oldHost).replaceAll(newHost);
    }

    public static String hostPortRewriteFirst(String oldHost, String newHost){
        return IP_PORT_PATTERN.matcher(oldHost).replaceFirst(newHost);
    }

    public static void main(String args[]){
        System.out.println(hostPortRewriteFirst("wss://111.160.220.217:6014/proxy/111.160.220.217:559/openUrl/27y9NIs", findIpPort("192.168.16.199:8888")));
//        System.out.println(hostRewrite("ws://192.168.16.42:559/openUrl/qAwTlEQ?beginTime=20210810T000000&endTime=20210811T000000&playBackMode=1&filesize=1024000 ", "192.168.16.148"));
    }
}

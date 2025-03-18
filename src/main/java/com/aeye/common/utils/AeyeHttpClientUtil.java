package com.aeye.common.utils;


import com.aeye.common.config.AeyeHttpPoolConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * https://github.com/apache/httpcomponents-client/blob/4.5.x/httpclient/src/examples/org/apache/http/examples/client/ClientConfiguration.java
 * @Author shenxingping
 * @Date 2021/1/16
 */
public class AeyeHttpClientUtil {

    private final static String UNKNOWN_KEY = "unknown";
    private final static String XML_HTTP_KEY = "XMLHttpRequest";
    private final static String X_REQUESTED_KEY = "x-requested-with";
    private final static int CONN_COUNT = 5;
    private final static int max_ip_length = 20;
    private static final Logger logger = LoggerFactory.getLogger(AeyeHttpClientUtil.class);
    /**
     * 采用http连接池
     */
    private static PoolingHttpClientConnectionManager connectionManager = null;

    private static CloseableHttpClient httpClient = null;
    static {
        try{
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    (s, sslSession) -> {return true;});

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslsf).build();

            connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        AeyeHttpPoolConfig poolConfig = SpringContextUtils.getBean(AeyeHttpPoolConfig.class);
        connectionManager.setMaxTotal(poolConfig.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(poolConfig.getMaxPerRoute());

        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(poolConfig.getSocketTimeout())
                .setConnectTimeout(poolConfig.getConnectTimeout())
                .setConnectionRequestTimeout(poolConfig.getConnectionRequestTimeout())
                .build();
        httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
    }


    public static boolean jsAjax(HttpServletRequest req){
        //判断是否为ajax请求，默认不是
        boolean isAjaxRequest = false;
        if(!StringUtils.isBlank(req.getHeader(X_REQUESTED_KEY)) && XML_HTTP_KEY.equals(req.getHeader(X_REQUESTED_KEY))){
            isAjaxRequest = true;
        }
        return isAjaxRequest;
    }

    public static String sendPostForm(String url, MultiValueMap<String, String> params) throws Exception{
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置超时
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(5000);
        RestTemplate client = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }

    public static JSONObject sendPostJson(String url, JSONObject params) throws Exception{
        return sendPostJson(url, params.toString());
    }

    public static JSONObject sendPostJson(String url, String jsonParams) throws Exception{
        return sendPostJson(url, jsonParams, new TypeReference<JSONObject>(){}, null);
    }

    public static <T> T sendPostJson(String url, String jsonParams, TypeReference<T> tClass, Map<String, String> headMap) throws Exception{
        HttpPost post = new HttpPost(url);
        post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        if(headMap != null){
            headMap.forEach((s, o) -> {
                post.setHeader(s, o);
            });
        }
        StringEntity entity = new StringEntity(jsonParams, StandardCharsets.UTF_8);
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        try {
            return JSON.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"), tClass);
        }finally {
            response.close();
        }
    }

    /**
     * 读取HttpServletRequest 里面的json，返回字符串
     * @Author 沈兴平
     * @Date 2020/12/1
     * @Param request
     * @return java.lang.String
     */
    public static String readHttpRequest(HttpServletRequest request) throws Exception{
        BufferedReader br;
        String jsonStr = null;
        br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        jsonStr = sb.toString();
        return jsonStr;
    }

    public static String sendGet(String url) throws Exception{
        return sendGet(url, new TypeReference<String>(){});
    }
    public static <T> T sendGet(String url, TypeReference<T> tClass) throws Exception{
        HttpGet post = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(post);
        try {
            return JSON.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"), tClass);
        }finally {
            response.close();
        }
    }

    public static boolean isConnect(String urlStr) {
        int counts = 1;
        int state = -1;
        if (urlStr == null || urlStr.length() <= 0) {
            return false;
        }
        while (counts <= CONN_COUNT) {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                state = con.getResponseCode();
                if (state == 200) {
                    return true;
                }
                break;
            }catch (Exception ex) {
                counts++;
                logger.info("URL不可用，连接第 "+counts+" 次");
                continue;
            }
        }
        return false;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static void main(String[] args) throws Exception{

        String authResuUrl = "http://local.oauth2.com:8001/api/getAllSystemMenu?userId=1";

        String value = AeyeHttpClientUtil.sendGet(authResuUrl);
        System.out.println(value);
    }

    /**
     * 获取IP地址
     *
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || UNKNOWN_KEY.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || UNKNOWN_KEY.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN_KEY.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN_KEY.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN_KEY.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            logger.error("IPUtils ERROR ", e);
        }
        ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
        if(ip.length() > max_ip_length){
            ip = ip.replace("127.0.0.1,", "").trim();
        }
        return ip;
    }

    /**
     * 获取当前访问URL （含协议、域名、端口号[忽略80端口]、项目名）
     * @param request
     * @return: String
     */
    public static String getServerUrl(HttpServletRequest request) {
        // 访问协议
        String agreement = request.getScheme();
        // 访问域名
        String serverName = request.getServerName();
        // 访问端口号
        int port = request.getServerPort();
        // 访问项目名
        String contextPath = request.getContextPath();
        String url = "%s://%s%s%s";
        String portStr = "";
        if (port != 80) {
            portStr += ":" + port;
        }
        return String.format(url, agreement, serverName, portStr, contextPath);

    }

    public static String getServerUrlNoPath(HttpServletRequest request) {
        // 访问协议
        String agreement = request.getScheme();
        // 访问域名
        String serverName = request.getServerName();
        // 访问端口号
        int port = request.getServerPort();
        // 访问项目名
        String url = "%s://%s%s";
        String portStr = "";
        if (port != 80) {
            portStr += ":" + port;
        }
        return String.format(url, agreement, serverName, portStr);

    }
}

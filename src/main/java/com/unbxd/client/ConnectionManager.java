package com.unbxd.client;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 09/07/14
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionManager {

    private static PoolingHttpClientConnectionManager __connectionManager;

    public static PoolingHttpClientConnectionManager getConnectionManager(){
        if(__connectionManager == null){
            synchronized (ConnectionManager.class){
                if(__connectionManager == null){

                    Registry registry = RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build();
                    __connectionManager = new PoolingHttpClientConnectionManager(registry);
                    __connectionManager.setDefaultMaxPerRoute(50);
                    __connectionManager.setMaxTotal(100);
                }
            }
        }

        return __connectionManager;
    }

    public static RequestConfig getRequestConfig() {
        RequestConfig defaultConfig = RequestConfig.custom()
                .setConnectTimeout(30 * 1000)
                .setSocketTimeout(60 * 1000)
                .build();

        return defaultConfig;
    }
}

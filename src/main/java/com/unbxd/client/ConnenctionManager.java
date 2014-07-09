package com.unbxd.client;

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
public class ConnenctionManager {

    private static PoolingHttpClientConnectionManager __connectionManager;

    public static PoolingHttpClientConnectionManager getConnectionManager(){
        if(__connectionManager == null){
            synchronized (ConnenctionManager.class){
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
}

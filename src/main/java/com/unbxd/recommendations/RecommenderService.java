package com.unbxd.recommendations;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 28/03/14
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommenderService {
    private static final String host = "apac-recommendations.unbxdapi.com";
    private static final String baseUrl = "http://apac-recommendations.unbxdapi.com/v1.0/";

    private static PoolingHttpClientConnectionManager manager;

    private static PoolingHttpClientConnectionManager getConnectionManager(){
        if(manager == null){
            synchronized (RecommenderService.class){
                if(manager == null){
                    manager = new PoolingHttpClientConnectionManager();
                    manager.setMaxTotal(100);
                    manager.setDefaultMaxPerRoute(50);
                    HttpHost recommenderHost = new HttpHost(host, 80);
                    manager.setMaxPerRoute(new HttpRoute(recommenderHost), 100);
                }
            }
        }
        return manager;
    }

    private static Map<String, Object> getResponse(String url) throws IOException {
        PoolingHttpClientConnectionManager manager = getConnectionManager();
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(manager).build();
        CloseableHttpResponse response = null;
        try{
            HttpGet request = new HttpGet(url);
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseText = EntityUtils.toString(entity, "UTF-8").trim();
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseText, Map.class);
            }
        }finally {
            if(response != null)
                response.close();
        }
        return null;
    }

    public static Map<String, Object> getRecommendationsForYou(String siteName, String apiKey, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/recommend/" + uid + "/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }

        return getResponse(url);
    }

    public static Map<String, Object> getRecentlyViewed(String siteName, String apiKey, String uid) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/recently-viewed/" + uid + "/?format=json";
        return getResponse(url);
    }

    public static Map<String, Object> getMoreLikeThese(String siteName, String apiKey, String pid, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/more-like-these/" + pid + "/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }
        if(uid != null){
            url += "&uid=" + uid;
        }
        return getResponse(url);
    }

    public static Map<String, Object> getAlsoViewed(String siteName, String apiKey, String pid, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/also-viewed/" + pid + "/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }
        if(uid != null){
            url += "&uid=" + uid;
        }
        return getResponse(url);
    }

    public static Map<String, Object> getAlsoBought(String siteName, String apiKey, String pid, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/also-bought/" + pid + "/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }
        if(uid != null){
            url += "&uid=" + uid;
        }
        return getResponse(url);
    }

    public static Map<String, Object> getTopSellers(String siteName, String apiKey, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/top-sellers/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }
        if(uid != null){
            url += "&uid=" + uid;
        }
        return getResponse(url);
    }

    public static Map<String, Object> getCategoryTopSellers(String siteName, String apiKey, String categoryId, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/category-top-sellers/" + categoryId + "/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }
        if(uid != null){
            url += "&uid=" + uid;
        }
        return getResponse(url);
    }

    public static Map<String, Object> getBrandTopSellers(String siteName, String apiKey, String brandId, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/brand-top-sellers/" + brandId + "/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }
        if(uid != null){
            url += "&uid=" + uid;
        }
        return getResponse(url);
    }

    public static Map<String, Object> getPDPTopSellers(String siteName, String apiKey, String pid, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/pdp-top-sellers/" + pid + "/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }
        if(uid != null){
            url += "&uid=" + uid;
        }
        return getResponse(url);
    }

    public static Map<String, Object> getCartRecommendations(String siteName, String apiKey, String uid, String ip) throws IOException {
        String url = baseUrl + apiKey + "/" + siteName + "/cart-recommend/" + uid + "/?format=json";
        if(ip != null){
            url += "&ip=" + ip;
        }
        return getResponse(url);
    }
}

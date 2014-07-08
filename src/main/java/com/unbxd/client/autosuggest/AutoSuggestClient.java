package com.unbxd.client.autosuggest;

import com.unbxd.client.autosuggest.exceptions.AutoSuggestException;
import com.unbxd.client.autosuggest.response.AutoSuggestResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoSuggestClient {

    private static final Logger LOG = LoggerFactory.getLogger(AutoSuggestClient.class);

    private static PoolingHttpClientConnectionManager __connectionManager;

    private static PoolingHttpClientConnectionManager getConnectionManager(){
        if(__connectionManager == null){
            synchronized (AutoSuggestClient.class){
                if(__connectionManager == null){
                    __connectionManager = new PoolingHttpClientConnectionManager();
                    __connectionManager.setDefaultMaxPerRoute(50);
                    __connectionManager.setMaxTotal(100);
                }
            }
        }

        return __connectionManager;
    }

    private String siteKey;
    private String apiKey;
    private boolean secure;

    private String _query;
    private Map<AutoSuggestType, Integer> _config;

    public AutoSuggestClient(String siteKey, String apiKey, boolean secure) {
        this.siteKey = siteKey;
        this.apiKey = apiKey;
        this.secure = secure;
    }

    private String getAutoSuggestUrl(){
        return (secure ? "https://" : "http://") + siteKey + ".search.unbxdapi.com/" + apiKey + "/autosuggest?wt=json";
    }

    public AutoSuggestClient autosuggest(String query, Map<AutoSuggestType, Integer> config){
        this._query = query;
        this._config = config;

        return this;
    }

    private String generateUrl(){

    }

    public AutoSuggestResponse execute() throws AutoSuggestException {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(getConnectionManager()).build();
        try{
            String url = this.generateUrl();

            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);

            try{
                StringBuffer sb = new StringBuffer();
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }

                String responseText = sb.toString();

                if(response.getStatusLine().getStatusCode() == 200){
                    Map<String, Object> responseObject = new ObjectMapper().readValue(responseText, Map.class);
                    return new AutoSuggestResponse(responseObject);
                } else {
                    LOG.error(responseText);
                    throw new AutoSuggestException(responseText);
                }
            } catch (JsonParseException e) {
                LOG.error(e.getMessage(), e);
                throw new AutoSuggestException(e);
            } catch (JsonMappingException e) {
                LOG.error(e.getMessage(), e);
                throw new AutoSuggestException(e);
            } catch (ClientProtocolException e) {
                LOG.error(e.getMessage(), e);
                throw new AutoSuggestException(e);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
                throw new AutoSuggestException(e);
            }
        } catch (ClientProtocolException e) {
            LOG.error(e.getMessage(), e);
            throw new AutoSuggestException(e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new AutoSuggestException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

}

package com.unbxd.client.autosuggest;

import com.unbxd.client.ConnenctionManager;
import com.unbxd.client.autosuggest.exceptions.AutoSuggestException;
import com.unbxd.client.autosuggest.response.AutoSuggestResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    private static final String __encoding = "UTF-8";

    private String siteKey;
    private String apiKey;
    private boolean secure;

    private String query;
    private int bucketCount;
    private int bucketSize;
    private int popularProductsCount;
    private int keywordSuggestionsCount;
    private int topQueriesCount;

    public AutoSuggestClient(String siteKey, String apiKey, boolean secure) {
        this.siteKey = siteKey;
        this.apiKey = apiKey;
        this.secure = secure;

        this.bucketCount = -1;
        this.bucketSize = -1;
        this.popularProductsCount = -1;
        this.keywordSuggestionsCount = -1;
        this.topQueriesCount = -1;
    }

    private String getAutoSuggestUrl(){
        return (secure ? "https://" : "http://") + siteKey + ".search.unbxdapi.com/" + apiKey + "/autosuggest?wt=json";
    }

    public AutoSuggestClient autosuggest(String query){
        this.query = query;

        return this;
    }

    public AutoSuggestClient setBucketCount(int bucketCount) {
        this.bucketCount = bucketCount;

        return this;
    }

    public AutoSuggestClient setBucketSize(int bucketSize) {
        this.bucketSize = bucketSize;

        return this;
    }

    public AutoSuggestClient setPopularProductsCount(int popularProductsCount) {
        this.popularProductsCount = popularProductsCount;

        return this;
    }

    public AutoSuggestClient setKeywordSuggestionsCount(int keywordSuggestionsCount) {
        this.keywordSuggestionsCount = keywordSuggestionsCount;

        return this;
    }

    public AutoSuggestClient setTopQueriesCount(int topQueriesCount) {
        this.topQueriesCount = topQueriesCount;

        return this;
    }

    private String generateUrl() throws AutoSuggestException {
        try {
            StringBuffer sb = new StringBuffer();

            if(query != null){
                sb.append(this.getAutoSuggestUrl());
                sb.append("&q=" + URLEncoder.encode(query, __encoding));

            }

            if(bucketCount != -1){
                sb.append("&buckets.count=" + URLEncoder.encode(bucketCount + "", __encoding));
            }

            if(bucketSize != -1){
                sb.append("&buckets.size=" + URLEncoder.encode(bucketSize + "", __encoding));
            }

            if(popularProductsCount != -1){
                sb.append("&popularProducts.count=" + URLEncoder.encode(popularProductsCount + "", __encoding));
            }

            if(keywordSuggestionsCount != -1){
                sb.append("&keywordSuggestions.count=" + URLEncoder.encode(keywordSuggestionsCount + "", __encoding));
            }

            if(topQueriesCount != -1){
                sb.append("&topQueries.count=" + URLEncoder.encode(topQueriesCount + "", __encoding));
            }

            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            LOG.error("Encoding error", e);
            throw new AutoSuggestException(e);
        }
    }

    public AutoSuggestResponse execute() throws AutoSuggestException {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(ConnenctionManager.getConnectionManager()).build();
        try{
            String url = this.generateUrl();

            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);

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
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

}

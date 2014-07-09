package com.unbxd.client.search;

import com.unbxd.client.ConnenctionManager;
import com.unbxd.client.search.exceptions.SearchException;
import com.unbxd.client.search.response.SearchResponse;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchClient {

    private static final Logger LOG = LoggerFactory.getLogger(SearchClient.class);

    public enum SortDir{
        ASC,
        DESC
    }

    private static final String __encoding = "UTF-8";

    private String siteKey;
    private String apiKey;
    private boolean secure;

    private String query;
    private Map<String, String> queryParams;
    private String bucketField;
    private List<String> categoryIds;
    private Map<String, List<String>> filters;
    private Map<String, SortDir> sorts;
    private int pageNo;
    private int pageSize;


    protected SearchClient(String siteKey, String apiKey, boolean secure) {
        this.siteKey = siteKey;
        this.apiKey = apiKey;
        this.secure = secure;

        this.filters = new HashMap<String, List<String>>();
        this.sorts = new LinkedHashMap<String, SortDir>(); // The map needs to be insertion ordered.

        this.pageNo = 1;
        this.pageSize = 10;
    }

    private String getSearchUrl(){
        return (secure ? "https://" : "http://") + siteKey + ".search.unbxdapi.com/" + apiKey + "/search?wt=json";
    }

    private String getBrowseUrl(){
        return (secure ? "https://" : "http://") + siteKey + ".search.unbxdapi.com/" + apiKey + "/browse?wt=json";
    }

    public SearchClient search(String query, Map<String, String> queryParams){
        this.query = query;
        this.queryParams = queryParams;

        return this;
    }

    public SearchClient bucket(String query, String bucketField, Map<String, String> queryParams){
        this.query = query;
        this.queryParams = queryParams;
        this.bucketField = bucketField;

        return this;
    }

    public SearchClient browse(String nodeId, Map<String, String> queryParams){
        this.categoryIds = Arrays.asList(nodeId);
        this.queryParams = queryParams;

        return this;
    }

    // Has to be used when one node has multiple parents. All the node ids will be ANDed
    public SearchClient browse(List<String> nodeIds, Map<String, String> queryParams){
        this.categoryIds = nodeIds;
        this.queryParams = queryParams;

        return this;
    }

    // Values in the same fields are ORed and different fields are ANDed
    public SearchClient addFilter(String fieldName, String... values){
        this.filters.put(fieldName, Arrays.asList(values));

        return this;
    }

    public SearchClient addSort(String field, SortDir sortDir){
        this.sorts.put(field, sortDir);

        return this;
    }

    public SearchClient addSort(String field){
        this.addSort(field, SortDir.DESC);

        return this;
    }

    public SearchClient setPage(int pageNo, int pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;

        return this;
    }

    private String generateUrl() throws SearchException {
        if(query != null && categoryIds != null){
            throw new SearchException("Can't set query and node id at the same time");
        }

        try {
            StringBuffer sb = new StringBuffer();

            if(query != null){
                sb.append(this.getSearchUrl());
                sb.append("&q=" + URLEncoder.encode(query, __encoding));

                if(bucketField != null){
                    sb.append("&bucket.field=" + URLEncoder.encode(bucketField, __encoding));
                }
            }else if(categoryIds != null && categoryIds.size() > 0){
                sb.append(this.getBrowseUrl());
                sb.append("&category-id=" + URLEncoder.encode(StringUtils.join(categoryIds, ","), __encoding));
            }

            if(queryParams != null && queryParams.size() > 0){
                for(String key : queryParams.keySet()){
                    sb.append("&" + key + "=" + URLEncoder.encode(queryParams.get(key), __encoding));
                }
            }

            if(filters != null && filters.size() > 0){
                for(String key : filters.keySet()){
                    for(String value : filters.get(key)){
                        sb.append("&filter=" + URLEncoder.encode(key + ":\"" + value + "\"", __encoding));
                    }
                }
            }

            if(sorts != null && sorts.size() > 0){
                List<String> sorts = new ArrayList<String>();
                for(String key : this.sorts.keySet()){
                    sorts.add(key + " " + this.sorts.get(key).name().toLowerCase());
                }
                sb.append("&sort=" + URLEncoder.encode(StringUtils.join(sorts, ","), __encoding));
            }

            sb.append("&pageNumber=" + pageNo);
            sb.append("&rows=" + pageSize);

            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            LOG.error("Encoding error", e);
            throw new SearchException(e);
        }
    }

    public SearchResponse execute() throws SearchException {
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
                return new SearchResponse(responseObject);
            }else{
                LOG.error(responseText);
                throw new SearchException(responseText);
            }
        } catch (JsonParseException e) {
            LOG.error(e.getMessage(), e);
            throw new SearchException(e);
        } catch (JsonMappingException e) {
            LOG.error(e.getMessage(), e);
            throw new SearchException(e);
        } catch (ClientProtocolException e) {
            LOG.error(e.getMessage(), e);
            throw new SearchException(e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SearchException(e);
        }
    }
}

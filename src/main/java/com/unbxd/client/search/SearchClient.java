package com.unbxd.client.search;

import com.unbxd.client.ConnectionManager;
import com.unbxd.client.search.exceptions.SearchException;
import com.unbxd.client.search.response.SearchResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 10:55 AM
 *
 * Client class for calling Search APIs
 */
public class SearchClient {

    private static final Logger LOG = Logger.getLogger(SearchClient.class);

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
    private Map<String, List<String>> textFilters;
    private Map<String, List<String>> rangeFilters;
    private Map<String, SortDir> sorts;
    private int pageNo;
    private int pageSize;
    private  Map<String, String> extraParams;


    protected SearchClient(String siteKey, String apiKey, boolean secure) {
        this.siteKey = siteKey;
        this.apiKey = apiKey;
        this.secure = secure;

        this.textFilters = new HashMap<String, List<String>>();
        this.rangeFilters = new HashMap<String, List<String>>();
        this.sorts = new LinkedHashMap<String, SortDir>(); // The map needs to be insertion ordered.

        this.extraParams = new HashMap<String, String>();
        this.pageNo = 0;
        this.pageSize = 10;
    }

    private String getSearchUrl(){
        return (secure ? "https://" : "http://") + "search.unbxdapi.com/" + apiKey + "/" +  siteKey + "/search?wt=json";
    }

    private String getBrowseUrl(){
        return (secure ? "https://" : "http://") + "search.unbxdapi.com/" + apiKey + "/" +  siteKey + "/browse?wt=json";
    }

    /**
     * Searches for a query and appends the query parameters in the call.
     * @param query
     * @param queryParams
     * @return this
     */
    public SearchClient search(String query, Map<String, String> queryParams){
        this.query = query;
        this.queryParams = queryParams;

        return this;
    }

    /**
     * Searches for a query, appends the query parameters in the call and responds with bucketed results.
     * @param query
     * @param bucketField Field on which buckets have to created.
     * @param queryParams
     * @return this
     */
    public SearchClient bucket(String query, String bucketField, Map<String, String> queryParams){
        this.query = query;
        this.queryParams = queryParams;
        this.bucketField = bucketField;

        return this;
    }

    /**
     * Calls for browse query and fetches results for given nodeId
     * @param nodeId
     * @param queryParams
     * @return this
     */
    public SearchClient browse(String nodeId, Map<String, String> queryParams){
        this.categoryIds = Arrays.asList(nodeId);
        this.queryParams = queryParams;

        return this;
    }

    /**
     * Calls for browse query and fetches results for given nodeIds.
     * Has to be used when one node has multiple parents. All the node ids will be ANDed
     * @param nodeIds
     * @param queryParams
     * @return this
     */
    public SearchClient browse(List<String> nodeIds, Map<String, String> queryParams){
        this.categoryIds = nodeIds;
        this.queryParams = queryParams;

        return this;
    }

    /**
     * Filters the results
     * Values in the same fields are ORed and different fields are ANDed
     * @param fieldName
     * @param values
     * @return this
     */
    public SearchClient addTextFilter(String fieldName, String... values){
        if(textFilters.containsKey(fieldName)) {
            List<String> previousValues = new ArrayList<String>(this.textFilters.get(fieldName));
            for(String val : values){
                previousValues.add(val);
            }
            this.textFilters.put(fieldName,previousValues);
        }else{
            this.textFilters.put(fieldName, Arrays.asList(values));
        }
        return this;
    }

    public SearchClient addRangeFilter(String fieldName, String start, String end){
        if(rangeFilters.containsKey(fieldName)){
            List<String> previousValues = this.rangeFilters.get(fieldName);
            String range = "[" + start + " TO "+ end + "]";
            previousValues.add(range);
            this.rangeFilters.put(fieldName, previousValues);
        }
        else{
            String range = "[" + start + " TO "+ end + "]";
            this.rangeFilters.put(fieldName,Arrays.asList(range));
        }
        return this;
    }

    public SearchClient otherParams(String Otherkey, String Othervalue){
        this.extraParams.put(Otherkey, Othervalue);
        return this;
    }

    /**
     * Sorts the results on a field
     * @param field
     * @param sortDir
     * @return this
     */
    public SearchClient addSort(String field, SortDir sortDir){
        this.sorts.put(field, sortDir);

        return this;
    }

    /**
     * Sorts the results on a field in descending
     * @param field
     * @return this
     */
    public SearchClient addSort(String field){
        this.addSort(field, SortDir.DESC);

        return this;
    }

    /**
     *
     * @param pageNo
     * @param pageSize
     * @return this
     */
    public SearchClient setPage(int pageNo, int pageSize){
        if(pageNo==0) {
            this.pageNo = pageNo;
        }else{
            this.pageNo = pageNo - 1;
        }
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

            if(textFilters != null && textFilters.size() > 0) {
                for (String key : textFilters.keySet()) {
                        sb.append("&filter=" + URLEncoder.encode(key + ":\"" + StringUtils.join(textFilters.get(key), "\" OR " + key +":\"") + "\"", __encoding));
                    }
            }

            if(rangeFilters != null && rangeFilters.size()>0){
                for(String key : rangeFilters.keySet()){
                    sb.append("&filter=" + URLEncoder.encode(key + ":" + StringUtils.join(rangeFilters.get(key), " OR " + key +":"), __encoding));
                    }

                }

            if(sorts != null && sorts.size() > 0){
                List<String> sorts = new ArrayList<String>();
                for(String key : this.sorts.keySet()){
                    sorts.add(key + " " + this.sorts.get(key).name().toLowerCase());
                }
                sb.append("&sort=" + URLEncoder.encode(StringUtils.join(sorts, ","), __encoding));
            }

            if(extraParams != null && extraParams.size() > 0){
                for(String key : extraParams.keySet()){
                    sb.append("&" + key + "=" + URLEncoder.encode(extraParams.get(key),__encoding));
                }
            }

            sb.append("&pageNumber=" + pageNo);
            sb.append("&rows=" + pageSize);

            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            LOG.error("Encoding error", e);
            throw new SearchException(e);
        }
    }

    /**
     * Executes search.
     *
     * @return {@link SearchResponse}
     * @throws SearchException
     */
    public SearchResponse execute() throws SearchException {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(ConnectionManager.getConnectionManager()).build();
        try{
            String url = this.generateUrl();

            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);

            if(response.getStatusLine().getStatusCode() == 200){
                Map<String, Object> responseObject = new ObjectMapper().readValue(new InputStreamReader(response.getEntity().getContent()), Map.class);
                return new SearchResponse(responseObject);
            }else{
                StringBuffer sb = new StringBuffer();
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }

                String responseText = sb.toString();

                LOG.error(responseText);
                throw new SearchException(responseText);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SearchException(e);
        }
    }
}

package com.unbxd.client.search;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchClientFactory {

    public static SearchClient getSearchClient(String siteKey, String apiKey, boolean secure){
        return new SearchClient(siteKey, apiKey, secure);
    }
}

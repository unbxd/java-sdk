package com.unbxd.client.recommendations;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendationsClientFactory {

    public static RecommendationsClient getRecommendationsClient(String siteKey, String apiKey, boolean secure){
        return new RecommendationsClient(siteKey, apiKey, secure);
    }
}

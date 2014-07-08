package com.unbxd.client.recommendations.response;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendationResponse {

    public int getStatusCode();
    public int getErrorCode();
    public String getMessage();
    public int getTotalResultsCount();
    public RecommendationResults getResults();

}

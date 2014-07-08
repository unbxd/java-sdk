package com.unbxd.client.recommendations.response;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendationResults {

    public int getResultsCount();
    public RecommendationResult getAt(int i);
    public RecommendationResult[] getResults();

}

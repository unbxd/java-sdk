package com.unbxd.client;

import com.unbxd.client.recommendations.exceptions.RecommendationsException;
import com.unbxd.client.recommendations.response.RecommendationResponse;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 09/07/14
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendationsClientTest extends TestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Unbxd.configure("demosite-u1407617955968", "64a4a2592a648ac8415e13c561e44991", "64a4a2592a648ac8415e13c561e44991");
    }

    public void test() throws RecommendationsException, ConfigException {
        RecommendationResponse response = Unbxd.getRecommendationsClient().getMoreLikeThis("sku1", null,"100.0.0.1").execute();

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertEquals(0, response.getTotalResultsCount());
        Assert.assertEquals(0, response.getResults().getResultsCount());
    }
}

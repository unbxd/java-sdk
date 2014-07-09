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

        Unbxd.configure("demo-u1393483043451", "ae30782589df23780a9d98502388555f", "ae30782589df23780a9d98502388555f");
    }

    public void test() throws RecommendationsException {
        RecommendationResponse response = Unbxd.getRecommendationsClient().getMoreLikeThis("532062745e4016fd1c73b7a4", null).execute();

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getStatusCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertEquals(6, response.getTotalResultsCount());
        Assert.assertEquals(6, response.getResults().getResultsCount());
        Assert.assertNotNull(response.getResults().getAt(0).getUniqueId());
    }
}

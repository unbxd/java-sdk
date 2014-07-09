package com.unbxd.client;

import com.unbxd.client.search.SearchClient;
import com.unbxd.client.search.exceptions.SearchException;
import com.unbxd.client.search.response.SearchResponse;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class SearchClientTest extends TestCase{
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Unbxd.configure("demo-u1393483043451", "ae30782589df23780a9d98502388555f", "ae30782589df23780a9d98502388555f");
    }

    public void test_search() throws SearchException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("fl", "uniqueId");
        queryParams.put("stats", "price");

        SearchResponse response = Unbxd.getSearchClient()
                .search("*", queryParams)
                .addFilter("color_fq","black")
                .addFilter("Brand_fq", "Ralph Lauren")
                .addSort("price", SearchClient.SortDir.ASC)
                .setPage(2, 5)
                .execute();

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getStatusCode());
        Assert.assertNotEquals(0, response.getQueryTime());
        Assert.assertEquals(0, response.getErrorCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertNotEquals(0, response.getTotalResultsCount());
        Assert.assertEquals(5, response.getResults().getResultsCount());
        Assert.assertEquals(1, response.getResults().getAt(0).getAttributes().size());
        Assert.assertNotNull(response.getResults().getAt(0).getAttributes().get("uniqueId"));
        Assert.assertNotNull(response.getStats());
        Assert.assertNotNull(response.getStats().getStat("price").getMin());
    }

    public void test_bucket() throws SearchException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("fl", "uniqueId");
        queryParams.put("stats", "price");

        SearchResponse response = Unbxd.getSearchClient()
                .bucket("*", "category", queryParams)
                .addFilter("color_fq","black")
                .addFilter("Brand_fq", "Ralph Lauren")
                .addSort("price", SearchClient.SortDir.ASC)
                .setPage(2, 5)
                .execute();

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getStatusCode());
        Assert.assertNotEquals(0, response.getQueryTime());
        Assert.assertEquals(0, response.getErrorCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertNotEquals(0, response.getTotalResultsCount());
        Assert.assertNull(response.getResults());
        Assert.assertEquals(5, response.getBuckets().getNumberOfBuckets());
        Assert.assertNotEquals(0, response.getBuckets().getBuckets().get(0).getTotalResultsCount());
        Assert.assertEquals(1, response.getBuckets().getBuckets().get(0).getResults().getAt(0).getAttributes().size());
        Assert.assertNotNull(response.getBuckets().getBuckets().get(0).getResults().getAt(0).getAttributes().get("uniqueId"));
        Assert.assertNotNull(response.getStats());
        Assert.assertNotNull(response.getStats().getStat("price").getMin());
    }
}

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

        Unbxd.configure("demosite-u1407617955968", "64a4a2592a648ac8415e13c561e44991", "64a4a2592a648ac8415e13c561e44991");
    }

    public void test_search() throws SearchException, ConfigException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("fl", "uniqueId");
        queryParams.put("stats", "price");

        SearchResponse response = Unbxd.getSearchClient()
                .search("*", queryParams)
                .addTextFilter("category_fq","men")
                .addRangeFilter("price_fq", "1000", "2000")
                .addSort("price", SearchClient.SortDir.ASC)
                .addOtherParams("stats","price_fq")
                .setPage(0, 10)
                .execute();

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getStatusCode());
        Assert.assertNotEquals(0, response.getQueryTime());
        Assert.assertEquals(0, response.getErrorCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertNotEquals(0, response.getTotalResultsCount());
        Assert.assertEquals(10, response.getResults().getResultsCount());
        Assert.assertEquals(1, response.getResults().getAt(0).getAttributes().size());
        Assert.assertNotNull(response.getResults().getAt(0).getAttributes().get("uniqueId"));
        Assert.assertNotNull(response.getStats());
        Assert.assertNotNull(response.getStats().getStat("price_fq").getMin());
        Assert.assertNotNull(response.getBanners());
    }

    public void test_browse() throws SearchException, ConfigException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("fl", "uniqueId");
        queryParams.put("stats", "price");

        SearchResponse response = Unbxd.getSearchClient()
                .browse("1", queryParams)
                .addTextFilter("category_fq", "men")
                .addTextFilter("category_fq","women")
                .addRangeFilter("price_fq", "1000", "2000")
                .addRangeFilter("price_fq", "2000", "3000")
                .addSort("price", SearchClient.SortDir.ASC)
                .setPage(0, 10)
                .execute();

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getStatusCode());
        Assert.assertNotEquals(0, response.getQueryTime());
        Assert.assertEquals(0, response.getErrorCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertNotEquals(0, response.getTotalResultsCount());
        Assert.assertEquals(10, response.getResults().getResultsCount());
        Assert.assertEquals(1, response.getResults().getAt(0).getAttributes().size());
        Assert.assertNotNull(response.getResults().getAt(0).getAttributes().get("uniqueId"));
        Assert.assertNotNull(response.getStats());
        Assert.assertNotNull(response.getStats().getStat("price").getMin());
        Assert.assertNotNull(response.getBanners());
    }

    public void test_bucket() throws SearchException, ConfigException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("fl", "uniqueId");
        queryParams.put("stats", "price");

        SearchResponse response = Unbxd.getSearchClient()
                .bucket("*", "category", queryParams)
                .addTextFilter("category_fq","men")
                .addRangeFilter("price_fq", "1000","2000")
                .addSort("price", SearchClient.SortDir.ASC)
                .setPage(0, 10)
                .execute();

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getStatusCode());
        Assert.assertNotEquals(0, response.getQueryTime());
        Assert.assertEquals(0, response.getErrorCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertNotEquals(0, response.getTotalResultsCount());
        Assert.assertNull(response.getResults());
        Assert.assertEquals(1, response.getBuckets().getNumberOfBuckets());
        Assert.assertNotEquals(0, response.getBuckets().getBuckets().get(0).getTotalResultsCount());
        Assert.assertEquals(1, response.getBuckets().getBuckets().get(0).getResults().getAt(0).getAttributes().size());
        Assert.assertNotNull(response.getBuckets().getBuckets().get(0).getResults().getAt(0).getAttributes().get("uniqueId"));
        Assert.assertNotNull(response.getStats());
        Assert.assertNotNull(response.getStats().getStat("price").getMin());
    }
}

package com.unbxd.client;

import com.unbxd.client.autosuggest.exceptions.AutoSuggestException;
import com.unbxd.client.autosuggest.response.AutoSuggestResponse;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 09/07/14
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoSuggestClientTest extends TestCase{
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Unbxd.configure("demosite-u1407617955968", "64a4a2592a648ac8415e13c561e44991", "64a4a2592a648ac8415e13c561e44991");
    }

    public void test_autosuggest() throws AutoSuggestException, ConfigException {
        AutoSuggestResponse response = Unbxd.getAutoSuggestClient()
                .autosuggest("sh")
                .setInFieldsCount(3)
                .setKeywordSuggestionsCount(5)
                .setPopularProductsCount(10)
                .setTopQueriesCount(4)
                .execute();

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getStatusCode());
        Assert.assertNotNull(response.getQueryTime());
        Assert.assertEquals(0, response.getErrorCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertNotEquals(0, response.getTotalResultsCount());
    }
}

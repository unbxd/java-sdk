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

        Unbxd.configure("autosuggesttest-u1405357792247", "7db139ac885f6516fb276520668daf83", "7db139ac885f6516fb276520668daf83");
    }

    public void test_autosuggest() throws AutoSuggestException, ConfigException {
        AutoSuggestResponse response = Unbxd.getAutoSuggestClient()
                .autosuggest("sh")
                .setInFieldsCount(3)
                .setKeywordSuggestionsCount(5)
                .setPopularProductsCount(10)
                .setTopQueriesCount(4)
                .execute();

        System.out.println(response.toString());
        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getStatusCode());
        Assert.assertNotEquals(0, response.getQueryTime());
        Assert.assertEquals(0, response.getErrorCode());
        Assert.assertEquals("OK", response.getMessage());
        Assert.assertNotEquals(0, response.getTotalResultsCount());
    }
}

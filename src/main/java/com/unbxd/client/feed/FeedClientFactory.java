package com.unbxd.client.feed;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedClientFactory {

    public static FeedClient getFeedClient(String siteKey, String secretKey, boolean secure){
        return new FeedClient(siteKey, secretKey, secure);
    }
}

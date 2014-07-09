package com.unbxd.client.autosuggest;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoSuggestClientFactory {

    public static AutoSuggestClient getAutoSuggestClient(String siteKey, String apiKey, boolean secure){
        return new AutoSuggestClient(siteKey, apiKey, secure);
    }
}

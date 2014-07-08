package com.unbxd.client.autosuggest.response;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoSuggestResponse {

    public int getStatusCode();
    public int getErrorCode();
    public String getMessage();
    public int getQueryTime();
    public AutoSuggestResults getResults();
}

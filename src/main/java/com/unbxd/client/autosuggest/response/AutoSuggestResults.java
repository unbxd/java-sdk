package com.unbxd.client.autosuggest.response;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoSuggestResults {

    public Map<AutoSuggestType, AutoSuggestResultSection> getResultSections();
    public AutoSuggestResultSection getResultSection(AutoSuggestType type);

}

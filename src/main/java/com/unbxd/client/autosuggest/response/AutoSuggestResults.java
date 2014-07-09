package com.unbxd.client.autosuggest.response;

import com.unbxd.client.autosuggest.AutoSuggestType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoSuggestResults {

    private Map<AutoSuggestType, AutoSuggestResultSection> _resultSections;

    protected AutoSuggestResults(List<Map<String, Object>> params) {
        this._resultSections = new HashMap<AutoSuggestType, AutoSuggestResultSection>();
        for(Map<String, Object> result : params){
            AutoSuggestType type = AutoSuggestType.valueOf((String) result.get("doctype"));
            if(!this._resultSections.containsKey(type))
                this._resultSections.put(type, new AutoSuggestResultSection(type));

            this._resultSections.get(type).addResult(result);
        }
    }

    public Map<AutoSuggestType, AutoSuggestResultSection> getResultSections(){
        return this._resultSections;
    }

    public AutoSuggestResultSection getBuckets(){
        return this._resultSections.get(AutoSuggestType.IN_FIELD);
    }

    public AutoSuggestResultSection getPopularProducts(){
        return this._resultSections.get(AutoSuggestType.POPULAR_PRODUCTS);
    }

    public AutoSuggestResultSection getKeywordSuggestions(){
        return this._resultSections.get(AutoSuggestType.KEYWORD_SUGGESTION);
    }

    public AutoSuggestResultSection getTopQueries(){
        return this._resultSections.get(AutoSuggestType.TOP_SEARCH_QUERIES);
    }

}

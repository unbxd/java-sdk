package com.unbxd.client.autosuggest.response;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoSuggestResult {

    private Map<String, Object> _attributes;

    protected AutoSuggestResult(Map<String, Object> params) {
        this._attributes = params;
    }

    public Map<String, Object> getAttributes(){
        return this._attributes;
    }

    public Object getAttribute(String fieldName){
        return this._attributes.get(fieldName);
    }

    public String getSuggestion(){
        return (String) this.getAttribute("autosuggest");
    }

}

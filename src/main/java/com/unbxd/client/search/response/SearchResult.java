package com.unbxd.client.search.response;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult {

    private String _uniqueId;
    private Map<String, Object> _values;

    protected SearchResult(Map<String, Object> product){
        this._values = product;
        this._uniqueId = (String) _values.get("uniqueId");
    }

    public Map<String, Object> getAttributes(){
        return this._values;
    }

    public String getUniqueId(){
        return this._uniqueId;
    }

    public Object getAttribute(String fieldName){
        return this._values.get(fieldName);
    }
}

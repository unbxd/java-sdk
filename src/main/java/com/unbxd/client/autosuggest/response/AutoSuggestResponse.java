package com.unbxd.client.autosuggest.response;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoSuggestResponse {

    private int _statusCode;
    private int _errorCode;
    private String _message;
    private int _queryTime;
    private int _totalResultsCount;
    private AutoSuggestResults _results;

    public AutoSuggestResponse(Map<String, Object> params) {
        Map<String, Object> metaData = (Map<String, Object>) params.get("searchMetaData");

        this._statusCode = (Integer) metaData.get("status");
        this._queryTime = (Integer) metaData.get("queryTime");

        if(params.containsKey("response")){
            Map<String, Object> response = (Map<String, Object>) params.get("response");
            this._totalResultsCount = (Integer) response.get("numberOfProducts");
            this._results = new AutoSuggestResults((List<Map<String, Object>>) response.get("products"));
        }

        if(params.containsKey("error")){
            Map<String, Object> error = (Map<String, Object>) params.get("error");
            this._errorCode = (Integer) error.get("code");
            this._message = (String) error.get("msg");
        }else{
            this._message = "OK";
        }
    }

    public int getStatusCode(){
        return this._statusCode;
    }

    public int getErrorCode(){
        return this._errorCode;
    }

    public String getMessage(){
        return this._message;
    }

    public int getQueryTime(){
        return this._queryTime;
    }

    public int getTotalResultsCount(){
        return this._totalResultsCount;
    }

    public AutoSuggestResults getResults(){
        return _results;
    }
}

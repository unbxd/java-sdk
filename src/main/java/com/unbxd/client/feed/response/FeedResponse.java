package com.unbxd.client.feed.response;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedResponse {

    private int _statusCode;
    private String _message;
    private String _uploadID;
    private List<FeedFieldError> _fieldErrors;

    public FeedResponse(Map<String, Object> response){
        _statusCode = (Integer) response.get("statusCode");
        _message = (String) response.get("message");
        _uploadID = (String) response.get("unbxdFileName");


    }

    public int getStatusCode(){
        return _statusCode;
    }

    public String getMessage(){
        return _message;
    }

    public String getUploadID(){
        return _uploadID;
    }

    public List<FeedFieldError> getFieldErrors(){
        return _fieldErrors;
    }

}

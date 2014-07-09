package com.unbxd.client.feed.response;

import java.util.ArrayList;
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
    private List<String> _unknownSchemaFields;
    private List<FeedFieldError> _fieldErrors;
    private int _rowNum;
    private int _colNum;

    public FeedResponse(Map<String, Object> response){
        _statusCode = (Integer) response.get("statusCode");
        _message = (String) response.get("message");
        _uploadID = (String) response.get("unbxdFileName");
        _unknownSchemaFields = (List<String>) response.get("unknownSchemaFields");

        if(response.containsKey("fieldErrors")){
            List<Map<String, Object>> fieldErrors = (List<Map<String, Object>>) response.get("fieldErrors");
            _fieldErrors = new ArrayList<FeedFieldError>();
            for(Map<String, Object> error : fieldErrors){
                _fieldErrors.add(new FeedFieldError(error));
            }
        }

        this._rowNum = (Integer) response.get("rowNum");
        this._colNum = (Integer) response.get("colNum");
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

    public List<String> getUnknownSchemaFields(){
        return _unknownSchemaFields;
    }

    public int getRowNum() {
        return _rowNum;
    }

    public int getColNum() {
        return _colNum;
    }
}

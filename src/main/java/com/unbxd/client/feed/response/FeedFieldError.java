package com.unbxd.client.feed.response;

import com.unbxd.client.feed.DataType;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedFieldError {

    private String _fieldName;
    private Object _fieldValue;
    private DataType _dataType;
    private boolean _multivalued;
    private int _errorCode;
    private String _message;
    private int _rowNum;
    private int _colNum;

    protected FeedFieldError(Map<String, Object> params) {
        this._fieldName = (String) params.get("fieldName");
        this._fieldValue = params.get("fieldValue");
        this._dataType = DataType.valueOf((String) params.get("dataType"));
        this._multivalued = (Boolean) params.get("multiValue");
        this._errorCode = (Integer) params.get("errorCode");
        this._message = (String) params.get("message");
        this._rowNum = (Integer) params.get("rowNum");
        this._colNum = (Integer) params.get("colNum");
    }

    public String getFieldName() {
        return _fieldName;
    }

    public Object getFieldValue() {
        return _fieldValue;
    }

    public DataType getDataType() {
        return _dataType;
    }

    public boolean isMultivalued() {
        return _multivalued;
    }

    public int getErrorCode() {
        return _errorCode;
    }

    public String getMessage() {
        return _message;
    }

    public int getRowNum() {
        return _rowNum;
    }

    public int getColNum() {
        return _colNum;
    }
}

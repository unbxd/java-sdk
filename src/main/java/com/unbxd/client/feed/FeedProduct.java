package com.unbxd.client.feed;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedProduct {
    private String uniqueId;
    private final Map<String, Object> _attributes;
    private List<Map<String, Object>> _associatedDocuments;

    public FeedProduct(String uniqueId, Map<String, Object> attributes){
        if(attributes == null)
            attributes = new HashMap<String, Object>();
        attributes.put("uniqueId", uniqueId);

        _attributes = attributes;
        _associatedDocuments = new ArrayList<Map<String, Object>>();

        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Map<String, Object> getAttributes(){
        return _attributes;
    }

    public List<Map<String, Object>> getAssociatedProducts(){
        return _associatedDocuments;
    }

    public void addAssociatedProduct(Map<String, Object> product){
        _associatedDocuments.add(product);
    }

    public Object get(Object key) {
        return _attributes.get(key);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FeedProduct{");
        sb.append("uniqueId='").append(uniqueId).append('\'');
        sb.append(", _attributes=").append(_attributes);
        sb.append(", _associatedDocuments=").append(_associatedDocuments);
        sb.append('}');
        return sb.toString();
    }
}

package com.unbxd.client.feed;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 4:54 PM
 *
 * Represents a product being added/updates in feed
 */
public class FeedProduct {
    // Unique Id of the product. Generally corresponds to the SKU.
    private String uniqueId;
    private final Map<String, Object> _attributes;
    private List<Map<String, Object>> _associatedDocuments;

    /**
     * @param uniqueId
     * @param attributes
     */
    public FeedProduct(String uniqueId, Map<String, Object> attributes){
        if(attributes == null)
            attributes = new HashMap<String, Object>();
        attributes.put("uniqueId", uniqueId);

        _attributes = attributes;
        _associatedDocuments = new ArrayList<Map<String, Object>>();

        this.uniqueId = uniqueId;
    }

    /**
     * @return Unique Id of the product
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @return Product Attributes
     */
    public Map<String, Object> getAttributes(){
        return _attributes;
    }

    /**
     * @return get list of associated products
     */
    public List<Map<String, Object>> getAssociatedProducts(){
        return _associatedDocuments;
    }

    public void addAssociatedProduct(Map<String, Object> product){
        _associatedDocuments.add(product);
    }

    /**
     *
     * @param key
     * @return Attribute of the product
     */
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

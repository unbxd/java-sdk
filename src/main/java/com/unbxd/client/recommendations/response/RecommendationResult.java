package com.unbxd.client.recommendations.response;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendationResult {

    public Map<String, Object> getValues();
    public String getUniqueId();
    public Object getValue(String fieldName);
    public String getReason();

}

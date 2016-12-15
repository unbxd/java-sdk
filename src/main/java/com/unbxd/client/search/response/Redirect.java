package com.unbxd.client.search.response;

import java.util.Map;

/**
 * Created by Sunil on 12/15/16.
 */
public class Redirect {
    private RedirectData redirectData;

    public Redirect(Map<String, Object> params) {
        redirectData = new RedirectData();
        if(params.containsKey("type") && params.containsKey("value")) {
            redirectData.setType((String) params.get("type"));
            redirectData.setValue((String) params.get("value"));
        }
    }

    public RedirectData getRedirectData() {
        return redirectData;
    }

    public void setRedirectData(RedirectData redirectData) {
        this.redirectData = redirectData;
    }
}

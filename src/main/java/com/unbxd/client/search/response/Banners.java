package com.unbxd.client.search.response;

import java.util.LinkedHashMap;

/**
 * Created by unbxd on 8/20/15.
 */
public class Banners {
    public String imageUrl;
    public String  landingPageUrl;
    public Banners(LinkedHashMap<String, String> banner) {
        this.imageUrl = banner.get("imageUrl");
        this.landingPageUrl = banner.get("landingUrl");
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLandingPageUrl() {
        return landingPageUrl;
    }
}

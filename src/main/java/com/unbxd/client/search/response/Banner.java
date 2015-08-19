package com.unbxd.client.search.response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by unbxd on 8/19/15.
 */
public class Banner {
    protected ArrayList<String> categories;
    protected ArrayList<LinkedHashMap<String, String>> banners;

    public Banner(Map<String, Object> params) {
        this.banners = (ArrayList<LinkedHashMap<String, String>>) params.get("banners");
        this.categories = (ArrayList<String>) params.get("categories");
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public ArrayList<LinkedHashMap<String, String>> getBanners() {
        return banners;
    }
}

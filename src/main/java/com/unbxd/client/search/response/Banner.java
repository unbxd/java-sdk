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
    protected List<Banners> _banner;

    public Banner(Map<String, Object> params) {
        this._banner = new ArrayList<Banners>();
        this.banners = (ArrayList<LinkedHashMap<String, String>>) params.get("banners");

        for(LinkedHashMap<String, String> aBanner : banners) {
            Banners banner = new Banners(aBanner);
            _banner.add(banner);
        }

        this.categories = (ArrayList<String>) params.get("categories");
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public List<Banners> getBanners() {
        return _banner;
    }
}

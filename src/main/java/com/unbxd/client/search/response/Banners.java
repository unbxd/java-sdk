package com.unbxd.client.search.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by unbxd on 8/19/15.
 */
public class Banners {
    private List<Banner> _banners;
   // private Map<String, Facets> _bannerMap;


    protected Banners(Map<String, Object> params) {
        this._banners = new ArrayList<Banner>();
        //this._bannerMap = new HashMap<String, Facets>();

        Banner banner = new Banner(params);
        this._banners.add(banner);


    }

    public List<Banner> get_banners() {
        return _banners;
    }
}

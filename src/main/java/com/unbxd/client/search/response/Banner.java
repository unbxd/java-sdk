package com.unbxd.client.search.response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by unbxd on 8/19/15.
 */
public class Banner {
    protected String algo;
    protected List<String> imageUrl;
    protected  List<String> landingUrl;
    protected Map<String ,ArrayList> banner_names;

    public Banner(Map<String, Object> params) {
        this.algo = (String) params.get("algo");

        imageUrl = new ArrayList<String>();
        landingUrl = new ArrayList<String>();
        banner_names = new LinkedHashMap<String, ArrayList>();
        for(String field : params.keySet()){
            if(field.equals("banners")){
                ArrayList<LinkedHashMap<String, String>> a = (ArrayList<LinkedHashMap<String, String>>) params.get("banners");
                for(LinkedHashMap<String, String> b : a){
                    imageUrl.add(b.get("imageUrl"));
                    landingUrl.add(b.get("landingUrl"));
                }
            }
            else if(params.get(field) instanceof  ArrayList<?>){
                banner_names.put(field, (ArrayList) params.get(field));
            }
        }

    }

    public String getAlgo() {
        return algo;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public List<String> getLandingUrl() {
        return landingUrl;
    }

    public Map<String, ArrayList> getBanner_names() {
        return banner_names;
    }

    public ArrayList<String> getBanner(String fieldname){
        return banner_names.get(fieldname);
    }
}

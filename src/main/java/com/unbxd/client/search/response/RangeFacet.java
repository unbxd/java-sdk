package com.unbxd.client.search.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class RangeFacet extends Facet{

    private int _gap;
    protected List<RangeFacetEntry> _rangeFacetEntries;

    protected RangeFacet(String facetName, Map<String, Object> params) {
        super(facetName, params);

        this._gap = (Integer) params.get("gap");
    }

    @Override
    protected void generateEntries(List<Object> values) {
        super.generateEntries(values);

        this._rangeFacetEntries = new ArrayList<RangeFacetEntry>();
        for(FacetEntry entry : _facetEntries){
            int from = Integer.parseInt(entry.getTerm());
            int to = from + _gap;
            this._rangeFacetEntries.add(new RangeFacetEntry(from, to, entry.getCount()));
        }
    }

    public List<RangeFacetEntry> getRangeEntries(){
        return this._rangeFacetEntries;
    }

}

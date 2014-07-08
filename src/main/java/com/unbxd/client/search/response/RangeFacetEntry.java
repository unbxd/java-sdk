package com.unbxd.client.search.response;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class RangeFacetEntry extends FacetEntry{

    private int from;
    private int to;

    public RangeFacetEntry(int from, int to, int count) {
        super(count);

        this.from = from;
        this.to = to;
    }

    public int getFrom(){
        return this.from;
    }

    public int getTo(){
        return this.to;
    }

}

package com.unbxd.client.search.response;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class BucketResults {

    private int _numberOfBuckets;
    private List<BucketResult> _buckets;
    private Map<String, BucketResult> _bucketsMap;

    protected BucketResults(Map<String, Object> params){
        this._numberOfBuckets = (Integer) params.get("numberOfBuckets");
        for(String bucketKey : params.keySet()){
            if(bucketKey.equals("totalProducts") || bucketKey.equals("numberOfBuckets")){
                continue;
            }

            BucketResult bucket = new BucketResult((Map<String, Object>) params.get(bucketKey));
            this._buckets.add(bucket);
            this._bucketsMap.put(bucketKey, bucket);
        }
    }

    public int getNumberOfBuckets(){
        return this._numberOfBuckets;
    }

    public BucketResult getBuckets(String value){
        return this._bucketsMap.get(value);
    }

    public List<BucketResult> getBuckets(){
        return this._buckets;
    }

}

package com.unbxd.client.recommendations;

import com.unbxd.client.recommendations.response.RecommendationResponse;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 08/07/14
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendationsClient {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationsClient.class);

    private static PoolingHttpClientConnectionManager __connectionManager;

    private static PoolingHttpClientConnectionManager getConnectionManager(){
        if(__connectionManager == null){
            synchronized (RecommendationsClient.class){
                if(__connectionManager == null){
                    __connectionManager = new PoolingHttpClientConnectionManager();
                    __connectionManager.setDefaultMaxPerRoute(50);
                    __connectionManager.setMaxTotal(100);
                }
            }
        }

        return __connectionManager;
    }

    private enum RecommenderBoxType {
        ALSO_VIEWED,
        ALSO_BOUGHT,
        RECENTLY_VIEWED,
        RECOMMENDED_FOR_YOU,
        MORE_LIKE_THESE,
        TOP_SELLERS,
        CATEGORY_TOP_SELLERS,
        BRAND_TOP_SELLERS,
        PDP_TOP_SELLERS,
        CART_RECOMMEND
    }

    private String siteKey;
    private String apiKey;
    private boolean secure;

    private RecommenderBoxType _boxType;
    private String _uid;
    private String _ip;
    private String _uniqueId;
    private String _category;
    private String _brand;

    public RecommendationsClient(String siteKey, String apiKey, boolean secure) {
        this.siteKey = siteKey;
        this.apiKey = apiKey;
        this.secure = secure;
    }

    private String getRecommendationUrl(){
        return (secure ? "https://" : "http://") + "apac-recommendations.unbxdapi.com/v1.0/" + apiKey + "/" + siteKey + "/";
    }

    public RecommendationsClient getRecentlyViewed(String uid){
        this._boxType = RecommenderBoxType.RECENTLY_VIEWED;
        this._uid = uid;

        return this;
    }

    public RecommendationsClient getRecommendedForYou(String uid, String ip){
        this._boxType = RecommenderBoxType.RECOMMENDED_FOR_YOU;
        this._uid = uid;
        this._ip = ip;

        return this;
    }

    public RecommendationsClient getMoreLikeThis(String uniqueId, String uid){
        this._boxType = RecommenderBoxType.MORE_LIKE_THESE;
        this._uid = uid;
        this._uniqueId = uniqueId;

        return this;
    }

    public RecommendationsClient getAlsoViewed(String uniqueId, String uid){
        this._boxType = RecommenderBoxType.ALSO_VIEWED;
        this._uid = uid;
        this._uniqueId = uniqueId;

        return this;
    }

    public RecommendationsClient getAlsoBought(String uniqueId, String uid){
        this._boxType = RecommenderBoxType.ALSO_BOUGHT;
        this._uid = uid;
        this._uniqueId = uniqueId;

        return this;
    }

    public RecommendationsClient getTopSellers(String uid, String ip){
        this._boxType = RecommenderBoxType.TOP_SELLERS;
        this._uid = uid;
        this._ip = ip;

        return this;
    }

    public RecommendationsClient getCategoryTopSellers(String category, String uid, String ip){
        this._boxType = RecommenderBoxType.CATEGORY_TOP_SELLERS;
        this._uid = uid;
        this._ip = ip;
        this._category = category;

        return this;
    }

    public RecommendationsClient getBrandTopSellers(String brand, String uid, String ip){
        this._boxType = RecommenderBoxType.BRAND_TOP_SELLERS;
        this._uid = uid;
        this._ip = ip;
        this._brand = brand;

        return this;
    }

    public RecommendationsClient getPDPTopSellers(String uniqueId, String uid, String ip){
        this._boxType = RecommenderBoxType.PDP_TOP_SELLERS;
        this._uid = uid;
        this._ip = ip;
        this._uniqueId = uniqueId;

        return this;
    }

    public RecommendationsClient getCartRecommendations(String uid, String ip){
        this._boxType = RecommenderBoxType.CART_RECOMMEND;
        this._uid = uid;
        this._ip = ip;

        return this;
    }

    public RecommendationResponse execute();

}

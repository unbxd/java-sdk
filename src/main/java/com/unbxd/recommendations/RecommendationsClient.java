package com.unbxd.recommendations;

import java.io.IOException;
import java.util.Map;

/**
 * Client to call recommendations APIs.
 * Response is a map in the following format :
 * Success :
 * {
     "count": 1,
     "boxType": "CART_RECOMMEND",
     "Recommendations": [
         {
            "uniqueId": 3106,
            "title": "Slim Fit Flat Front Trousers",
            "brand": "Louis Philippe",
            "category": "Trousers & Chinos",
            "price": 1899
         }
     ]
   }
 *
 * Error :
 * {
     "count": 0,
     "error": {
        "code": 3005,
        "message": "Product Id not found"
     }
   }
 */
public class RecommendationsClient {
    /**
     * Site Name
     */
    private String siteName;

    /**
     * Secret API KEY
     */
    private String apiKey;

    public RecommendationsClient(String siteName, String apiKey){
        this.siteName = siteName;
        this.apiKey = apiKey;
    }

    /**
     * Fetches Personalized recommendations for a user
     * @param uid value of the 'unbxd.userId' cookie
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getRecommendationsForYou(String uid, String ip) throws IOException {
        return RecommenderService.getRecommendationsForYou(siteName, apiKey, uid, ip);
    }

    /**
     * Fetches Recently Viewed products for a user
     * @param uid value of the 'unbxd.userId' cookie
     * @return
     */
    public Map<String, Object> getRecentlyViewedProducts(String uid) throws IOException {
        return RecommenderService.getRecentlyViewed(siteName, apiKey, uid);
    }

    /**
     * Fetches products similar to the given product
     * @param pid 'uniqueId' of the product
     * @param uid value of the 'unbxd.userId' cookie (optional)
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getMoreLikeThese(String pid, String uid, String ip) throws IOException {
        return RecommenderService.getMoreLikeThese(siteName, apiKey, pid, uid, ip);
    }

    /**
     * Fetches products that people viewed who also viewed the given product
     * @param pid 'uniqueId' of the product
     * @param uid value of the 'unbxd.userId' cookie (optional)
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getAlsoViewed(String pid, String uid, String ip) throws IOException {
        return RecommenderService.getAlsoViewed(siteName, apiKey, pid, uid, ip);
    }

    /**
     * Fetches products that people bought who also viewed the given bought
     * @param pid 'uniqueId' of the product
     * @param uid value of the 'unbxd.userId' cookie (optional)
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getAlsoBought(String pid, String uid, String ip) throws IOException {
        return RecommenderService.getAlsoBought(siteName, apiKey, pid, uid, ip);
    }

    /**
     * Fetches top sellers on the website
     * @param uid value of the 'unbxd.userId' cookie
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getTopSellers(String uid, String ip) throws IOException {
        return RecommenderService.getTopSellers(siteName, apiKey, uid, ip);
    }

    /**
     * Fetches top sellers for the given category on the website
     * @param categoryId Category Id
     * @param uid value of the 'unbxd.userId' cookie (optional)
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getCategoryTopSellers(String categoryId, String uid, String ip) throws IOException {
        return RecommenderService.getCategoryTopSellers(siteName, apiKey, categoryId, uid, ip);
    }

    /**
     * Fetches top sellers for the given brand on the website
     * @param brandId Brand Id
     * @param uid value of the 'unbxd.userId' cookie (optional)
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getBrandTopSellers(String brandId, String uid, String ip) throws IOException {
        return RecommenderService.getBrandTopSellers(siteName, apiKey, brandId, uid, ip);
    }

    /**
     * Fetches top sellers to be shown on the product description page (PDP)
     * @param pid 'uniqueId' of the product
     * @param uid value of the 'unbxd.userId' cookie (optional)
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getPDPTopSellers(String pid, String uid, String ip) throws IOException {
        return RecommenderService.getPDPTopSellers(siteName, apiKey, pid, uid, ip);
    }

    /**
     * Fetches recommendations based on the user's cart
     * @param uid value of the 'unbxd.userId' cookie
     * @param ip ip address of the user (optional)
     * @return
     */
    public Map<String, Object> getCartRecommendations(String uid, String ip) throws IOException {
        return RecommenderService.getCartRecommendations(siteName, apiKey, uid, ip);
    }
}

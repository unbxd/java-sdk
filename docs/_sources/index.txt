.. Unbxd JAVA SDK documentation master file, created by
   sphinx-quickstart on Mon Aug  4 10:41:39 2014.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Introduction
============

This SDK will enable you to use full feature set of Unbxd APIs. 
You will be able to do the following through this SDK: 

* Upload Feed
* Make Search calls
* Make Autosuggest calls
* Make Recommendation calls

For details you can refer to the source on `GitHub <https://github.com/unbxd/java-sdk>`_.

.. toctree::
   :maxdepth: 2

Adding the dependency
=====================
Add the following dependency in you pom.xml file::

	<dependency>
	  <groupId>com.unbxd</groupId>
	  <artifactId>java-sdk</artifactId>
	  <version>1.0</version>
	</dependency>
	<repository>
	  <id>java-sdk</id>
	  <name>Unbxd JAVA SDK</name>
	  <url>https://raw.githubusercontent.com/unbxd/java-sdk/master/repository</url>
	</repository>

Configuration
=============
The SDK must be initialized in the following manner::

	Unbxd.configure("Your Site ID", "Your API Key", "Your Secret Key");

If you want all the calls to happen over an HTTPS link do the following::

	Unbxd.configure("Your Site ID", "Your API Key", "Your Secret Key");


Pushing the feed
================

Adding products
***************

The following lines of code would send a couple of products to Unbxd Servers::

	Map<String, Object> product1 = new HashMap<String, Object>();
	product1.put("title", "nice shoes"); // Title of the product
	product1.put("some-field", "test-field-value"); // A custom field
	product1.put("brand", "Adidas");
	product1.put("category", "Sports Shoes");
	product1.put("price", 1100);

	Map<String, Object> product2 = new HashMap<String, Object>();
	product2.put("title", "leather jacket");
	product2.put("some-field", "test-field-value2");
	product2.put("brand", "Adidas");
	product2.put("category", "Jackets");
	product2.put("price", 5000);

	FeedResponse response = Unbxd.getFeedClient()
				.addSchema("some-field", DataType.TEXT) // Adds a custom field
				.addProduct(new FeedProduct("sku1", product)) // sku1 is the Unique Id for the product
				.addProduct(new FeedProduct("sku2", product2))
				.push(false);
You can find the list of pre-configured fields here TODO. The above code adds a field "some-field" to the schema.

Marking custom field as multivalued
###################################

The custom field can be marked to allow multiple values like this::
	
	.addSchema("some-field", DataType.TEXT, true, false) // Adds a custom field

Including custom field in Auto-Suggest
######################################

The custom field can be marked to be included in Auto-Suggest like this::
	
	.addSchema("some-field", DataType.TEXT, false, true) // Adds a custom field

Flush existing product
######################

If you wish to flush out all the existing products while indexing new products you need to call .push(true) like this::

	FeedResponse response = Unbxd.getFeedClient()
				.addSchema("some-field", DataType.TEXT) // Adds a custom field
				.addProduct(new FeedProduct("sku1", product))
				.addProduct(new FeedProduct("sku2", product2))
				.push(true);

Updating products
***************

To update an existing product you call do the following::
	
	Map<String, Object> product = new HashMap<String, Object>();
	product.put("title", "new title");
	
	FeedResponse response = Unbxd.getFeedClient().updateProduct(new FeedProduct("sku1", product)).push(false);

Deleting products
***************

To delete an existing product you call do the following::
	
	FeedResponse response = Unbxd.getFeedClient().deleteProduct("sku1").push(false);

Understanding FeedResponse Object
*********************************

FeedResponse object gives you information about the Feed Upload call. 

* getStatusCode : 200 in case of success. Failure otherwise.
* getMessage : An error message when status code is not 200.
* getUploadID : an identifier for this upload
* getUnknownSchemaFields : fields whose schema was unknown if any
* getFieldErrors : errors that occured at a field level


Using Search Client
===================

Making a Search Call
********************

The following code snippet will make a search call with query "shirts"::
	
	SearchResponse response = Unbxd.getSearchClient().search("shirts", null).execute();

The following code snippet will make a search call with query "shirts" with filters, will sort it on price and return the second page of the results::
	
	SearchResponse response = Unbxd.getSearchClient()
			.search("shirts", null)
			.addFilter("color_fq","black")
			.addFilter("brand_fq", "Ralph Lauren")
			.addSort("price", SearchClient.SortDir.ASC)
			.setPage(2, 10) // 10 products per page
			.execute();

Extra query parameters can be added like this::

	Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("fl", "title"); // will return only product titles
		queryParams.put("stats", "price"); // will include price stats in the response

	SearchResponse response = Unbxd.getSearchClient()
			.search("shirts", queryParams)
			.addFilter("color_fq","black")
			.addFilter("brand_fq", "Ralph Lauren")
			.addSort("price", SearchClient.SortDir.ASC)
			.setPage(2, 10) // 10 products per page
			.execute();


Making a Bucketing Call
***********************

The following code snippet will make a bucketing call with query "shirts" and bucket "category"::
	
	SearchResponse response = Unbxd.getSearchClient().bucket("*", "category", null).execute();

All other options are same as the Search call

Making a Browse Call
********************

The following code snippet will make a browse call with category id "3"::
	
	SearchResponse response = Unbxd.getSearchClient().browse("1", null).execute();

All other options are same as the Search call


Using AutoSuggest Client
========================

The following code snippet will make a autosuggest call with query "shi"::

	AutoSuggestResponse response = Unbxd.getAutoSuggestClient().autosuggest("shi").execute();

Using the Recommendations Client
================================

Get Recently Viewed Products
****************************

::
	
	RecommendationResponse response = Unbxd.getRecommendationsClient().getRecentlyViewed(uid); // uid is value of the cookie : "unbxd.userId"

Get Reccommended For You Products
****************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getRecommendedForYou(uid, "100.0.0.1"); // uid is value of the cookie : "unbxd.userId"

Get More Like This Products
****************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getMoreLikeThis("sku1", uid); // uid is value of the cookie : "unbxd.userId"


Get Also Viewed Products
****************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getAlsoViewed("sku1", uid); // uid is value of the cookie : "unbxd.userId"

Get Also Bought Products
****************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getAlsoBought("sku1", uid); // uid is value of the cookie : "unbxd.userId"

Get Top Selling Products
************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getTopSellers(uid, "100.0.0.1");  // uid is value of the cookie : "unbxd.userId"

Get Top Selling Products within a Category
******************************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getCategoryTopSellers("Shoes", uid, "100.0.0.1");  // uid is value of the cookie : "unbxd.userId"

Get Top Selling Products within a Brand
***************************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getBrandTopSellers("Adidas", uid, "100.0.0.1");  // uid is value of the cookie : "unbxd.userId"

Get Top Selling Products similar to a product
*********************************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getPDPTopSellers("sku1", uid, "100.0.0.1");  // uid is value of the cookie : "unbxd.userId"

Get Products based on Cart
*********************************************
	
::

	RecommendationResponse response = Unbxd.getRecommendationsClient().getCartRecommendations(uid, "100.0.0.1");  // uid is value of the cookie : "unbxd.userId"





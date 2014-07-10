package com.unbxd.client;

import com.unbxd.client.feed.DataType;
import com.unbxd.client.feed.FeedProduct;
import com.unbxd.client.feed.TaxonomyNode;
import com.unbxd.client.feed.exceptions.FeedUploadException;
import com.unbxd.client.feed.response.FeedResponse;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 10/07/14
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedClientTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Unbxd.configure("sdk_test-u1404981344388", "149abee9a65f0d135cd07c90308c54d4", "149abee9a65f0d135cd07c90308c54d4");
    }

    public void test_product_upload() throws ConfigException, FeedUploadException {
        Map<String, Object> product = new HashMap<String, Object>();
        product.put("title", "phodu joote");
        product.put("some-field", "test-field-value");
        product.put("brand", "Adidas");
        product.put("category", "Sports Shoes");
        product.put("price", 1100);

        FeedResponse response = Unbxd.getFeedClient()
                .addSchema("some-field", DataType.TEXT)
                .addProduct(new FeedProduct("testsku", product))
                .addProduct(new FeedProduct("testsku2", product))
                .push(false);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.getMessage());
        Assert.assertNotNull(response.getUploadID());
        Assert.assertEquals(0, response.getUnknownSchemaFields().size());
        Assert.assertEquals(0, response.getFieldErrors().size());

        product = new HashMap<String, Object>();
        product.put("title", "test-product-update");

        response = Unbxd.getFeedClient().updateProduct(new FeedProduct("testsku", product)).push(false);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.getMessage());
        Assert.assertNotNull(response.getUploadID());
        Assert.assertEquals(0, response.getUnknownSchemaFields().size());
        Assert.assertEquals(0, response.getFieldErrors().size());

        response = Unbxd.getFeedClient().deleteProduct("testsku").push(false);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.getMessage());
        Assert.assertNotNull(response.getUploadID());
        Assert.assertEquals(0, response.getUnknownSchemaFields().size());
        Assert.assertEquals(0, response.getFieldErrors().size());
    }

    public void test_product_upload_should_fail_unknown_fields() throws ConfigException, FeedUploadException {
        Map<String, Object> product = new HashMap<String, Object>();
        product.put("title", "phodu joote");
        product.put("some-unknown-field", "test-field-value");
        product.put("brand", "Adidas");
        product.put("category", "Sports Shoes");
        product.put("price", 1100);

        FeedResponse response = Unbxd.getFeedClient()
                .addProduct(new FeedProduct("testsku3", product))
                .push(false);
        Assert.assertNotNull(response);
        Assert.assertEquals(602, response.getStatusCode());
        Assert.assertNotNull(response.getMessage());
        Assert.assertNotNull(response.getUploadID());
        Assert.assertEquals(1, response.getUnknownSchemaFields().size());
        Assert.assertEquals("some-unknown-field", response.getUnknownSchemaFields().get(0));
        Assert.assertEquals(0, response.getFieldErrors().size());
    }

    public void test_product_upload_should_fail_field_error() throws ConfigException, FeedUploadException {
        Map<String, Object> product = new HashMap<String, Object>();
        product.put("title", "phodu joote");
        product.put("brand", "Adidas");
        product.put("category", "Sports Shoes");
        product.put("price", "1100abc");

        FeedResponse response = Unbxd.getFeedClient()
                .addProduct(new FeedProduct("testsku3", product))
                .push(false);
        Assert.assertNotNull(response);
        Assert.assertEquals(401, response.getStatusCode());
        Assert.assertNotNull(response.getMessage());
        Assert.assertNotNull(response.getUploadID());
        Assert.assertEquals(0, response.getUnknownSchemaFields().size());
        Assert.assertEquals(1, response.getFieldErrors().size());
        Assert.assertEquals("price", response.getFieldErrors().get(0).getFieldName());
        Assert.assertEquals("1100abc", response.getFieldErrors().get(0).getFieldValue());
        Assert.assertEquals(DataType.DECIMAL, response.getFieldErrors().get(0).getDataType());
        Assert.assertNotNull(response.getFieldErrors().get(0).getMessage());
        Assert.assertEquals(402, response.getFieldErrors().get(0).getErrorCode());
        Assert.assertNotEquals(0, response.getFieldErrors().get(0).getRowNum());
        Assert.assertNotEquals(0, response.getFieldErrors().get(0).getColNum());
    }

    public void test_taxonomy_upload() throws ConfigException, FeedUploadException {
        FeedResponse response = Unbxd.getFeedClient()
                .addTaxonomyNode(new TaxonomyNode("1", "Men", null))
                .addTaxonomyNode(new TaxonomyNode("2", "Shoes", Arrays.asList("1")))
                .addTaxonomyMapping("testsku2", Arrays.asList("1", "2"))
                .push(false);
        System.out.println(response.toString());
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.getMessage());
        Assert.assertNotNull(response.getUploadID());
        Assert.assertEquals(0, response.getUnknownSchemaFields().size());
        Assert.assertEquals(0, response.getFieldErrors().size());
    }
}

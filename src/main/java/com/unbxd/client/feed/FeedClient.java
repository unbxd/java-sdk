package com.unbxd.client.feed;

import com.unbxd.client.ConnectionManager;
import com.unbxd.client.feed.exceptions.FeedInputException;
import com.unbxd.client.feed.exceptions.FeedUploadException;
import com.unbxd.client.feed.response.FeedResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedClient {

    private static final Logger LOG = LoggerFactory.getLogger(FeedClient.class);

    private String siteKey;
    private String secretKey;
    private boolean secure;

    private List<FeedField> _fields;
    private Map<String, FeedProduct> _addedDocs;
    private Map<String, FeedProduct> _updatedDocs;
    private Set<String> _deletedDocs;

    private List<TaxonomyNode> _taxonomyNodes;
    private Map<String, List<String>> _taxonomyMappings;

    protected FeedClient(String siteKey, String secretKey, boolean secure){
        this.siteKey = siteKey;
        this.secretKey = secretKey;
        this.secure = secure;

        _fields = new ArrayList<FeedField>();
        _addedDocs = new HashMap<String, FeedProduct>();
        _updatedDocs = new HashMap<String, FeedProduct>();
        _deletedDocs = new HashSet<String>();

        _taxonomyNodes = new ArrayList<TaxonomyNode>();
        _taxonomyMappings = new HashMap<String, List<String>>();
    }

    private String getFeedUrl(){
        return (secure ? "https://" : "http://") + "feed.unbxdapi.com/upload/v2/" + secretKey + "/" + siteKey;
    }

    public FeedClient addSchema(String fieldName, DataType datatype, boolean multivalued, boolean autosuggest){
        _fields.add(new FeedField(fieldName, datatype, multivalued, autosuggest));
        return this;
    }

    public FeedClient addSchema(String fieldName, DataType datatype){
        _fields.add(new FeedField(fieldName, datatype, false, false));
        return this;
    }

    public FeedClient addProduct(FeedProduct product) {
        _addedDocs.put(product.getUniqueId(), product);

        return this;
    }

    public FeedClient addProducts(List<FeedProduct> products) {
        for(FeedProduct product : products){
            this.addProduct(product);
        }

        return this;
    }

    public FeedClient addVariant(String parentUniqueId, Map<String, Object> variantAttributes) throws FeedInputException {
        if(!_addedDocs.containsKey(parentUniqueId)){
            throw new FeedInputException("Parent product needs to be added");
        }

        _addedDocs.get(parentUniqueId).addAssociatedProduct(variantAttributes);

        return this;
    }

    public FeedClient addVariants(String parentUniqueId, List<Map<String, Object>> variants) throws FeedInputException {
        for(Map<String, Object> variant : variants){
            this.addVariant(parentUniqueId, variant);
        }

        return this;
    }

    public FeedClient updateProduct(FeedProduct productDelta) {
        _updatedDocs.put(productDelta.getUniqueId(), productDelta);

        return this;
    }

    public FeedClient updateProducts(List<FeedProduct> productsDeltas) {
        for(FeedProduct product : productsDeltas){
            this.updateProduct(product);
        }

        return this;
    }

    public FeedClient deleteProduct(String uniqueId){
        _deletedDocs.add(uniqueId);

        return this;
    }

    public FeedClient deleteProducts(List<String> uniqueIds){
        _deletedDocs.addAll(uniqueIds);

        return this;
    }

    public FeedClient addTaxonomyNode(TaxonomyNode node){
        _taxonomyNodes.add(node);

        return this;
    }

    public FeedClient addTaxonomyNodes(List<TaxonomyNode> nodes){
        _taxonomyNodes.addAll(nodes);

        return this;
    }

    public FeedClient addTaxonomyMapping(String uniqueId, List<String> nodeIds){
        _taxonomyMappings.put(uniqueId, nodeIds);

        return this;
    }

    public FeedClient addTaxonomyMappings(Map<String, List<String>> mappings){
        _taxonomyMappings.putAll(mappings);

        return this;
    }

    // Uploads the products. Has to handle file creation etc.
    public FeedResponse push(boolean isFullImport) throws FeedUploadException {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(ConnectionManager.getConnectionManager()).build();

        Document doc = new FeedFile(_fields, _addedDocs.values(), _updatedDocs.values(), _deletedDocs, _taxonomyNodes, _taxonomyMappings).getDoc();

        try {
            long t = new Date().getTime();

            File file = File.createTempFile(siteKey, ".xml");
            FileOutputStream fos = new FileOutputStream(file);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);

            LOG.debug("Stored at : " + file.getAbsolutePath());

            String url = getFeedUrl();
            if(isFullImport){
                url += "?fullimport=true";
            }

            HttpPost post = new HttpPost(url);

            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            entity.addPart("file", new FileBody(file));
            post.setEntity(entity.build());

            HttpResponse response = httpClient.execute(post);

            t = new Date().getTime() - t;
            LOG.debug("Took : " + t + " millisecs");

            if(response.getStatusLine().getStatusCode() == 200){
                try{
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> map = mapper.readValue(new InputStreamReader(response.getEntity().getContent()), Map.class);
                    return new FeedResponse(map);
                }catch (Exception e){
                    LOG.error("Failed to parse response", e);
                    throw new FeedUploadException(e);
                }
            }else {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }

                String responseText = sb.toString();

                LOG.error(responseText);
                throw new FeedUploadException(responseText);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new FeedUploadException(e);
        } catch (TransformerConfigurationException e) {
            LOG.error(e.getMessage(), e);
            throw new FeedUploadException(e);
        } catch (TransformerException e) {
            LOG.error(e.getMessage(), e);
            throw new FeedUploadException(e);
        }
    }

}

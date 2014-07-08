package com.unbxd.client.feed;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedFile {
    private Document _doc;

    public FeedFile(Collection<FeedField> fields, Collection<FeedProduct> addedDocs, Collection<FeedProduct> updatedDocs, Collection<String> deletedDocs){
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            _doc = docBuilder.newDocument();
            Element root = _doc.createElement("feed");
            _doc.appendChild(root);

            Element catalog = _doc.createElement("catalog");
            root.appendChild(catalog);

            if(fields.size() > 0)
                writeSchema(fields, catalog);

            if(addedDocs.size() > 0){
                Element addNode = _doc.createElement("add");
                catalog.appendChild(addNode);
                writeAdd(addedDocs, addNode);
            }

            if(updatedDocs.size() > 0){
                Element updateNode = _doc.createElement("update");
                catalog.appendChild(updateNode);
                writeUpdate(updatedDocs, updateNode);
            }

            if(deletedDocs.size() > 0){
                Element deleteNode = _doc.createElement("delete");
                catalog.appendChild(deleteNode);
                writeDelete(deletedDocs, deleteNode);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void writeSchema(Collection<FeedField> fields, Element parent){
        for(FeedField field : fields){
            Element schema = _doc.createElement("schema");

            Element fieldName = _doc.createElement("fieldName");
            fieldName.appendChild(_doc.createTextNode(field.getName()));
            schema.appendChild(fieldName);

            Element dataType = _doc.createElement("dataType");
            dataType.appendChild(_doc.createTextNode(field.getDataType().getValue()));
            schema.appendChild(dataType);

            Element multiValued = _doc.createElement("multiValued");
            multiValued.appendChild(_doc.createTextNode(new Boolean(field.isMultiValued()).toString()));
            schema.appendChild(multiValued);

            Element autoSuggest = _doc.createElement("autoSuggest");
            autoSuggest.appendChild(_doc.createTextNode(new Boolean(field.isAutoSuggest()).toString()));
            schema.appendChild(autoSuggest);

            parent.appendChild(schema);
        }
    }

    private void writeAttribute(Element items, String field, Object o, boolean associated){
        Collection<Object> values;
        if(o instanceof Collection){
            values = (Collection<Object>) o;
        }else{
            values = Arrays.asList(o);
        }

        for(Object value : values){
            if(value == null) continue;

            Element e = _doc.createElement(field + (associated ? "Associated" : ""));

            String str = value.toString();
            str = str.replaceAll("[\u0000-\u001f]", "");
            str = StringEscapeUtils.escapeXml(str);
            e.appendChild(_doc.createTextNode(str));
            items.appendChild(e);
        }
    }

    private void writeAdd(Collection<FeedProduct> addedDocs, Element parent){
        for(FeedProduct product : addedDocs){
            Element items = _doc.createElement("items");

            for(String field : product.getAttributes().keySet()){
                writeAttribute(items, field, product.get(field), false);
            }

            if(product.getAssociatedProducts() != null && product.getAssociatedProducts().size() > 0){
                for(Map<String, Object> associatedProduct : product.getAssociatedProducts()){
                    Element associatedItems = _doc.createElement("associatedProducts");
                    for(String field : associatedProduct.keySet()){
                        writeAttribute(associatedItems, field, associatedProduct.get(field), true);
                    }
                }
            }

            parent.appendChild(items);
        }
    }

    private void writeUpdate(Collection<FeedProduct> updatedDocs, Element parent){
        for(FeedProduct product : updatedDocs){
            Element items = _doc.createElement("items");

            for(String field : product.getAttributes().keySet()){
                writeAttribute(items, field, product.get(field), false);
            }

            parent.appendChild(items);
        }
    }

    private void writeDelete(Collection<String> deletedDocs, Element parent){
        for(String uniqueId : deletedDocs){
            Element items = _doc.createElement("items");

            Element e = _doc.createElement("uniqueId");
            e.appendChild(_doc.createTextNode(uniqueId));
            items.appendChild(e);

            parent.appendChild(items);
        }
    }

    public Document getDoc(){
        return _doc;
    }
}

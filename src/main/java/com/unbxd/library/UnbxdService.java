package com.unbxd.library;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.net.URLEncoder;

class UnbxdService {

	private String url;
	
	/**
	 * 
	 * 
	 * @param sortMap
	 * @return sort=fieldname1 asc,fieldname2 desc
	 * @throws UnsupportedEncodingException 
	 */
		

	private String getSort(Map<String, Integer> sortMap) throws UnbxdException {
		
		try
		{
			StringBuilder sort= new StringBuilder();
		Iterator sortIterator = sortMap.entrySet().iterator();
		Integer asc = 1;
		Integer desc = -1;
		while (sortIterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) sortIterator.next();
			sort.append(pairs.getKey());
			sort.append(" ");
			if (pairs.getValue() == asc) {
				sort.append("asc");
			} else if (pairs.getValue() == desc) {
				sort.append("desc");
			}
			if (sortIterator.hasNext()) {
				sort.append(",");
			}
		}
		
		return URLEncoder.encode(sort.toString(), "UTF-8") ;
		//return sort;
	}
		catch(Exception e)
		 {
			 throw new UnbxdException(e.getMessage());
		 }

	}
	/**
	 * (*:* AND (attribute1:attribute1Value1 OR attribute1:attribute1Value2) AND
	 * (rangeFilter:[from TO to] OR rangeFilter:[* TO to]))
	 * 
	 * @param attributeFilters
	 * @param rangeFilter
	 * @return should be filter value
	 */
	private String getFilter(Map<String, List<String>> attributeFilters,
			Map<String, List<RangeFilter>> rangeFilter) throws UnbxdException {
		try
		{
			
			StringBuilder filter= new StringBuilder();
			filter.append("(*:* AND (");
		Iterator mapIterator = attributeFilters.entrySet().iterator();
		Iterator rangeIterator = rangeFilter.entrySet().iterator();

		while (mapIterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) mapIterator.next();
			Iterator<String> iterator = ((List<String>) pairs.getValue())
					.iterator();
			while (iterator.hasNext()) {
				filter.append(pairs.getKey());
				filter.append(":\"");
				filter.append(iterator.next()).append("\"");
				if (iterator.hasNext()) {
					filter.append(" OR ");
				} else {
					filter.append(")");
				}
			}
			if (mapIterator.hasNext()) {
				filter .append(" AND (");
			}
			// mapIterator.remove(); // avoids a ConcurrentModificationException
		}

		while (rangeIterator.hasNext()) {
			
			if (filter.toString() != "(*:* AND (") {
				filter.append(" AND (");
			}

			Map.Entry pair = (Map.Entry) rangeIterator.next();
			
			Iterator<RangeFilter> rangeListIterator = ((List<RangeFilter>) pair.getValue()).iterator();
			while (rangeListIterator.hasNext()) {
				filter.append(pair.getKey()).append(":");
				RangeFilter data = rangeListIterator.next();
				filter.append("[").append(((data.getFrom() != null) ? data.getFrom() : "*"));
				filter.append(" TO ");
				filter.append(((data.getTo() != null) ? data.getTo() : "*"));
				filter.append("]");
				if (rangeListIterator.hasNext()) {
					filter.append(" OR ");
				} else {
					filter.append(")");
				}
				
			}

		}
		filter.append(")");
		return URLEncoder.encode(filter.toString(), "UTF-8") ;
		//return filter;
		}
		catch(Exception e)
		 {
			 throw new UnbxdException(e.getMessage());
		 }
	}



	private String getQueryParam(UnbxdParams params) throws UnbxdException {
		if (params.getRuleSet().equals("browse")) {
			
			return "?category-id=" + params.getCategoryId();
		} 
		
		else if (params.getRuleSet().equals("filter")) {

			return "?cond="
					+ this.getFilter(params.getFilter(),
							params.getRangeFilter());
		} else {
			
			return "?q=" + ((params.getQuery() != null) && (params.getQuery() != "") ? params.getQuery() : "*");
		}

	}

	private String prepareUrl(UnbxdParams params, String address)
			throws UnbxdException {
		
		try
		{
		StringBuilder url = new StringBuilder();
		url.append(address).append("/").append(params.getRuleSet());

		url.append(this.getQueryParam(params));
		
		url.append("&start=").append(((params.getStart() != 0) ? params.getStart() : 0)).append("&rows=").append(((params.getLimit() != 0) ? params.getLimit() : 20));
		
		if (!(params.getFilter().isEmpty()) || !(params.getRangeFilter().isEmpty()))
		{
			
			url.append("&filter=").append(this.getFilter(params.getFilter(), params.getRangeFilter()));
		}
		
		
		if (!(params.getSort().isEmpty())) {
			
			url.append("&sort=").append(this.getSort(params.getSort()));
		}

		url.append("&wt=json");
		// TODO: Get filters, sorting, other options from params

		return url.toString();
	}
		catch(Exception e)
		 {
			 throw new UnbxdException(e.getMessage());
		 }
		
	}

	UnbxdResult search(UnbxdParams params, String address, boolean spellCheck) throws UnbxdException {
		
		 GetMethod method;
		 String url;
		 
		 try{
		url = prepareUrl(params, address);
		/*
		 * TODO: create UnbxdResponse class and add the following functionality:
		 * 1. json_decode 2. setSpellCheckQuery 3. getTotalHits 4.
		 * getSpellSuggestion
		 */
		if(params.isDebug())
		{
			//log url
			
		}
		HttpClient client = new HttpClient();
		// Create a method instance.
		method = new GetMethod(url);

		/*method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));*/

			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				throw new UnbxdException("Method failed: " + method.getStatusLine());
			}
			InputStream response = null;
			response = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response));
			String jsonString = "";
			String line;
			while ((line = br.readLine()) != null) {
				jsonString += line;
			}
			br.close();
			// Construct a JSONObject from a source JSON text string.
			JSONObject result = new JSONObject(jsonString);
			
			UnbxdResult unbxdResult = new UnbxdResult(result);
			if(spellCheck)
			{
				unbxdResult.setSpellCheckQuery(params.getQuery());
			}
			if((unbxdResult!=null) && !(spellCheck) && (unbxdResult.getTotalHits() == 0) && (unbxdResult.getSpellSuggestion() != null))
			{
				params.setQuery(unbxdResult.getSpellSuggestion());
				return this.search(params, address, true);
			}
			return unbxdResult;
			
		} 
		 catch(Exception e)
		 {
			 throw new UnbxdException(e.getMessage() , 400);
		 }
		 

	}
}

package com.unbxd.library;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class UnbxdApi {
	private String siteName;
	private String apiKey;
	private String transport;
	private UnbxdParams params = new UnbxdParams();
	private UnbxdService unbxdService = new UnbxdService();

	public UnbxdApi(String siteName, String apiKey) throws UnbxdException {
			this(siteName, apiKey, "http");
	
	}

	public UnbxdApi(String siteName, String apiKey, String transport) throws UnbxdException {
		
		this.setSiteName(siteName);
		this.setApiKey(apiKey);
		this.setTransport(transport);
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) throws UnbxdException{
		if(siteName == null)
		{
			throw new UnbxdException(UnbxdException.getNOSITENAME());
		}
		this.siteName = siteName;
	}

	public String getApiKey() {
		
		return apiKey;
	}

	public void setApiKey(String apiKey) throws UnbxdException {
		if(apiKey == null)
		{
			throw new UnbxdException(UnbxdException.getNOAPIKEY());
		}
		this.apiKey = apiKey;
	}

	public UnbxdApi prepareSearch(String query) {
		this.params.setRuleSet("search");
		this.params.setQuery(query);
		return this;
	}

	public UnbxdApi prepareBrowse(String categoryId) {
		this.params.setRuleSet("browse");
		this.params.setCategoryId(categoryId);
		return this;
	}

	public UnbxdApi setField(String field) {
		this.params.setFields(field);
		return this;
	}

	public UnbxdApi setRuleSet(String ruleset) {
		this.params.setRuleSet(ruleset);
		return this;
	}

	public UnbxdApi addSort(String field, Integer value) throws UnbxdException {
		try
		{
		this.params.setSort(field, value);
		return this;
		}
		catch(Exception e)
		{
			throw new UnbxdException(e.getMessage());
		}
	}

	public UnbxdApi addAttributeFilter(String field, List<String> value) throws UnbxdException {
		try
		{
		this.params.setFilter(field, value);
		return this;
		}
		catch(Exception e)
		{
			throw new UnbxdException(e.getMessage());
		}
	}

	public UnbxdApi addAttributeFilter(Map<String, List<String>> attributeFilters) throws UnbxdException {
		try{
			this.params.setFilter(attributeFilters);
		return this;
		}
		catch(Exception e)
		{
			throw new UnbxdException(e.getMessage());
		}
	}

	public UnbxdApi addRangeFilter(String field, List<RangeFilter> value) throws UnbxdException {
		try
		{
		this.params.setRangeFilter(field, value);
		return this;
		}
		catch(Exception e)
		{
			throw new UnbxdException(e.getMessage());
		}
		
	}

	public UnbxdApi addRangeFilter(Map<String, List<RangeFilter>> rangeFilter) throws UnbxdException {
		try
		{
		this.params.setRangeFilter(rangeFilter);
		return this;
		}
		
		catch(Exception e)
		{
			throw new UnbxdException(e.getMessage());
		}
		
	}

	public UnbxdApi setPage(int page) {
		this.params.setPage(page);
		return this;
	}

	public UnbxdApi setLimit(int limit) {
		this.params.setLimit(limit);
		return this;
	}

	public UnbxdApi setDebug(boolean debug) {
		this.params.setDebug(debug);
		return this;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}
	
	public UnbxdApi setUserId(String uid){
		this.params.setUserId(uid);
		return this;
	}
	
	public UnbxdApi setIP(String ip){
		this.params.setUserIP(ip);
		return this;
	}
	

	public UnbxdResult getResults() throws UnbxdException {
		String address = (this.getTransport() != null ? this.getTransport()
				: "http")
				+ "://"
				+ this.getSiteName()
				+ ".search.unbxdapi.com/" + this.getApiKey();
		UnbxdResult response = unbxdService.search(this.params, address, false);
		return response;
	}

}

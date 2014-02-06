package com.unbxd.library;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UnbxdResult {
	
	private JSONObject result;


	private String spellCheckQuery;
	private JSONObject response;
	private int totalHits;
	private int took;
	
	
	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public String getSpellCheckQuery() {
		return spellCheckQuery;
	}

	public void setSpellCheckQuery(String spellCheckQuery) {
		this.spellCheckQuery = spellCheckQuery;
	}

	public JSONObject getResponse() {
		return response;
	}

	public void setResponse(JSONObject response) {
		this.response = response;
	}

	public int getTook() {
		return took;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTook(int took) {
		this.took = took;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}


	
	UnbxdResult(JSONObject response) throws UnbxdException, JSONException
	{
		this.response = response;
		if(!(this.response == null))
		{
			this.totalHits = response.getJSONObject("response").getInt("numberOfProducts");
			this.took = (response.getJSONObject("searchMetaData").has("queryTime") ? response.getJSONObject("searchMetaData").getInt("queryTime") : 0 );
			
		}
		//rewind()
		//init()
	}
	
	public JSONArray getProducts() throws JSONException
	{
		return ((this.response.getJSONObject("response").has("products")) ? this.response.getJSONObject("response").getJSONArray("products") : null);
	}
	
	public boolean hasFacets() throws JSONException
	{
		return this.response.has("facets");
	}
	
	public JSONObject getFacets() throws JSONException
	{
		
		return ((this.response.has("facets")) ? this.response.getJSONObject("facets") : null);
	}
	
	public String getSpellSuggestion() throws JSONException
	{
		String spellSuggestion;
		if(this.response.has("didYouMean"))
		{
			JSONArray spellCheck = this.response.getJSONArray("didYouMean");
			for(int suggestion=0; suggestion<spellCheck.length(); suggestion++)
			{
				if(spellCheck.getJSONObject(suggestion).has("suggestion"))
				{
					return(spellCheck.getJSONObject(suggestion).getString("suggestion"));
					
				}
				
			}
		}
		
		return null;
	}
	
	public String getQuery() throws JSONException
	{
		String query="";
		//return (this.response.has("stats") ? this.response.getJSONObject("stats") : null);
		if(this.response.has("queryParams"))
		{
			return (this.response.getJSONObject("queryParams").has("q") ? this.response.getJSONObject("queryParams").getString("q") : "");
		}
		return query;
		
	}
	public int getRows() throws JSONException
	{
		int rows = 0;
		//return (this.response.has("stats") ? this.response.getJSONObject("stats") : null);
		if(this.response.has("queryParams"))
		{
			
			return (this.response.getJSONObject("queryParams").has("rows") ? this.response.getJSONObject("queryParams").getInt("rows") : 0);
		}
		return rows;
		
	}
	
	public int getStart() throws JSONException
	{
		int start = 0;
		//return (this.response.has("stats") ? this.response.getJSONObject("stats") : null);
		if(this.response.has("queryParams"))
		{
			
			return (this.response.getJSONObject("queryParams").has("start") ? this.response.getJSONObject("queryParams").getInt("start") : 0);
		}
		return start;
		
	}
	
	public JSONObject getStats() throws JSONException
	{
		return (this.response.has("stats") ? this.response.getJSONObject("stats") : null);
	}
	

}

package com.unbxd.library;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


class UnbxdParams {
	
	private int timeout;
	private boolean debug;
	private Map<String, List<String>> filter = new HashMap<String, List<String>>();
	private Map<String, List<RangeFilter>> rangeFilter = new HashMap<String, List<RangeFilter>>();
	private Map<String, Integer> sort = new HashMap<String, Integer>();
	private String ruleSet;
	private String query;
	private String categoryId;
	private int start;
	private int limit;
	
	
	
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	
	public Map<String, Integer> getSort() {
		return sort;
	}
	public void setSort(Map<String, Integer> sort) {
		this.sort = sort;
	}
	public void setSort(String field, Integer value) {
		this.sort.put(field, value);
	}
	
	public Map<String, List<String>> getFilter() {
		return filter;
	}
	
	public void setFilter(Map<String, List<String>> filter) {
		
		Iterator iterator = filter.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) iterator.next();
			
		this.setFilter((String)pairs.getKey(), (List<String>) pairs.getValue());
		}
	}
	
	public void setFilter(String field, List<String> value)
	{
		this.filter.put(field, value);
	}
	public Map<String, List<RangeFilter>> getRangeFilter() {
		return rangeFilter;
	}
	public void setRangeFilter(Map<String, List<RangeFilter>> rangeFilter) {
		Iterator iterator = rangeFilter.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) iterator.next();
			
		this.setRangeFilter((String)pairs.getKey(), (List<RangeFilter>) pairs.getValue());
		}
	}
	public void setRangeFilter(String field, List<RangeFilter> value) {
		this.rangeFilter.put(field, value);
	}
	public String getRuleSet() {
		return ruleSet;
	}
	public void setRuleSet(String ruleSet) {
		this.ruleSet = ruleSet;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	
		    
	}
		    
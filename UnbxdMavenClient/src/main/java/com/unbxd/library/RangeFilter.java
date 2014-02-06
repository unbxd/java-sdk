package com.unbxd.library;


public class RangeFilter<T extends Number> {
	
	T from;
	T to;
	
	public T getFrom() {
		return from;
	}
	public void setFrom(T from) {
		this.from = from;
	}
	
	public T getTo() {
		return to;
	}
	public void setTo(T to) {
		this.to = to;
	}
	
}

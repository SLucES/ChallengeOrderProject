package com.moteefe.challenge.domain;

import java.io.Serializable;
import java.util.List;

public class OrderParameters implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1425505060851351945L;
	
	String region;
	List<Item> items;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}

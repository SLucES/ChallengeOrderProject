package com.moteefe.challenge.dto;

import java.io.Serializable;
import java.util.List;

public class OrderParametersDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 949083073578977599L;
	
	String region;
	List<ItemDto> items;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public List<ItemDto> getItems() {
		return items;
	}
	public void setItems(List<ItemDto> items) {
		this.items = items;
	}
	
	
}

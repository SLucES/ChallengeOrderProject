package com.moteefe.challenge.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShipmentDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3135882575898254292L;
	
	String supplier;
	OffsetDateTime deliveryDate;
	List<ItemDto> items;
	
	@JsonProperty("supplier")
	public String getSupplier() {
		return supplier;
	}
	@JsonProperty("supplier")
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@JsonProperty("delivery_date")
	public OffsetDateTime getDeliveryDate() {
		return deliveryDate;
	}
	@JsonProperty("delivery_date")
	public void setDeliveryDate(OffsetDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@JsonProperty("items")
	public List<ItemDto> getItems() {
		return items;
	}
	@JsonProperty("items")
	public void setItems(List<ItemDto> items) {
		this.items = items;
	}
	
	
}

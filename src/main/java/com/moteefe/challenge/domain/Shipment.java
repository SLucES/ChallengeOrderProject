package com.moteefe.challenge.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class Shipment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2976055628479475773L;

	String supplier;
	OffsetDateTime deliveryDate;
	List<Item> items;
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public OffsetDateTime getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(OffsetDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}

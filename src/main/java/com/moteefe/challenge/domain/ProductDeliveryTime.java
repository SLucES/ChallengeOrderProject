package com.moteefe.challenge.domain;

import java.io.Serializable;

public class ProductDeliveryTime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8882732525848826578L;
	
	String productName;
	String supplier;
	String location;
	Long days;
	Long inStock;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getDays() {
		return days;
	}
	public void setDays(Long days) {
		this.days = days;
	}
	public Long getInStock() {
		return inStock;
	}
	public void setInStock(Long inStock) {
		this.inStock = inStock;
	}
}

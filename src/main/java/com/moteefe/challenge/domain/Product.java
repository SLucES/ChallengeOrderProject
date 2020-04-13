package com.moteefe.challenge.domain;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5443916876835884232L;
	
	String productName;
	String supplier;
	List<DeliveryTime> deliveryTimes;
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
	public List<DeliveryTime> getDeliveryTimes() {
		return deliveryTimes;
	}
	public void setDeliveryTimes(List<DeliveryTime> deliveryTimes) {
		this.deliveryTimes = deliveryTimes;
	}
	public Long getInStock() {
		return inStock;
	}
	public void setInStock(Long inStock) {
		this.inStock = inStock;
	}
	
	
}

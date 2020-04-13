package com.moteefe.challenge.service;

import java.util.List;

import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.domain.Product;
import com.moteefe.challenge.domain.ProductDeliveryTime;

public interface DbService {

	public Product retrieveProductByProductNameAndSupplier(String productName, String supplier);
	
	public List<Product> retrieveProductsByProductName(String productName);
	
	public ProductDeliveryTime[] orderProductListByLocationAsc(List<Product> products, String location);
	
	public List<String> findCommonSupplierForItems(List<Item> items);
}

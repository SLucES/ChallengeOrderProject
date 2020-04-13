package com.moteefe.challenge.helper;

import java.util.ArrayList;
import java.util.List;

import com.moteefe.challenge.domain.DeliveryTime;
import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.domain.Product;

public class DbServiceTestHelper {

	public static List<Product> blackMugsProductList(){
		List<Product> products = new ArrayList<>();
		
		List<DeliveryTime> deliveryTimesSU = new ArrayList<>();
		List<DeliveryTime> deliveryTimesS4U = new ArrayList<>();
		
		DeliveryTime dtEU = new DeliveryTime();
    	dtEU.setDays(1L);
    	dtEU.setLocation("eu");
    	
    	DeliveryTime dtUK = new DeliveryTime();
    	dtUK.setDays(2L);
    	dtUK.setLocation("uk");
    	
    	DeliveryTime dtUS7 = new DeliveryTime();
    	dtUS7.setDays(7L);
    	dtUS7.setLocation("us");
    	
    	DeliveryTime dtUS6 = new DeliveryTime();
    	dtUS6.setDays(6L);
    	dtUS6.setLocation("us");
    	
    	deliveryTimesSU.add(dtEU);
    	deliveryTimesSU.add(dtUK);
    	deliveryTimesSU.add(dtUS7);
    	
    	deliveryTimesS4U.add(dtEU);
    	deliveryTimesS4U.add(dtUK);
    	deliveryTimesS4U.add(dtUS6);
    	
    	Product blackMugShirtsUnlimited = new Product();
    	blackMugShirtsUnlimited.setProductName("black_mug");
    	blackMugShirtsUnlimited.setSupplier("Shirts Unlimited");
    	blackMugShirtsUnlimited.setInStock(4L);
    	blackMugShirtsUnlimited.setDeliveryTimes(deliveryTimesSU);
    	
    	Product blackMugShirts4U = new Product();
    	blackMugShirts4U.setProductName("black_mug");
    	blackMugShirts4U.setSupplier("Shirts4U");
    	blackMugShirts4U.setInStock(3L);
    	blackMugShirts4U.setDeliveryTimes(deliveryTimesS4U);
    	
    	products.add(blackMugShirtsUnlimited);
    	products.add(blackMugShirts4U);
    	
    	return products;
	}
	
	public static List<Item> shirts4UItems(){
		List<Item> items = new ArrayList<>();
		
		Item blackMug = new Item();
		blackMug.setCount(3L);
		blackMug.setTitle("black_mug");
		
		Item pinkTShirt = new Item();
		pinkTShirt.setCount(8L);
		pinkTShirt.setTitle("pink_t-shirt");
		
		items.add(blackMug);
		items.add(pinkTShirt);
		
		return items;
	}
}

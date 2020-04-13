package com.moteefe.challenge.util;

import java.util.List;

import com.moteefe.challenge.domain.DeliveryTime;
import com.moteefe.challenge.domain.Product;
import com.moteefe.challenge.domain.ProductDeliveryTime;

public class ProductConverter {

	public static ProductDeliveryTime convertToProductDeliveryTime(Product product, String location) {
		ProductDeliveryTime productDeliveryTime = new ProductDeliveryTime();
		if(product != null) {
			productDeliveryTime.setProductName(product.getProductName());
			productDeliveryTime.setSupplier(product.getSupplier());
			productDeliveryTime.setLocation(location);
			
			List<DeliveryTime> deliveryTimes = product.getDeliveryTimes();
			DeliveryTime deliveryTime = getDeliveryTimeByLocation(deliveryTimes, location);
			if(deliveryTime != null) {
				productDeliveryTime.setDays(deliveryTime.getDays());
				productDeliveryTime.setInStock(product.getInStock());
			} else {
				return null;
			}
		}
		
		return productDeliveryTime;
	}
	
	public static DeliveryTime getDeliveryTimeByLocation(List<DeliveryTime> deliveryTimes, String location){
	    if(deliveryTimes.stream().filter(o -> o.getLocation().contentEquals(location)).findFirst().isPresent()) {
	    	return deliveryTimes.stream().filter(o -> o.getLocation().contentEquals(location)).findFirst().get();
	    } else {
	    	return null;
	    }
	}
}



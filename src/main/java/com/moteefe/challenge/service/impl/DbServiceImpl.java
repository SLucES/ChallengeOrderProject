package com.moteefe.challenge.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.moteefe.challenge.domain.DeliveryTime;
import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.domain.Product;
import com.moteefe.challenge.domain.ProductDeliveryTime;
import com.moteefe.challenge.service.DbService;
import com.moteefe.challenge.util.ProductConverter;

@Service("dbService")
public class DbServiceImpl implements DbService{
	
	String csvSplitter = ",";
	String deliverySplitter = ";";
	String deliveryTimeSplitter = ":";

	
	public Product retrieveProductByProductNameAndSupplier(String productName, String supplier) {
		Product product = null;
		
		BufferedReader csvReader;
		try {
			csvReader = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("product_db.csv").getFile()));
			String productString;
			while ((productString = csvReader.readLine()) != null) {
				productString = productString.replace(", ", ";");
			    String[] rowData = productString.split(csvSplitter);
			    
			    if(rowData[0].toUpperCase().equals(productName.toUpperCase())) {
			    	if(rowData[1].toUpperCase().equals(supplier.toUpperCase())) {
				    	product = createProductFromRowData(rowData);

				    	csvReader.close();
				    	return product;
			    	}
			    }
			   
			}
			csvReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
	public List<String> findCommonSupplierForItems(List<Item> items) {
		HashMap<String, Integer> suppliersItemCount = new HashMap<String, Integer>();
		for(Item item : items) {
			List<Product> products = retrieveProductsByProductName(item.getTitle());
			for(Product product : products) {
				suppliersItemCount.put(product.getSupplier(), suppliersItemCount.getOrDefault(product.getSupplier(), 0) + 1);
			}
		}
		
		Integer itemCount = items.size();
		List<String> suppliers = new ArrayList<>();
		suppliersItemCount.forEach((k,v)->{
					if(v.equals(itemCount)) {
						suppliers.add(k);
					}
					
					});
		return suppliers; 
	}
	
	public List<Product> retrieveProductsByProductName(String productName) {
		List<Product> products = new ArrayList<>();
		
		BufferedReader csvReader;
		try {
			csvReader = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("product_db.csv").getFile()));
			String productString;
			productString = csvReader.readLine();
			while ((productString = csvReader.readLine()) != null) {
				productString = productString.replace(", ", ";");
			    String[] rowData = productString.split(csvSplitter);
			    
			    if(rowData[0].toUpperCase().equals(productName.toUpperCase())) {
			    	 Product product = createProductFromRowData(rowData);
					 products.add(product);
			    }
			   
			}
			csvReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return products;
	}
	
	public ProductDeliveryTime[] orderProductListByLocationAsc(List<Product> products, String location) {
		if(products != null && location != null) {
			List<ProductDeliveryTime> productDeliveryTimes = products.stream().map(product -> ProductConverter.convertToProductDeliveryTime(product, location)).collect(Collectors.toList());
			if(!productDeliveryTimes.contains(null)) {
				ProductDeliveryTime[] productDeliveryTimesArray = productDeliveryTimes.toArray(new ProductDeliveryTime[productDeliveryTimes.size()]);
				if(productDeliveryTimesArray != null && productDeliveryTimesArray.length > 0) {
					sort(productDeliveryTimesArray, location, 0, productDeliveryTimesArray.length-1);
					return productDeliveryTimesArray;
				}
				return null;
			}
			return null;
		}
		
		return null;
	}
	
	private Product createProductFromRowData(String[] rowData) {
		Product product = new Product();
		
		product.setProductName(rowData[0]);
		product.setSupplier(rowData[1]);
		product.setDeliveryTimes(createDeliveryTimesFromRawData(rowData[2]));
		product.setInStock(Long.parseLong(rowData[3]));
		
		return product;
	}
	
	private List<DeliveryTime> createDeliveryTimesFromRawData(String rawData) {
		List<DeliveryTime> deliveryTimes = new ArrayList<>();
		
		String treatedRawData = rawData.substring(2, rawData.length()-2);
		
		String[] deliveries = treatedRawData.split(deliverySplitter);
		for(String delivery : deliveries) {
			DeliveryTime deliveryTime;
			String[] deliveryTimeString = delivery.split(deliveryTimeSplitter);
			
			deliveryTime = createDeliveryTimeFromString(deliveryTimeString);
			deliveryTimes.add(deliveryTime);
		}
		
		return deliveryTimes;
	}
	
	private DeliveryTime createDeliveryTimeFromString(String[] deliveryTimeString) {
		DeliveryTime deliveryTime = new DeliveryTime();
		String location = deliveryTimeString[0].stripLeading();
		location = location.substring(1, location.length()-1);
		deliveryTime.setLocation(location);
		deliveryTime.setDays(Long.parseLong(deliveryTimeString[1].stripLeading()));
		
		return deliveryTime;
	}
	
	//QuickSort support method
	private int partition(ProductDeliveryTime[] productDeliveryTimesArray, String location, int low, int high) 
    { 
		Long deliveryTime = productDeliveryTimesArray[high].getDays();
        int pivot = deliveryTime.intValue();  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than the pivot 
            if (productDeliveryTimesArray[j].getDays().intValue() < pivot) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                ProductDeliveryTime temp = productDeliveryTimesArray[i]; 
                productDeliveryTimesArray[i] = productDeliveryTimesArray[j]; 
                productDeliveryTimesArray[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        ProductDeliveryTime temp = productDeliveryTimesArray[i+1]; 
        productDeliveryTimesArray[i+1] = productDeliveryTimesArray[high]; 
        productDeliveryTimesArray[high] = temp; 
  
        return i+1; 
    } 
  
  
    // QuickSort main method
    private void sort(ProductDeliveryTime[] productDeliveryTimesArray, String location, int lowerIndex, int higherIndex) 
    { 
    	if (lowerIndex < higherIndex) 
        {  
	    	int pi = partition(productDeliveryTimesArray, location, lowerIndex, higherIndex); 
	  
	        // Sort elements recursively
	    	sort(productDeliveryTimesArray, location, 0, pi-1); 
	        sort(productDeliveryTimesArray, location, pi+1, productDeliveryTimesArray.length - 1); 
        }
    }
}

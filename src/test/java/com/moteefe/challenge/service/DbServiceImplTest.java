package com.moteefe.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.domain.Product;
import com.moteefe.challenge.domain.ProductDeliveryTime;
import com.moteefe.challenge.helper.DbServiceTestHelper;

@SpringBootTest
public class DbServiceImplTest {
	
    @Autowired
    private DbService dbService;
    
    @Test
    public void whenValidProductNameAndSupplier_thenRetrieveProduct() {
    	String productName = "black_mug";
    	String supplier = "Shirts4U";
    	
        Product product = dbService.retrieveProductByProductNameAndSupplier(productName, supplier);
      
         assertThat(product.getProductName()).isEqualTo(productName);
         assertThat(product.getSupplier()).isEqualTo(supplier);
         assertThat(product.getInStock()).isEqualTo(3);
    }
    
    @Test
    public void whenNoAvailableProductNameAndSupplier_thenRetrieveNull() {
    	String productName = "silver_mug";
    	String supplier = "Shirts";
    	
        Product product = dbService.retrieveProductByProductNameAndSupplier(productName, supplier);
      
         assertThat(product).isEqualTo(null);
    }
    
    @Test
    public void whenValidProductName_thenRetrieveProducts() {
    	String productName = "black_mug";
    	
        List<Product> products = dbService.retrieveProductsByProductName(productName);
      
         assertThat(products).hasSize(2);
         assertThat(products).allMatch(product -> product.getProductName().equals("black_mug"));
    }
    
    @Test
    public void whenNoAvailableProductName_thenRetrieveEmpty() {
    	String productName = "gold_mug";
    	
        List<Product> products = dbService.retrieveProductsByProductName(productName);
      
         assertThat(products).isEmpty();
    }
    
    @Test
    public void whenValidProductsAndRegion_thenRetrieveSortedByShortestDelivery() {
    	List<Product> products = DbServiceTestHelper.blackMugsProductList();
    	
    	ProductDeliveryTime[] productDeliveryTimes = dbService.orderProductListByLocationAsc(products, "us");
    	
         assertThat(productDeliveryTimes[0].getDays()).isEqualTo(6L);
         assertThat(productDeliveryTimes[1].getDays()).isEqualTo(7L);
    }
    
    @Test
    public void whenNullProductsAndRegion_thenRetrieveNull() {
    	List<Product> products = null;
    	
    	ProductDeliveryTime[] productDeliveryTimes = dbService.orderProductListByLocationAsc(products, null);
    	
         assertThat(productDeliveryTimes).isEqualTo(null);
    }
    
    @Test
    public void whenCommonSupplierForItems_thenRetrieveListOfSuppliers() {
    	List<Item> items = DbServiceTestHelper.shirts4UItems();
    	
    	List<String> suppliers = dbService.findCommonSupplierForItems(items);
    	
         assertThat(suppliers.size()).isEqualTo(1);
         assertThat(suppliers.get(0)).isEqualTo("Shirts4U");
    }
    
    @Test
    public void whenNoItems_thenRetrieveEmpty() {
    	List<Item> items = new ArrayList<>();
    	
    	List<String> suppliers = dbService.findCommonSupplierForItems(items);
    	
         assertThat(suppliers).isEmpty();
    }
    
}

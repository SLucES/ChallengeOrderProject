package com.moteefe.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.domain.Order;
import com.moteefe.challenge.domain.OrderParameters;
import com.moteefe.challenge.helper.OrderTestHelper;

@SpringBootTest
public class OrderServiceImplTest {	
 
    @Autowired
    private OrderService orderService;
 
    @Test
    public void whenValidOrder_thenCreateOrder() {
    	OrderParameters orderParameters = new OrderParameters();
    	
    	Item item = new Item();
    	item.setCount(1L);
    	item.setTitle("black_mug");
    	
    	List<Item> items = new ArrayList<>();
    	items.add(item);
    	
    	orderParameters.setRegion("us");
    	orderParameters.setItems(items);
    	
        Order order = orderService.createOrder(orderParameters);
      
         assertThat(order.getDeliveryDate())
          .isEqualTo(OrderTestHelper.createValidBlackMugOrder().getDeliveryDate());
         assertThat(order.getShipments().size())
         .isEqualTo(OrderTestHelper.createValidBlackMugOrder().getShipments().size());
    }
    
    @Test
    public void whenNoAvailableOrder_thenNoOrder() {
    	OrderParameters orderParameters = new OrderParameters();
    	
    	Item item = new Item();
    	item.setCount(1L);
    	item.setTitle("black_mug");
    	
    	List<Item> items = new ArrayList<>();
    	items.add(item);
    	
    	orderParameters.setRegion("rsa");
    	orderParameters.setItems(items);
    	
        Order order = orderService.createOrder(orderParameters);
      
         assertThat(order)
          .isEqualTo(null);
    }

}

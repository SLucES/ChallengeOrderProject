package com.moteefe.challenge.helper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.domain.Order;
import com.moteefe.challenge.domain.Shipment;

public class OrderTestHelper {

	public static Order createValidBlackMugOrder() {
		Order order = new Order();
		
		OffsetDateTime today = OffsetDateTime.of(OffsetDateTime.now().getYear(),
				OffsetDateTime.now().getMonthValue(), 
				OffsetDateTime.now().getDayOfMonth(), 
				0, 0, 0, 0, ZoneOffset.UTC);
		
		order.setDeliveryDate(today.plusDays(6));
		
		List<Shipment> shipments = new ArrayList<>();
		
		List<Item> items = new ArrayList<>();
		
		Shipment shipment = new Shipment();
		shipment.setDeliveryDate(today.plusDays(6));
		shipment.setSupplier("Shirts4U");
		
		Item item = new Item();
		item.setCount(1L);
		item.setTitle("black_mug");
		
		items.add(item);
		
		shipment.setItems(items);
		
		shipments.add(shipment);
		
		order.setShipments(shipments);
		
		return order;
	}
	
}

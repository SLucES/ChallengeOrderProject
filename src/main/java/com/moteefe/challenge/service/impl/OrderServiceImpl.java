package com.moteefe.challenge.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moteefe.challenge.domain.DeliveryTime;
import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.domain.Order;
import com.moteefe.challenge.domain.OrderParameters;
import com.moteefe.challenge.domain.Product;
import com.moteefe.challenge.domain.ProductDeliveryTime;
import com.moteefe.challenge.domain.Shipment;
import com.moteefe.challenge.service.DbService;
import com.moteefe.challenge.service.OrderService;
import com.moteefe.challenge.util.ProductConverter;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	DbService dbService;

	public synchronized Order createOrder(OrderParameters orderParameters) {
		Order order = new Order();
		List<Item> items = orderParameters.getItems(); 
		
		for(Item item : items) {
			order = buildOrderPerItem(item, order, orderParameters.getRegion());
		}
		
		Order orderFromUniqueSupplier = chooseOrderFromLowestDeliveryTimeUniqueSupplier(orderParameters);
		if(orderFromUniqueSupplier != null && !orderFromUniqueSupplier.getDeliveryDate().isAfter(order.getDeliveryDate())) {
			order = orderFromUniqueSupplier;
		}
		
		if(order.getDeliveryDate() == null) {
			order = null;
		}
		
		return order;
	}
	
	private Order buildOrderPerItem(Item item, Order order, String region) {
		List<Product> products = dbService.retrieveProductsByProductName(item.getTitle());
		if(products != null) {
			ProductDeliveryTime[] productDeliveryTimes = dbService.orderProductListByLocationAsc(products, region);
			
			Long itemCount = item.getCount();
			
			if(productDeliveryTimes != null && productDeliveryTimes.length > 0) {
				for(Integer suppliersCount = 0; suppliersCount < productDeliveryTimes.length; suppliersCount++) {
					Item itemWithRemainingQuantity = new Item();
					itemWithRemainingQuantity.setTitle(item.getTitle());
					itemWithRemainingQuantity.setCount(itemCount);
					
					ProductDeliveryTime currentSupplierDeliveryTime = productDeliveryTimes[suppliersCount];
					// is this supplier enough or do we need more?
					if(itemCount <= currentSupplierDeliveryTime.getInStock()) {
						//last supplier
						//try to find shipment on current order from this supplier
						if(getShipmentBySupplier(order.getShipments(), currentSupplierDeliveryTime.getSupplier()) != null) {
							//there is a previous shipment from supplier
							Shipment shipment = getShipmentBySupplier(order.getShipments(), currentSupplierDeliveryTime.getSupplier());
							int supplierIndex = order.getShipments().indexOf(shipment);
							
							shipment = buildSupplierShipment(order, shipment, itemWithRemainingQuantity, currentSupplierDeliveryTime);
							
							order = buildSupplierOrder(order, shipment, itemWithRemainingQuantity, currentSupplierDeliveryTime, supplierIndex);
							
						} else {
							//no previous shipment from supplier
							Shipment shipment = null;
							
							shipment = buildSupplierShipment(order, shipment, itemWithRemainingQuantity, currentSupplierDeliveryTime);
							
							order = buildSupplierOrder(order, shipment, itemWithRemainingQuantity, currentSupplierDeliveryTime, -1);
						}
						
						return order;
					} else {
						//we need at least the next supplier
						itemWithRemainingQuantity.setCount(currentSupplierDeliveryTime.getInStock());
						itemCount = itemCount - currentSupplierDeliveryTime.getInStock();
						
						//try to find shipment on current order from this supplier
						if(getShipmentBySupplier(order.getShipments(), currentSupplierDeliveryTime.getSupplier()) != null) {
							//there is a previous shipment from supplier
							Shipment shipment = getShipmentBySupplier(order.getShipments(), currentSupplierDeliveryTime.getSupplier());
							int supplierIndex = order.getShipments().indexOf(shipment);
							
							shipment = buildSupplierShipment(order, shipment, itemWithRemainingQuantity, currentSupplierDeliveryTime);
							
							order = buildSupplierOrder(order, shipment, itemWithRemainingQuantity, currentSupplierDeliveryTime, supplierIndex);
							
						} else {
							//no previous shipment from supplier
							Shipment shipment = null;
							
							shipment = buildSupplierShipment(order, shipment, itemWithRemainingQuantity, currentSupplierDeliveryTime);
							
							order = buildSupplierOrder(order, shipment, itemWithRemainingQuantity, currentSupplierDeliveryTime, -1);
						}
					}
				}
			}
			
			//only is called if the stock is inexistent or incomplete
			//since this is not a scenario that was mentioned it will not be treated; 
			return order;
		} else {
			return null;
		}
	}
	
	private Shipment buildSupplierShipment(Order order, Shipment shipment, Item item, ProductDeliveryTime currentSupplierDeliveryTime) {
		List<Item> shipmentItems;
		OffsetDateTime currentShipmentDeliveryDate = null;
		if(shipment == null) {
			shipment = new Shipment();
			shipmentItems = new ArrayList<>();
			shipment.setSupplier(currentSupplierDeliveryTime.getSupplier());
		} else {
			shipmentItems = shipment.getItems();
			currentShipmentDeliveryDate = shipment.getDeliveryDate();
		}
		
		shipmentItems.add(item);
		shipment.setItems(shipmentItems);
		
		OffsetDateTime today = OffsetDateTime.of(OffsetDateTime.now().getYear(),
				OffsetDateTime.now().getMonthValue(), 
				OffsetDateTime.now().getDayOfMonth(), 
				0, 0, 0, 0, ZoneOffset.UTC);
		
		if(currentShipmentDeliveryDate == null || currentShipmentDeliveryDate.isBefore(today.plusDays(currentSupplierDeliveryTime.getDays()))) {
			shipment.setDeliveryDate(today.plusDays(currentSupplierDeliveryTime.getDays()));
		}
		
		return shipment;
	}
	
	private Order buildSupplierOrder(Order order, 
			Shipment shipment, 
			Item item, 
			ProductDeliveryTime currentSupplierDeliveryTime, 
			int supplierIndex) {
		
		List<Shipment> orderShipments = order.getShipments();
		
		if(supplierIndex != -1) {
			orderShipments.remove(supplierIndex);
		}
		if(orderShipments == null) {
			orderShipments = new ArrayList<>();
		}
		orderShipments.add(shipment);
		order.setShipments(orderShipments);
		
		OffsetDateTime currentOrderDeliveryDate = order.getDeliveryDate();
		
		OffsetDateTime today = OffsetDateTime.of(OffsetDateTime.now().getYear(),
				OffsetDateTime.now().getMonthValue(), 
				OffsetDateTime.now().getDayOfMonth(), 
				0, 0, 0, 0, ZoneOffset.UTC);
		
		if(currentOrderDeliveryDate == null || currentOrderDeliveryDate.isBefore(today.plusDays(currentSupplierDeliveryTime.getDays()))) {
			order.setDeliveryDate(today.plusDays(currentSupplierDeliveryTime.getDays()));
		}
		
		return order;
	}
	
	private Order chooseOrderFromLowestDeliveryTimeUniqueSupplier(OrderParameters orderParameters) {
		Order chosenOrder = null;
		
		List<String> suppliers = dbService.findCommonSupplierForItems(orderParameters.getItems());
		for(String supplier : suppliers) {
			Order supplierOrder = buildOrderForSupplier(orderParameters, supplier);
			
			if(chosenOrder == null || chosenOrder.getDeliveryDate().isAfter(supplierOrder.getDeliveryDate())) {
				chosenOrder = supplierOrder;
			}
		}
		
		return chosenOrder;
	}
	
	private Order buildOrderForSupplier(OrderParameters orderParameters, String supplier) {
		Order order = null;
		
		List<Item> items = orderParameters.getItems(); 
		
		for(Item item : items) {
			order = buildUniqueSupplierOrderPerItem(item, order, orderParameters.getRegion(), supplier);
			if(order == null) {
				return null;
			}
		}
		
		return order;
	}
	
	private Order buildUniqueSupplierOrderPerItem(Item item, Order order, String region, String supplier) {
		Product product = dbService.retrieveProductByProductNameAndSupplier(item.getTitle(), supplier);
		if(product != null) {
			List<DeliveryTime> deliveryTimes = product.getDeliveryTimes(); 
			DeliveryTime deliveryTime = ProductConverter.getDeliveryTimeByLocation(deliveryTimes, region);
			if(deliveryTime != null) {
				Long days = deliveryTime.getDays();
				
				OffsetDateTime today = OffsetDateTime.of(OffsetDateTime.now().getYear(),
						OffsetDateTime.now().getMonthValue(), 
						OffsetDateTime.now().getDayOfMonth(), 
						0, 0, 0, 0, ZoneOffset.UTC);
				
				if(product.getInStock() < item.getCount()) {
					return null;
				} else {
					if(order == null) {
						order = new Order();
						List<Shipment> shipments = new ArrayList<>();
						List<Item> shipmentItems = new ArrayList<>();
						
						shipmentItems.add(item);
						
						Shipment shipment = new Shipment();
						shipment.setItems(shipmentItems);
						shipment.setSupplier(supplier);
						shipment.setDeliveryDate(today.plusDays(days));
						
						shipments.add(shipment);
						order.setShipments(shipments);
						order.setDeliveryDate(today.plusDays(days));
					} else {
						List<Shipment> shipments = order.getShipments();
						if(order.getShipments() != null && !order.getShipments().isEmpty()) {
							Shipment shipment = shipments.get(0);
							
							List<Item> shipmentItems = shipment.getItems();
							shipmentItems.add(item);
							shipment.setItems(shipmentItems);
							
							if(shipment.getDeliveryDate().isBefore(today.plusDays(days))) {
								shipment.setDeliveryDate(today.plusDays(days));
							}
							
							shipments.remove(0);
							shipments.add(shipment);
							
							order.setShipments(shipments);
							if(order.getDeliveryDate().isBefore(today.plusDays(days))) {
								order.setDeliveryDate(today.plusDays(days));
							}
						}
					}
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return order;
	}
	
	
	
	private static Shipment getShipmentBySupplier(List<Shipment> shipments, String supplier){
		if(shipments != null && !shipments.isEmpty()){
		    if(shipments.stream().filter(o -> o.getSupplier().contentEquals(supplier)).findFirst().isPresent()) {
		    	return shipments.stream().filter(o -> o.getSupplier().contentEquals(supplier)).findFirst().get();
		    } else {
		    	return null;
		    }
		} else {
			return null;
		}
	}
}

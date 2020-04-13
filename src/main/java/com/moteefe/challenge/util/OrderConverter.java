package com.moteefe.challenge.util;

import java.util.List;
import java.util.stream.Collectors;

import com.moteefe.challenge.domain.Order;
import com.moteefe.challenge.dto.OrderDto;
import com.moteefe.challenge.dto.ShipmentDto;

public class OrderConverter {

	public static OrderDto convertToDto(Order order) {
		OrderDto orderDto = new OrderDto();
		
		orderDto.setDeliveryDate(order.getDeliveryDate());
		
		List<ShipmentDto> shipmentsDto = order.getShipments().stream().map(shipment -> ShipmentConverter.convertToDto(shipment)).collect(Collectors.toList());
		orderDto.setShipments(shipmentsDto);
		
		return orderDto;
	}
	
}

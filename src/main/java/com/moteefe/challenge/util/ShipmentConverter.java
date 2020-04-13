package com.moteefe.challenge.util;

import java.util.List;
import java.util.stream.Collectors;

import com.moteefe.challenge.domain.Shipment;
import com.moteefe.challenge.dto.ItemDto;
import com.moteefe.challenge.dto.ShipmentDto;

public class ShipmentConverter {

	public static ShipmentDto convertToDto(Shipment shipment) {
		ShipmentDto shipmentDto = new ShipmentDto();
		
		shipmentDto.setSupplier(shipment.getSupplier());
		shipmentDto.setDeliveryDate(shipment.getDeliveryDate());
		
		List<ItemDto> itemsDto = shipment.getItems().stream().map(item -> ItemConverter.convertToDto(item)).collect(Collectors.toList());
		shipmentDto.setItems(itemsDto);
		
		return shipmentDto;
	}
	
}

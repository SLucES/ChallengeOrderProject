package com.moteefe.challenge.util;

import java.util.List;
import java.util.stream.Collectors;

import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.domain.OrderParameters;
import com.moteefe.challenge.dto.OrderParametersDto;

public class OrderParametersConverter {

	public static OrderParameters convertToEntity(OrderParametersDto orderParametersDto) {
		OrderParameters orderParameters = new OrderParameters();
		
		List<Item> items = orderParametersDto.getItems().stream().map(itemDto -> ItemConverter.convertToEntity(itemDto)).collect(Collectors.toList());
		
		orderParameters.setItems(items);
		orderParameters.setRegion(orderParametersDto.getRegion());
		
		return orderParameters;
	}
	
}

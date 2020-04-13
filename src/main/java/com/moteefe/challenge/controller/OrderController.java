package com.moteefe.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moteefe.challenge.domain.Order;
import com.moteefe.challenge.domain.OrderParameters;
import com.moteefe.challenge.dto.OrderDto;
import com.moteefe.challenge.dto.OrderParametersDto;
import com.moteefe.challenge.service.OrderService;
import com.moteefe.challenge.util.OrderConverter;
import com.moteefe.challenge.util.OrderParametersConverter;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;

	@PostMapping(value="", produces = "application/json")
	public OrderDto createOrder(@RequestBody OrderParametersDto orderParametersDto){
		OrderParameters orderParameters = OrderParametersConverter.convertToEntity(orderParametersDto);
		Order order = orderService.createOrder(orderParameters);
		
		OrderDto orderDto = OrderConverter.convertToDto(order);
		return orderDto;
	}
}

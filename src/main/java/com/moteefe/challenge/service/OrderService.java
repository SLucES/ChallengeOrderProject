package com.moteefe.challenge.service;

import com.moteefe.challenge.domain.Order;
import com.moteefe.challenge.domain.OrderParameters;

public interface OrderService {

	public Order createOrder(OrderParameters orderParameters);
	
}

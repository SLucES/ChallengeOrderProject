package com.moteefe.challenge.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2855072277466767715L;
	OffsetDateTime deliveryDate;
	List<Shipment> shipments;
	
	public OffsetDateTime getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(OffsetDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public List<Shipment> getShipments() {
		return shipments;
	}
	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}
	
	
}

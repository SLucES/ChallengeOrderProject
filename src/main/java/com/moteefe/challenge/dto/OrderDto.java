package com.moteefe.challenge.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6431042947787797128L;
	OffsetDateTime deliveryDate;
	List<ShipmentDto> shipments;
	
	@JsonProperty("delivery_date")
	public OffsetDateTime getDeliveryDate() {
		return deliveryDate;
	}
	@JsonProperty("delivery_date")
	public void setDeliveryDate(OffsetDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@JsonProperty("shipments")
	public List<ShipmentDto> getShipments() {
		return shipments;
	}
	@JsonProperty("shipments")
	public void setShipments(List<ShipmentDto> shipments) {
		this.shipments = shipments;
	}
	
	
}

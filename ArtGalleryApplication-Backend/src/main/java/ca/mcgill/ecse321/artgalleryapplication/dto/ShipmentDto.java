package ca.mcgill.ecse321.artgalleryapplication.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;

public class ShipmentDto {
	private Boolean toGallery;
	private Time estimatedArrivalTime;
	private int shipmentId;
	private Date estimatedArrivalDate;
	private Address returnAddress;
	private Address destinationAddress;


	public ShipmentDto() {
	}

	public ShipmentDto(Boolean toGallery, Time estimatedArrivalTime, int shipmentId, Date estimatedArrivalDate, Address returnAddress, Address destinationAddress) {
		this.toGallery = toGallery;
		this.estimatedArrivalTime = estimatedArrivalTime;
		this.shipmentId = shipmentId;
		this.estimatedArrivalDate = estimatedArrivalDate;
		this.returnAddress = returnAddress;
		this.destinationAddress = destinationAddress;
	}

	public Boolean getToGallery() {
		return this.toGallery;
	}

	public Time getEstimatedArrivalTime() {
		return this.estimatedArrivalTime;
	}

	public int shipmentId() {
		return this.shipmentId;
	}

	public Date getEstimatedArrivalDate() {
		return this.estimatedArrivalDate;
	}
	
	public Address getReturnAddress() {
		return this.returnAddress;
	}
	
	public Address getDestinationAddress() {
		return this.destinationAddress;
	}
	
}

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

	public ShipmentDto(Boolean toGal, Time eta, int shipmentId, Date estimatedArrival, Address r, Address d) {
		this.toGallery = toGal;
		this.estimatedArrivalTime = eta;
		this.shipmentId = shipmentId;
		this.estimatedArrivalDate = estimatedArrival;
		this.returnAddress = r;
		this.destinationAddress = d;
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

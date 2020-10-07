package ca.mcgill.ecse321.artgalleryapplication.model;

import javax.persistence.Entity;
import java.sql.Time;
import javax.persistence.Id;
import java.sql.Date;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Shipment{
private Boolean toGallery;
   
   public void setToGallery(Boolean value) {
this.toGallery = value;
    }
public Boolean getToGallery() {
return this.toGallery;
    }
private Time estimatedArrivalTime;

public void setEstimatedArrivalTime(Time value) {
this.estimatedArrivalTime = value;
    }
public Time getEstimatedArrivalTime() {
return this.estimatedArrivalTime;
    }
private int shipmentId;

public void setShipmentId(int value) {
this.shipmentId = value;
    }
@Id
public int getShipmentId() {
return this.shipmentId;
    }
private Date estimatedArrivalDate;

public void setEstimatedArrivalDate(Date value) {
this.estimatedArrivalDate = value;
    }
public Date getEstimatedArrivalDate() {
return this.estimatedArrivalDate;
    }
private Address returnAddress;

@OneToOne
public Address getReturnAddress() {
   return this.returnAddress;
}

public void setReturnAddress(Address returnAddress) {
   this.returnAddress = returnAddress;
}

private Address destination;

@OneToOne
public Address getDestination() {
   return this.destination;
}

public void setDestination(Address destination) {
   this.destination = destination;
}

}

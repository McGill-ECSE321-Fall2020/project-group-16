package ca.mcgill.ecse321.artgalleryapplication.model;

import javax.persistence.Entity;
import java.sql.Date;
import javax.persistence.Id;
import java.sql.Time;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Order{
private OrderStatus orderStatus;
   
   public void setOrderStatus(OrderStatus value) {
this.orderStatus = value;
    }
public OrderStatus getOrderStatus() {
return this.orderStatus;
    }
private double totalAmount;

public void setTotalAmount(double value) {
this.totalAmount = value;
    }
public double getTotalAmount() {
return this.totalAmount;
    }
private Date orderDate;

public void setOrderDate(Date value) {
this.orderDate = value;
    }
public Date getOrderDate() {
return this.orderDate;
    }
private int orderId;

public void setOrderId(int value) {
this.orderId = value;
    }
@Id
public int getOrderId() {
return this.orderId;
    }
private Time orderTime;

public void setOrderTime(Time value) {
this.orderTime = value;
    }
public Time getOrderTime() {
return this.orderTime;
    }
private UserProfile userProfile;

@ManyToOne(optional=false)
public UserProfile getUserProfile() {
   return this.userProfile;
}

public void setUserProfile(UserProfile userProfile) {
   this.userProfile = userProfile;
}

private Set<Payment> payment;

@OneToMany(mappedBy="order", cascade={CascadeType.ALL})
public Set<Payment> getPayment() {
   return this.payment;
}

public void setPayment(Set<Payment> payments) {
   this.payment = payments;
}

private Shipment shipment;

@OneToOne(mappedBy="order", cascade={CascadeType.ALL})
public Shipment getShipment() {
   return this.shipment;
}

public void setShipment(Shipment shipment) {
   this.shipment = shipment;
}

private Set<Artwork> artwork;

@OneToMany(mappedBy="order")
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

private UserProfile customer;

@OneToOne(optional=false)
public UserProfile getCustomer() {
   return this.customer;
}

public void setCustomer(UserProfile customer) {
   this.customer = customer;
}

}

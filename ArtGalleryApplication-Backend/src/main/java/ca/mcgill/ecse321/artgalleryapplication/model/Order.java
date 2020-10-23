package ca.mcgill.ecse321.artgalleryapplication.model;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="ag_order")
public class Order{
@Enumerated
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

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int orderId;
public void setOrderId(int value) {
this.orderId = value;
    }
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

private Payment payment;
@OneToOne(cascade={CascadeType.ALL})
public Payment getPayment() {
   return this.payment;
}
public void setPayment(Payment payment) {
   this.payment = payment;
}

private Shipment shipment;

@OneToOne(cascade={CascadeType.ALL})
public Shipment getShipment() {
   return this.shipment;
}

public void setShipment(Shipment shipment) {
   this.shipment = shipment;
}

private Artwork artwork;

@OneToOne(optional=false)
public Artwork getArtwork() {
   return this.artwork;
}

public void setArtwork(Artwork artwork) {
   this.artwork = artwork;
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

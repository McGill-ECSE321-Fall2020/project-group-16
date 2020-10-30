package ca.mcgill.ecse321.artgalleryapplication.dto;

import ca.mcgill.ecse321.artgalleryapplication.model.OrderStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;

import java.sql.Date;
import java.sql.Time;

public class OrderDto {

    private int orderId;
    private OrderStatus orderStatus;
    private UserProfileDto customer;
    private ArtworkDto artwork;
    private PaymentDto payment;
    private ShipmentDto shipment;
    private double totalAmount;
    private Date orderDate;
    private Time orderTime;

    public OrderDto(int orderId, UserProfileDto customer, ArtworkDto artwork, double totalAmount,
                    OrderStatus orderStatus, Date orderDate, Time orderTime, PaymentDto payment, ShipmentDto shipment) {
        this.orderId = orderId;
        this.customer = customer;
        this.artwork = artwork;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.payment = payment;
        this.shipment = shipment;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
    }

    public OrderDto(int orderId, UserProfileDto customer, ArtworkDto artwork) {
        this.orderId = orderId;
        this.customer = customer;
        this.artwork = artwork;
        this.totalAmount = artwork.getPrice();
        this.orderDate = new Date(System.currentTimeMillis());
        this.orderTime = new Time(System.currentTimeMillis());
        this.orderStatus = null;
        this.payment = null;
        this.shipment = null;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserProfileDto getCustomer() {
        return customer;
    }

    public void setCustomer(UserProfileDto customer) {
        this.customer = customer;
    }

    public ArtworkDto getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDto artwork) {
        this.artwork = artwork;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }

    public ShipmentDto getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentDto shipment) {
        this.shipment = shipment;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Time getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Time orderTime) {
        this.orderTime = orderTime;
    }
}

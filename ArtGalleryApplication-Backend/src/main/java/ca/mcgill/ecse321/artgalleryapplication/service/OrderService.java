package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ca.mcgill.ecse321.artgalleryapplication.model.OrderStatus.*;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private OrderRepository orderRepository;


    // --- Create --- //

    /**
     * 
     * @param artworkId
     * @param username
     * @return an order
     */
    @Transactional
    public Order placeOrder(int artworkId, String username){
        int orderId = artworkId * username.hashCode();
        Date orderDate = Date.valueOf(LocalDate.now());
        Time orderTime = Time.valueOf(LocalTime.now());

        if (username.trim().length() == 0)
            throw new IllegalArgumentException("Customer Username cannot be empty or have spaces.");

        Artwork artwork = artworkRepository.findArtworkByArtworkId(artworkId);
        if (artwork == null) {
            throw new IllegalArgumentException("No artwork associated with this artworkId.");
        }

        UserProfile customer = userRepository.findUserProfileByUsername(username);
        if (customer == null) {
            throw new IllegalArgumentException("No user associated with this username.");
        }

        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(PaymentPending);
        order.setOrderDate(orderDate);
        order.setOrderTime(orderTime);
        order.setTotalAmount(artwork.getPrice());
        order.setArtwork(artwork);
        order.setCustomer(customer);
        orderRepository.save(order);

        return order;
    }


    // --- Delete --- //

    /**
     * 
     * @param orderId
     * @return delete successful
     */
    @Transactional
    public boolean deleteOrder(int orderId) {
        Order orderTBD = orderRepository.findOrderByOrderId(orderId);
        if (orderTBD == null) {
            throw new IllegalArgumentException("Order does not exist to be deleted.");
        }

        orderRepository.deleteByOrderId(orderId);
        return true;
    }


    // --- Getters --- //

    /**
     * 
     * @param orderId
     * @return an order
     */
    @Transactional
    public Order getOrderById(int orderId){
        Order order = orderRepository.findOrderByOrderId(orderId);

        if (order == null) {
            throw new IllegalArgumentException("No Order associated with this id.");
        }

        return order;
    }

    /**
     * 
      * @param username
     * @return list of orders
     */  
    public List<Order> getOrdersByUser(String username) {
        UserProfile customer = userRepository.findUserProfileByUsername(username);

        if (customer == null)
            throw new IllegalArgumentException("No user associated with this username.");

        return orderRepository.findByCustomer(customer);
    }

    /**
     * 
     * @return list of all orders
     */
    @Transactional
    public List<Order> getAllOrders() {
        return toList(orderRepository.findAll());
    }


    // -- Associations -- //

    /**
     * 
     * @param orderId
     * @param paymentId
     * @return an updated order
     */
    @Transactional
    public Order addPaymentToOrder(int orderId, int paymentId){
        Order updateOrder = orderRepository.findOrderByOrderId(orderId);
        Payment addedPayment = paymentRepository.findPaymentByPaymentId(paymentId);
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (addedPayment == null)
            throw new IllegalArgumentException("Payment does not exist in database.");

        updateOrder.setPayment(addedPayment);
        updateOrder.setOrderStatus(Placed);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    /**
     * 
     * @param orderId
     * @param shipmentId
     * @return an updated order
     */
    @Transactional
    public Order addShipmentToOrder(int orderId, int shipmentId){
        Order updateOrder = orderRepository.findOrderByOrderId(orderId);
        Shipment addedShipment = shipmentRepository.findShipmentByShipmentId(shipmentId);
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (addedShipment == null)
            throw new IllegalArgumentException("Shipment does not exist in database.");

        updateOrder.setShipment(addedShipment);
        updateOrder.setOrderStatus(Shipped);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    // TODO: add create new shippment field


    // --- Update --- //

    /**
     * 
     * @param order
     * @param artworkId
     * @return an updated order
     */
    @Transactional
    public Order updateOrderArtwork(Order order, int artworkId){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");

        Artwork artwork = artworkRepository.findArtworkByArtworkId(artworkId);
        if (artwork == null)
            throw new IllegalArgumentException("No artwork associated with this id.");

        updateOrder.setArtwork(artwork);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    /**
     * 
     * @param order
     * @param customerUsername
     * @return an updated order
     */
    @Transactional
    public Order updateOrderCustomer(Order order, String customerUsername){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");

        UserProfile customer = userRepository.findUserProfileByUsername(customerUsername);
        if (customer == null)
            throw new IllegalArgumentException("No user associated with this username.");

        updateOrder.setCustomer(customer);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    /**
     * 
     * @param order
     * @param paymentId
     * @return an updated order
     */
    @Transactional
    public Order updateOrderPayment(Order order, int paymentId){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");

        Payment payment = paymentRepository.findPaymentByPaymentId(paymentId);
        if (payment == null)
            throw new IllegalArgumentException("No Payment associated with this id.");

        updateOrder.setPayment(payment);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    /**
     * 
     * @param order
     * @param shipmentId
     * @return an updated order
     */
    @Transactional
    public Order updateOrderShippment(Order order, int shipmentId){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");

        Shipment shipment = shipmentRepository.findShipmentByShipmentId(shipmentId);
        if (shipment == null)
            throw new IllegalArgumentException("No Shipment associated with this id.");

        updateOrder.setShipment(shipment);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    /**
     * 
     * @param order
     * @param orderDate
     * @return an updated order
     */
    @Transactional
    public Order updateOrderDate(Order order, Date orderDate){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (orderDate == null)
            throw new IllegalArgumentException("Order Date cannot be empty.");

        updateOrder.setOrderDate(orderDate);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    /**
     * 
     * @param order
     * @param orderTime
     * @return an updated order
     */
    @Transactional
    public Order updateOrderTime(Order order, Time orderTime){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (orderTime == null)
            throw new IllegalArgumentException("Order Time cannot be empty.");

        updateOrder.setOrderTime(orderTime);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    /**
     * 
     * @param orderId
     * @param amount
     * @return an updated order
     */
    @Transactional
    public Order updateOrderAmount(int orderId, double amount){
       Order updateOrder = orderRepository.findOrderByOrderId(orderId);
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative.");

        updateOrder.setTotalAmount(amount);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    /**
     * 
     * @param orderId
     * @param orderStatus
     * @return an updated order
     */
    @Transactional
    public Order updateOrderStatus(int orderId, OrderStatus orderStatus){
        Order updateOrder = orderRepository.findOrderByOrderId(orderId);
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (orderStatus == null)
            throw new IllegalArgumentException("Order Status cannot be empty.");

        updateOrder.setOrderStatus(orderStatus);
        orderRepository.save(updateOrder);
        return updateOrder;
    }


    // --- Helper Methods --- //

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

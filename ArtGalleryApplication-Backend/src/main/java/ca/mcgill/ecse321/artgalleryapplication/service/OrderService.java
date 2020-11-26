package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
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
        Date orderDate = Date.valueOf(LocalDate.now());
        Time orderTime = Time.valueOf(LocalTime.now());

        if (username.trim().length() == 0)
            throw new ApiRequestException("Customer Username cannot be empty.");

        Artwork artwork = artworkRepository.findArtworkByArtworkId(artworkId);
        if (artwork == null) {
            throw new ApiRequestException("No artwork associated with this artworkId.");
        }

        UserProfile customer = userRepository.findByUsername(username);
        if (customer == null) {
            throw new ApiRequestException("No user associated with this username.");
        }

        Order order = new Order();
        order.setOrderStatus(PaymentPending);
        order.setTotalAmount(artwork.getPrice());
        order.setOrderDate(orderDate);
        order.setOrderTime(orderTime);
        order.setArtwork(artwork);
        order.setCustomer(customer);
        orderRepository.save(order);

        artwork.setArtworkStatus(ArtworkStatus.Sold);
        artworkRepository.save(artwork);

        return order;
    }

    @Transactional
    public Order placeOrder(int artworkId, String username, double price, int paymentId, int shipmentId){
        Order order = placeOrder(artworkId, username);
        Payment payment = paymentRepository.findPaymentByPaymentId(paymentId);
        Shipment shipment = shipmentRepository.findShipmentByShipmentId(shipmentId);

        if (order == null)
            throw new ApiRequestException("Order does not exist in database.");
        if (price < 0)
            throw new ApiRequestException("Price cannot be negative.");
        if (payment == null)
            throw new ApiRequestException("Payment does not exist in database.");
        if (shipment == null)
            throw new ApiRequestException("Shipment does not exist in database.");

        order.setTotalAmount(price);
        order.setPayment(payment);
        order.setShipment(shipment);
        order.setOrderStatus(Placed);
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
            throw new ApiRequestException("Order does not exist to be deleted.");
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
            throw new ApiRequestException("No Order associated with this id.");
        }

        return order;
    }

    /**
     * 
      * @param username
     * @return list of orders
     */  
    public List<Order> getOrdersByUser(String username) {
        UserProfile customer = userRepository.findByUsername(username);
        if (customer == null)
            throw new ApiRequestException("No user associated with this username.");

        return orderRepository.findByCustomer(customer);
    }

    /**
     *
     * @param orderStatus
     * @return list of orders
     */
    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        if (orderStatus == null)
            throw new ApiRequestException("Not a valid OrderStatus.");

        return orderRepository.findByOrderStatus(orderStatus);
    }

    public Order getCurrentOrder(String username) {
        UserProfile customer = userRepository.findByUsername(username);
        if (customer == null)
            throw new ApiRequestException("No user associated with this username.");

        return orderRepository.findOrdersByCustomerAndOrderStatus(customer, PaymentPending).get(0);

    }

    public List<Order> getPastOrders(String username) {
        UserProfile customer = userRepository.findByUsername(username);
        if (customer == null)
            throw new ApiRequestException("No user associated with this username.");

        List<Order> pastOrders = orderRepository.findByCustomer(customer);
        pastOrders.remove(orderRepository.findOrdersByCustomerAndOrderStatus(customer, PaymentPending).get(0));
        return pastOrders;
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
            throw new ApiRequestException("Order does not exist in database.");
        if (addedPayment == null)
            throw new ApiRequestException("Payment does not exist in database.");

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
            throw new ApiRequestException("Order does not exist in database.");
        if (addedShipment == null)
            throw new ApiRequestException("Shipment does not exist in database.");

        updateOrder.setShipment(addedShipment);
        updateOrder.setOrderStatus(Shipped);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    @Transactional
    public Order addPaymentAndShipmentToOrder(int orderId, int paymentId, int shipmentId){
        Order updateOrder = orderRepository.findOrderByOrderId(orderId);
        Payment addedPayment = paymentRepository.findPaymentByPaymentId(paymentId);
        Shipment addedShipment = shipmentRepository.findShipmentByShipmentId(shipmentId);
        if (updateOrder == null)
            throw new ApiRequestException("Order does not exist in database.");
        if (addedPayment == null)
            throw new ApiRequestException("Payment does not exist in database.");
        if (addedShipment == null)
            throw new ApiRequestException("Shipment does not exist in database.");

        updateOrder.setPayment(addedPayment);
        updateOrder.setShipment(addedShipment);
        updateOrder.setOrderStatus(Placed);
        orderRepository.save(updateOrder);
        return updateOrder;
    }


    // --- Update --- //

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
            throw new ApiRequestException("Order does not exist in database.");
        if (amount < 0)
            throw new ApiRequestException("Amount cannot be negative.");

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
            throw new ApiRequestException("Order does not exist in database.");
        if (orderStatus == null)
            throw new ApiRequestException("Order Status cannot be empty.");

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

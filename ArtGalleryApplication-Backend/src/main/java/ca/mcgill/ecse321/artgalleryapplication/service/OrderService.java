package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
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
    private AddressRepository addressRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private GalleryEventRepository galleryEventRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ArtGalleryApplicationRepository artGalleryApplicationRepository;



    // --- Create --- //

    @Transactional
    public Order createOrder(int orderId, int artworkId, String customerUsername, Date orderDate, Time orderTime){
        String error = "";
        if (orderDate == null) {
            error += "Order date cannot be empty.";
        }
        if (orderTime == null) {
            error += "Order time cannot be empty.";
        }
        if (customerUsername == null || customerUsername.trim().length() == 0) {
            error += "Customer Username cannot be empty or have spaces.";
        }

        Artwork artwork = artworkRepository.findArtworkByArtworkId(artworkId);
        if (artwork == null) {
            error += "No artwork associated with this artworkId.";
        }

        UserProfile customer = userRepository.findUserProfileByUsername(customerUsername);
        if (customer != null) {
            error += "No user associated with this username.";
        }
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
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


    // -- Associations -- //

    @Transactional
    public Order addPaymentToOrder(Order order, Payment payment){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");
        if (payment == null)
            throw new IllegalArgumentException("Payment does not exist.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        Payment addedPayment = paymentRepository.findPaymentByPaymentId(payment.getPaymentId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (addedPayment == null)
            throw new IllegalArgumentException("Payment does not exist in database.");

        updateOrder.setPayment(addedPayment);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    @Transactional
    public Order addShipmentToOrder(Order order, Shipment shipment){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");
        if (shipment == null)
            throw new IllegalArgumentException("Shipment does not exist.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        Shipment addedShipment = shipmentRepository.findShipmentByShipmentId(shipment.getShipmentId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (addedShipment == null)
            throw new IllegalArgumentException("Shipment does not exist in database.");

        updateOrder.setShipment(addedShipment);
        orderRepository.save(updateOrder);
        return updateOrder;
    }


    // --- Delete --- //

    @Transactional
    public boolean deleteOrder(Order order) {
        int id = order.getOrderId();
        Order orderTBD = orderRepository.findOrderByOrderId(id);
        if (orderTBD == null) {
            throw new IllegalArgumentException("Order does not exist to be deleted.");
        }
        orderRepository.deleteByOrderId(id);
        return true;
    }


    // --- Update --- //

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

    @Transactional
    public Order updateOrderAmount(Order order, double amount){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative.");

        updateOrder.setTotalAmount(amount);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    @Transactional
    public Order updateOrderStatus(Order order, OrderStatus orderStatus){
        if (order == null)
            throw new IllegalArgumentException("An order is required to be updated.");

        Order updateOrder = orderRepository.findOrderByOrderId(order.getOrderId());
        if (updateOrder == null)
            throw new IllegalArgumentException("Order does not exist in database.");
        if (orderStatus == null)
            throw new IllegalArgumentException("Order Status cannot be empty.");

        updateOrder.setOrderStatus(orderStatus);
        orderRepository.save(updateOrder);
        return updateOrder;
    }

    //helper methods

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

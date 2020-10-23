package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.Order;
import ca.mcgill.ecse321.artgalleryapplication.service.ArtworkService;
import ca.mcgill.ecse321.artgalleryapplication.service.OrderService;
import ca.mcgill.ecse321.artgalleryapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class OrderRestController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArtworkService artworkService;

    @GetMapping(value = { "/orders", "/orders/" })
    public List<OrderDto> getAllOrders() throws IllegalArgumentException {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orderService.getAllOrders()) {
            orderDtos.add(convertToDto(order));
        }
        return orderDtos;
    }

    @GetMapping(value = { "/order/{id}", "/order/{id}/" })
    public OrderDto getOrderById(@PathVariable("id") int id) throws IllegalArgumentException {
        Order order = orderService.getOrderById(id);
        return convertToDto(order);
    }


    @PostMapping(value = { "/placeOrder/{id}", "/placeOrder/{id}/" })
    public OrderDto placeOrder(
            @PathVariable("id") int id,
            @RequestParam("username") String username,
            @RequestParam("artworkId") int artworkId
    ) throws IllegalArgumentException {
        Order order = orderService.placeOrder(artworkId, username);

        return convertToDto(order);
    }


    // --- Update Order --- //

    @PutMapping(value = { "/order/addPayment", "/order/addPayment/" })
    public OrderDto addPaymentToOrder(OrderDto orderDto, PaymentDto paymentDto) {
        if (paymentDto == null)
            throw new NullPointerException("A payment is required to update order");
        // Todo: get PaymentDto
        //OrderDto updatedOrder = orderService.addPaymentToOrder(orderDto.getOrderId(), paymentDto.getId());

        Order updatedOrder = orderService.addPaymentToOrder(orderDto.getOrderId(), 0);
        return convertToDto(updatedOrder);
    }

    @PutMapping(value = { "/order/addShipment", "/order/addShipment/" })
    public OrderDto addShipmentToOrder(OrderDto orderDto, ShipmentDto shipmentDto) {
        if (shipmentDto == null)
            throw new NullPointerException("A payment is required to update order");
        // Todo: get ShipmentDto
        //OrderDto updatedOrder = orderService.addShipmentToOrder(orderDto.getOrderId(), shipmentDto.getId());

        Order updatedOrder = orderService.addShipmentToOrder(orderDto.getOrderId(), 0);
        return convertToDto(updatedOrder);
    }

    // --- Delete --- //

    @DeleteMapping(value = { "/deleteOrder/{id}", "/deleteOrder/{id}/" })
    public boolean deleteOrder(@PathVariable("id") int id) throws IllegalArgumentException {
        return orderService.deleteOrder(id);
    }

    @DeleteMapping(value = { "/deleteOrder", "/deleteOrder/" })
    public boolean deleteOrder(OrderDto orderDto) throws IllegalArgumentException {
        if (orderDto == null)
            throw new IllegalArgumentException("An order is required to be deleted.");

        return orderService.deleteOrder(orderDto.getOrderId());
    }


    // --- Dto Conversion --- //

    // TODO: Need UserProfileDto and ArtworkDto
    private OrderDto convertToDto(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Order does not exist");

//        UserProfileDto userDto = convertToDto(order.getCustomer());
//        OrderDto orderDto = new OrderDto(order.getOrderId(), userDto, artworkDto, order.getOrderDate(), order.getOrderTime());
        OrderDto orderDto = new OrderDto(order.getOrderId(), new UserProfileDto(), new ArtworkDto(), order.getOrderDate(), order.getOrderTime());
        return orderDto;
    }
//
//    private OrderDto convertToDto(Order order) {
//        if (order == null)
//            throw new IllegalArgumentException("Order does not exist");
//
//        UserProfileDto userDto = convertToDto(order.getCustomer());
//        OrderDto orderDto = new OrderDto(order.getOrderId(), userDto, artworkDto, order.getOrderDate(), order.getOrderTime());
//        return orderDto;
//    }

}

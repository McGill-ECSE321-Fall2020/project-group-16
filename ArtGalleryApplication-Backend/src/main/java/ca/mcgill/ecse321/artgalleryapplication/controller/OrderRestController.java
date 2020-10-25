package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.Order;
import ca.mcgill.ecse321.artgalleryapplication.model.OrderStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
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


    // --- Getters --- //

    /**
     *
     * @return a list of OrderDtos
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/orders", "/orders/" })
    public List<OrderDto> getAllOrders() throws IllegalArgumentException {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orderService.getAllOrders()) {
            orderDtos.add(convertToDto(order));
        }
        return orderDtos;
    }

    /**
     *
     * @param id
     * @return an OrderDto
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/order/{id}", "/order/{id}/" })
    public OrderDto getOrderById(@PathVariable("id") int id) throws IllegalArgumentException {
        List<OrderDto> orderDtos = new ArrayList<>();
        Order order = orderService.getOrderById(id);
        return convertToDto(order);
    }

    /**
     *
     * @param customer
     * @return a list of OrderDtos
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/customerOrders", "/customerOrders/" })
    public List<OrderDto> getOrdersByUser(UserProfile customer) throws IllegalArgumentException {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orderService.getOrdersByUser(customer.getUsername())) {
            orderDtos.add(convertToDto(order));
        }
        return orderDtos;
    }


    // --- Create --- //

    /**
     *
     * @param username
     * @param artworkId
     * @return an OrderDto
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/placeOrder", "/placeOrder/" })
    public OrderDto placeOrder(
            @RequestParam("username") String username,
            @RequestParam("artworkId") int artworkId
    ) throws IllegalArgumentException {

        Order order = orderService.placeOrder(artworkId, username);
        return convertToDto(order);
    }


    // --- Update Order --- //

    /**
     *
     * @param orderDto
     * @param paymentDto
     * @return an updated OrderDto
     */
    @PutMapping(value = { "/order/addPayment", "/order/addPayment/" })
    public OrderDto addPaymentToOrder(OrderDto orderDto, PaymentDto paymentDto) {
        // Todo: get PaymentDto
        //OrderDto updatedOrder = orderService.addPaymentToOrder(orderDto.getOrderId(), paymentDto.getId());

        Order updatedOrder = orderService.addPaymentToOrder(orderDto.getOrderId(), 0);
        return convertToDto(updatedOrder);
    }

    /**
     *
     * @param orderDto
     * @param shipmentDto
     * @return an updated OrderDto
     */
    @PutMapping(value = { "/order/addShipment", "/order/addShipment/" })
    public OrderDto addShipmentToOrder(OrderDto orderDto, ShipmentDto shipmentDto) {
        // Todo: get ShipmentDto
        //OrderDto updatedOrder = orderService.addShipmentToOrder(orderDto.getOrderId(), shipmentDto.getId());

        Order updatedOrder = orderService.addShipmentToOrder(orderDto.getOrderId(), 0);
        return convertToDto(updatedOrder);
    }

    /**
     *
     * @param id
     * @param orderStatus
     * @return an updated OrderDto
     */
    @PutMapping(value = { "/order/updateStatus/{id}", "/order/updateStatus/{id}/" })
    public OrderDto updateOrderStatus(@PathVariable("id") int id, OrderStatus orderStatus) {
        Order updatedOrder = orderService.updateOrderStatus(id, orderStatus);
        return convertToDto(updatedOrder);
    }

    /**
     *
     * @param id
     * @param amount
     * @return an updated OrderDto
     */
    @PutMapping(value = { "/order/updateAmount/{id}", "/order/updateAmount/{id}/" })
    public OrderDto updateOrderAmount(@PathVariable("id") int id, @RequestParam("amount") double amount) {
        Order updatedOrder = orderService.updateOrderAmount(id, amount);
        return convertToDto(updatedOrder);
    }


    // --- Delete --- //

    /**
     *
     * @param id
     * @return boolean
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = { "/deleteOrder/{id}", "/deleteOrder/{id}/" })
    public boolean deleteOrder(@PathVariable("id") int id) throws IllegalArgumentException {
        return orderService.deleteOrder(id);
    }

    /**
     *
     * @param orderDto
     * @return boolean
     * @throws IllegalArgumentException
     */
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
        return new OrderDto(order.getOrderId(), new UserProfileDto(), new ArtworkDto(), order.getOrderDate(), order.getOrderTime());
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

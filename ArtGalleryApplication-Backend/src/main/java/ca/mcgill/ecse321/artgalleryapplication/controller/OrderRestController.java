package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.OrderStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.PaymentForm;
import ca.mcgill.ecse321.artgalleryapplication.service.OrderService;
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
import java.util.List;
import java.util.stream.Collectors;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;

@CrossOrigin(origins = "*")
@RestController
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    // --- Create --- //

    /**
     *
     * @param username
     * @param artworkId
     * @return an OrderDto
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/orders/place-order/{username}", "/orders/place-order/{username}/" })
    public OrderDto placeOrder(
            @PathVariable("username") String username,
            @RequestParam("artworkId") int artworkId
    ) throws IllegalArgumentException {

        return convertToDto(orderService.placeOrder(artworkId, username));
    }

//    @PostMapping(value = { "/orders/place-order/{username}/{artworkId}", "/orders/place-order/{username}/{artworkId}/" })
//    public OrderDto placeOrder(
//            @PathVariable("username") String username,
//            @PathVariable("artworkId") int artworkId,
//            @RequestParam("paymentForm") PaymentForm paymentForm,
//            @RequestParam("cardNumber") String cardNumber,
//            @RequestParam("expirationDate") Date expirationDate,
//            @RequestParam("cvv") int cvv,
//            @RequestParam("toGallery") Boolean toGallery,
//            @RequestParam("estimatedArrivalTime") Time estimatedArrivalTime,
//            @RequestParam("estimatedArrivalDate") Date estimatedArrivalDate,
//            @RequestParam("retStreetAddress") String retStreetAddress,
//            @RequestParam("retStreetAddress") String retStreetAddress2,
//            @RequestParam("retStreetAddress") String retPostalCode,
//            @RequestParam("retStreetAddress") String retCity,
//            @RequestParam("retStreetAddress") String retProvince,
//            @RequestParam("retStreetAddress") String retCountry,
//            @RequestParam("destStreetAddress") String destStreetAddress,
//            @RequestParam("destStreetAddress") String destStreetAddress2,
//            @RequestParam("destStreetAddress") String destPostalCode,
//            @RequestParam("destStreetAddress") String destCity,
//            @RequestParam("destStreetAddress") String destProvince,
//            @RequestParam("destStreetAddress") String destCountry
//    ) throws IllegalArgumentException {
//
//        OrderDto initOrder = convertToDto(orderService.placeOrder(artworkId, username));
//        AddressDto retAddress =
//
//        return convertToDto(orderService.placeOrder(artworkId, username));
//    }


    // --- Getters --- //

    /**
     *
     * @return a list of OrderDtos
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/orders", "/orders/" })
    public List<OrderDto> getAllOrders() throws IllegalArgumentException {
        return orderService.getAllOrders().stream().map(ConvertToDto::convertToDto).collect(Collectors.toList());
    }

    /**
     *
     * @param id
     * @return an OrderDto
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/orders/{id}", "/orders/{id}/" })
    public OrderDto getOrderById(@PathVariable("id") int id) throws IllegalArgumentException {
        return convertToDto(orderService.getOrderById(id));
    }

    /**
     *
     * @param status
     * @return a list of OrderDtos
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/orders/get-by-status/{status}", "/orders/get-by-status/{status}" })
    public List<OrderDto> getOrdersByStatus(@PathVariable("status") OrderStatus status) throws IllegalArgumentException {
        return orderService.getOrdersByStatus(status).stream().map(ConvertToDto::convertToDto).collect(Collectors.toList());
    }

    /**
     *
     * @param username
     * @return a list of OrderDtos
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/orders/get-by-user/{username}", "/orders/get-by-user/{username}" })
    public List<OrderDto> getOrdersByUser(@PathVariable("username") String username) throws IllegalArgumentException {
        return orderService.getOrdersByUser(username).stream().map(ConvertToDto::convertToDto).collect(Collectors.toList());
    }

    @GetMapping(value = {"/orders/get-current-order/{username}", "/orders/get-current-order/{username}/"})
    public OrderDto getCurrentOrder(@PathVariable("username") String username) throws IllegalArgumentException {
        return convertToDto(orderService.getCurrentOrder(username));
    }

    @GetMapping(value = {"/orders/get-past-orders/{username}", "/orders/get-past-orders/{username}/"})
    public List<OrderDto> getPastOrders(@PathVariable("username") String username) {
        return orderService.getPastOrders(username).stream().map(o -> convertToDto(o)).collect(Collectors.toList());
    }

    // --- Update Order --- //

    /**
     *
     * @param orderId
     * @param paymentId
     * @return an updated OrderDto
     */
    @PutMapping(value = { "/orders/{id}/add-payment", "/orders/{id}/add-payment/" })
    public OrderDto addPaymentToOrder(@PathVariable("id") int orderId,
                                      @RequestParam("paymentId") int paymentId) {
        return convertToDto(orderService.addPaymentToOrder(orderId, paymentId));
    }

    /**
     *
     * @param orderId
     * @param shipmentId
     * @return an updated OrderDto
     */
    @PutMapping(value = { "/orders/{id}/add-shipment", "/orders/{id}/add-shipment/" })
    public OrderDto addShipmentToOrder(@PathVariable("id") int orderId,
                                       @RequestParam("shipmentId") int shipmentId) {
        return convertToDto(orderService.addShipmentToOrder(orderId, shipmentId));
    }

    @PutMapping(value = { "/orders/{id}/add-payment-shipment", "/orders/{id}/add-payment-shipment/" })
    public OrderDto addShipmentToOrder(@PathVariable("id") int orderId,
                                       @RequestParam("paymentId") int paymentId,
                                       @RequestParam("shipmentId") int shipmentId) {
        return convertToDto(orderService.addPaymentAndShipmentToOrder(orderId, paymentId, shipmentId));
    }

    /**
     *
     * @param id
     * @param orderStatus
     * @return an updated OrderDto
     */
    @PutMapping(value = { "/orders/{id}/update-status", "/orders/{id}/update-status/" })
    public OrderDto updateOrderStatus(@PathVariable("id") int id,
                                      @RequestParam("orderStatus") OrderStatus orderStatus) {
        return convertToDto(orderService.updateOrderStatus(id, orderStatus));
    }

    /**
     *
     * @param id
     * @param amount
     * @return an updated OrderDto
     */
    @PutMapping(value = { "/orders/{id}/update-amount", "/orders/{id}/update-amount/" })
    public OrderDto updateOrderAmount(@PathVariable("id") int id,
                                      @RequestParam("amount") double amount) {
        return convertToDto(orderService.updateOrderAmount(id, amount));
    }


    // --- Delete --- //

    /**
     *
     * @param id
     * @return boolean
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = { "/orders/{id}", "/orders/{id}/" })
    public boolean deleteOrder(@PathVariable("id") int id) throws IllegalArgumentException {
        return orderService.deleteOrder(id);
    }

    /**
     *
     * @param orderDto
     * @return boolean
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = { "/orders", "/orders/" })
    public boolean deleteOrder(OrderDto orderDto) throws IllegalArgumentException {
        if (orderDto == null)
            throw new IllegalArgumentException("An order is required to be deleted.");

        return orderService.deleteOrder(orderDto.getOrderId());
    }

}

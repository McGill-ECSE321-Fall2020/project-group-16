package ca.mcgill.ecse321.artgalleryapplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import  ca.mcgill.ecse321.artgalleryapplication.model.*;

@ExtendWith(MockitoExtension.class)
public class TestOrderService {
    @Mock
    private OrderRepository orderDao;
    @Mock
    private ArtworkRepository artworkDao;
    @Mock
    private UserRepository userDao;
    @Mock
    private ShipmentRepository shipmentDao;
    @Mock
    private PaymentRepository paymentDao;

    @InjectMocks
    private OrderService service;


    // --- Test Constants --- //

    private static final int ORDER_ID = 100;
    private static final int NONEXISTING_ID = 1000;
    private static final OrderStatus ORDER_STATUS = OrderStatus.PaymentPending;
    private static final Date DATE = Date.valueOf(LocalDate.now());
    private static final Time TIME = Time.valueOf("12:00:00");

    private static final String USERNAME_1 = "aomidi";
    private static final String NONEXISTING_USERNAME = "banana";

    private static final int ARTWORK_ID_1 = 101;
    private static final double ARTWORK_PRICE_1 = 5000.00;

    private static final int PAYMENT_ID_1 = 102;

    private static final int SHIPMENT_ID_1 = 103;


    @BeforeEach
    public void setMockOutput() {
        lenient().when(orderDao.findOrderByOrderId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ORDER_ID)) {
                return setupOrder();
            } else {
                return null;
            }
        });
        lenient().when(orderDao.findByOrderStatus(any(OrderStatus.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<Order> orderList = new ArrayList<>();
            Order order = setupOrder();

            if (invocation.getArgument(0).equals(order.getOrderStatus())) {
                orderList.add(order);
            }

            return orderList;
        });
        lenient().when(orderDao.findOrdersByCustomerAndOrderStatus(any(UserProfile.class), any(OrderStatus.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<Order> orderList = new ArrayList<>();
            Order order = setupOrder();
            UserProfile userProfile = invocation.getArgument(0);

            if (userProfile.getUsername().equals(order.getCustomer().getUsername())
            && invocation.getArgument(1).equals(order.getOrderStatus())) {
                orderList.add(order);
            }

            return orderList;
        });
        lenient().when(orderDao.findByCustomer(any(UserProfile.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<Order> orderList = new ArrayList<>();
            Order order = setupOrder();
            UserProfile userProfile = invocation.getArgument(0);

            if (userProfile.getUsername().equals(order.getCustomer().getUsername())) {
                orderList.add(order);
            }

            return orderList;
        });
        lenient().when(orderDao.findByPayment(any(Payment.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<Order> orderList = new ArrayList<>();
            Order order = setupOrder();

            if (invocation.getArgument(0).equals(order.getPayment())) {
                orderList.add(order);
            }

            return orderList;
        });
        lenient().when(orderDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<Order> orderList = new ArrayList<>();
            orderList.add(setupOrder());
            return orderList;
        });

        lenient().when(userDao.findByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USERNAME_1)) {
                return setupUser();
            } else {
                return null;
            }
        });

        lenient().when(artworkDao.findArtworkByArtworkId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ARTWORK_ID_1)) {
                return setupArtwork();
            } else {
                return null;
            }
        });

        lenient().when(paymentDao.findPaymentByPaymentId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(PAYMENT_ID_1)) {
                return setupPayment();
            } else {
                return null;
            }
        });

        lenient().when(shipmentDao.findShipmentByShipmentId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(SHIPMENT_ID_1)) {
                return setupShipment();
            } else {
                return null;
            }
        });
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(orderDao.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(userDao.save(any(UserProfile.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(artworkDao.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(paymentDao.save(any(Payment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(shipmentDao.save(any(Shipment.class))).thenAnswer(returnParameterAsAnswer);
    }

    // --- Create Tests --- //

    @Test
    public void testPlaceOrder() {
        Order order = null;
        try {
            order = service.placeOrder(ARTWORK_ID_1, USERNAME_1);
            order.setOrderId(ORDER_ID);
        } catch (ApiRequestException e) {
            e.printStackTrace();
            fail();
        }

        assertOrder(order, ORDER_STATUS, ARTWORK_PRICE_1);
    }

    @Test
    public void testPlaceOrderNoUsername() {
        Order order = null;
        try {
            order = service.placeOrder(ARTWORK_ID_1, "");
        } catch (ApiRequestException e) {
            assertEquals("Customer Username cannot be empty.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testPlaceOrderNoUser() {
        Order order = null;
        try {
            order = service.placeOrder(ARTWORK_ID_1, NONEXISTING_USERNAME);
        } catch (ApiRequestException e) {
            assertEquals("No user associated with this username.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testPlaceOrderNoArtwork() {
        Order order = null;
        try {
            order = service.placeOrder(NONEXISTING_ID, USERNAME_1);
        } catch (ApiRequestException e) {
            assertEquals("No artwork associated with this artworkId.", e.getMessage());
        }
        assertNull(order);
    }


    // --- Delete Tests --- //

    @Test
    public void testDeleteOrder(){
        assertTrue(service.deleteOrder(ORDER_ID));
    }

    @Test
    public void testDeleteOrderNoOrder(){
        Order order = null;
        try {
            service.deleteOrder(NONEXISTING_ID);
        } catch (ApiRequestException e) {
            assertEquals("Order does not exist to be deleted.", e.getMessage());
        }
        assertNull(order);
    }


    // --- Getter Tests --- //

    @Test
    public void testGetOrderById(){
        Order order = null;
        try {
            order = service.getOrderById(ORDER_ID);
        } catch (ApiRequestException e) {
            e.printStackTrace();
            fail();
        }

        assertOrder(order, ORDER_STATUS, ARTWORK_PRICE_1);
    }

    @Test
    public void testGetOrderByIdNoOrder(){
        Order order = null;
        try {
            order = service.getOrderById(NONEXISTING_ID);
        } catch (ApiRequestException e) {
            assertEquals("No Order associated with this id.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testGetOrdersByUsers(){
        List<Order> orders = service.getOrdersByUser(USERNAME_1);

        assertEquals(1, orders.size());
        assertOrder(orders.get(0), ORDER_STATUS, ARTWORK_PRICE_1);
    }

    @Test
    public void testGetOrdersByUsersNoUser(){
        List<Order> orders = null;
        try {
            orders = service.getOrdersByUser(NONEXISTING_USERNAME);
        } catch (ApiRequestException e) {
            assertEquals("No user associated with this username.", e.getMessage());
        }
        assertNull(orders);
    }

    @Test
    public void testGetOrdersByStatus(){
        List<Order> orders = service.getOrdersByStatus(ORDER_STATUS);

        assertEquals(1, orders.size());
        assertOrder(orders.get(0), ORDER_STATUS, ARTWORK_PRICE_1);
    }

    @Test
    public void testGetOrdersByStatusNullStatus(){
        List<Order> orders = null;
        try {
            orders = service.getOrdersByStatus(null);
        } catch (ApiRequestException e) {
            assertEquals("Not a valid OrderStatus.", e.getMessage());
        }
        assertNull(orders);
    }

    @Test
    public void testGetCurrentOrder(){
        Order order = service.getCurrentOrder(USERNAME_1);

        assertOrder(order, ORDER_STATUS, ARTWORK_PRICE_1);
    }

    @Test
    public void testGetCurrentOrderNoUser(){
        Order order = null;
        try {
            order = service.getCurrentOrder(NONEXISTING_USERNAME);
        } catch (ApiRequestException e) {
            assertEquals("No user associated with this username.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testGetPastOrders(){
        List<Order> orders = service.getPastOrders(USERNAME_1);

        assertEquals(1, orders.size());
        assertOrder(orders.get(0), ORDER_STATUS, ARTWORK_PRICE_1);
    }

    @Test
    public void testGetPastOrdersNoUser(){
        List<Order> orders = null;
        try {
            orders = service.getPastOrders(NONEXISTING_USERNAME);
        } catch (ApiRequestException e) {
            assertEquals("No user associated with this username.", e.getMessage());
        }
        assertNull(orders);
    }

    @Test
    public void testGetAllOrders(){
        List<Order> orders = service.getAllOrders();

        assertEquals(1, orders.size());
        assertOrder(orders.get(0), ORDER_STATUS, ARTWORK_PRICE_1);
    }


    // --- Add Tests --- //

    @Test
    public void testAddPaymentToOrder() {
        Order order = null;
        try {
            order = service.addPaymentToOrder(ORDER_ID, PAYMENT_ID_1);
        } catch (ApiRequestException e) {
            e.printStackTrace();
            fail();
        }

        assertOrder(order, OrderStatus.Placed, ARTWORK_PRICE_1);
    }

    @Test
    public void testAddPaymentToOrderNoOrder() {
        Order order = null;
        try {
            order = service.addPaymentToOrder(NONEXISTING_ID, PAYMENT_ID_1);
        } catch (ApiRequestException e) {
            assertEquals("Order does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testAddPaymentToOrderNoPayment() {
        Order order = null;
        try {
            order = service.addPaymentToOrder(ORDER_ID, NONEXISTING_ID);
        } catch (ApiRequestException e) {
            assertEquals("Payment does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testAddShipmentToOrder() {
        Order order = null;
        try {
            order = service.addShipmentToOrder(ORDER_ID, SHIPMENT_ID_1);
        } catch (ApiRequestException e) {
            e.printStackTrace();
            fail();
        }

        assertOrder(order, OrderStatus.Shipped, ARTWORK_PRICE_1);
    }

    @Test
    public void testAddShipmentToOrderNoOrder() {
        Order order = null;
        try {
            order = service.addShipmentToOrder(NONEXISTING_ID, SHIPMENT_ID_1);
        } catch (ApiRequestException e) {
            assertEquals("Order does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testAddShipmentToOrderNoShipment() {
        Order order = null;
        try {
            order = service.addShipmentToOrder(ORDER_ID, NONEXISTING_ID);
        } catch (ApiRequestException e) {
            assertEquals("Shipment does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testAddPaymentAndShipmentToOrder() {
        Order order = null;
        try {
            order = service.addPaymentAndShipmentToOrder(ORDER_ID, PAYMENT_ID_1, SHIPMENT_ID_1);
        } catch (ApiRequestException e) {
            e.printStackTrace();
            fail();
        }

        assertOrder(order, OrderStatus.Placed, ARTWORK_PRICE_1);
    }

    @Test
    public void testAddPaymentAndShipmentToOrderNoOrder() {
        Order order = null;
        try {
            order = service.addPaymentAndShipmentToOrder(NONEXISTING_ID, PAYMENT_ID_1, SHIPMENT_ID_1);
        } catch (ApiRequestException e) {
            assertEquals("Order does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testAddPaymentAndShipmentToOrderNoShipment() {
        Order order = null;
        try {
            order = service.addPaymentAndShipmentToOrder(ORDER_ID, PAYMENT_ID_1, NONEXISTING_ID);
        } catch (ApiRequestException e) {
            assertEquals("Shipment does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testAddPaymentAndShipmentToOrderNoPayment() {
        Order order = null;
        try {
            order = service.addPaymentAndShipmentToOrder(ORDER_ID, NONEXISTING_ID, SHIPMENT_ID_1);
        } catch (ApiRequestException e) {
            assertEquals("Payment does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }



    // --- Update Tests --- //

    @Test
    public void testUpdateOrderStatus() {
        Order order = null;
        OrderStatus orderStatus = OrderStatus.Delivered;
        try {
            order = service.updateOrderStatus(ORDER_ID, orderStatus);
        } catch (ApiRequestException e) {
            e.printStackTrace();
            fail();
        }

        assertOrder(order, orderStatus, ARTWORK_PRICE_1);
    }

    @Test
    public void testUpdateOrderStatusNoOrder() {
        Order order = null;
        try {
            order = service.updateOrderStatus(NONEXISTING_ID, null);
        } catch (ApiRequestException e) {
            assertEquals("Order does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testUpdateOrderStatusNoOrderStatus() {
        Order order = null;
        try {
            order = service.updateOrderStatus(ORDER_ID, null);
        } catch (ApiRequestException e) {
            assertEquals("Order Status cannot be empty.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testUpdateOrderAmount() {
        Order order = null;
        double amount = 20.99;
        try {
            order = service.updateOrderAmount(ORDER_ID, amount);
        } catch (ApiRequestException e) {
            e.printStackTrace();
            fail();
        }

        assertOrder(order, ORDER_STATUS, amount);
    }

    @Test
    public void testUpdateOrderNoOrder() {
        Order order = null;
        try {
            order = service.updateOrderAmount(NONEXISTING_ID, 1000);
        } catch (ApiRequestException e) {
            assertEquals("Order does not exist in database.", e.getMessage());
        }
        assertNull(order);
    }

    @Test
    public void testUpdateOrderAmountNegativeAmount() {
        Order order = null;
        try {
            order = service.updateOrderAmount(ORDER_ID, -1000);
        } catch (ApiRequestException e) {
            assertEquals("Amount cannot be negative.", e.getMessage());
        }
        assertNull(order);
    }


    // --- Helper Methods --- //

    private void assertOrder(Order order, OrderStatus orderStatus, double amount){
        assertNotNull(order);
        assertEquals(TestOrderService.ORDER_ID, order.getOrderId());
        assertEquals(TestOrderService.USERNAME_1, order.getCustomer().getUsername());
        assertEquals(TestOrderService.ARTWORK_ID_1, order.getArtwork().getArtworkId());
        assertEquals(orderStatus, order.getOrderStatus());

        if (order.getPayment() != null)
            assertEquals(TestOrderService.PAYMENT_ID_1, order.getPayment().getPaymentId());
        if (order.getShipment() != null)
            assertEquals(TestOrderService.SHIPMENT_ID_1, order.getShipment().getShipmentId());

        assertEquals(amount, order.getTotalAmount());
        assertEquals(TestOrderService.DATE, order.getOrderDate());
    }

    private Order setupOrder() {
        Order order = new Order();
        order.setOrderId(ORDER_ID);
        order.setCustomer(setupUser());
        order.setArtwork(setupArtwork());
        order.setOrderStatus(ORDER_STATUS);
        order.setPayment(setupPayment());
        order.setShipment(setupShipment());
        order.setTotalAmount(order.getArtwork().getPrice());
        order.setOrderDate(DATE);
        order.setOrderTime(TIME);

        return order;
    }

    private UserProfile setupUser(){
        UserProfile user = new UserProfile();
        user.setUsername(TestOrderService.USERNAME_1);

        return user;
    }

    private Artwork setupArtwork(){
        Artwork artwork = new Artwork();
        artwork.setArtworkId(TestOrderService.ARTWORK_ID_1);
        artwork.setPrice(TestOrderService.ARTWORK_PRICE_1);

        return artwork;
    }

    private Payment setupPayment(){
        Payment payment = new Payment();
        payment.setPaymentId(TestOrderService.PAYMENT_ID_1);

        return payment;
    }

    private Shipment setupShipment(){
        Shipment shipment = new Shipment();
        shipment.setShipmentId(TestOrderService.SHIPMENT_ID_1);

        return shipment;
    }
}

package ca.mcgill.ecse321.artgalleryapplication.dao;

import ca.mcgill.ecse321.artgalleryapplication.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderPersistenceTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AddressRepository addressRepository;


    @AfterEach
    public void clearDatabase() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        artworkRepository.deleteAll();
        shipmentRepository.deleteAll();
        paymentRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOrder() {
        double totalAmount = 1000.00;
        Date orderDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19));
        int orderId = 123;
        Time orderTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Payment payment = new Payment();
        payment.setCardNumber("1234");
        payment.setCvv(123);
        payment.setExpirationDate(java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19)));
        payment.setPaymentDate(java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19)));
        payment.setPaymentForm(PaymentForm.CreditCard);
        payment.setPaymentId(321);
        payment.setPaymentTime(java.sql.Time.valueOf(LocalTime.of(12, 35)));
        paymentRepository.save(payment);

        Address address = getRandAddress();
        addressRepository.save(address);

        //shipment
        Shipment shipment = new Shipment();
        shipment.setDestination(address);
        shipment.setEstimatedArrivalDate(java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19)));
        shipment.setEstimatedArrivalTime(java.sql.Time.valueOf(LocalTime.of(12, 35)));
        shipment.setReturnAddress(address);
        shipment.setShipmentId(12345);
        shipment.setToGallery(false);
        shipmentRepository.save(shipment);

        //artwork
        Artwork artwork = new Artwork();
        //artwork.setArtist(artists);
        artwork.setArtworkId(123);
        artwork.setArtworkStatus(ArtworkStatus.ForSale);
        artwork.setCollection("non");
        artwork.setCreationDate(java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19)));
        artwork.setDescription("boring");
        artwork.setDimensions("12x12");
        artwork.setImageUrl("idc.org");
        artwork.setMedium("chalk");
        artwork.setPrice(12.99);
        artwork.setTitle("Example");
        artworkRepository.save(artwork);

        //customer
        UserProfile customer = new UserProfile();
        String username = "tester1";
        String password = "testPass1";
        String firstName = "Fname";
        String lastName = "Lname";
        String description = "test case for user profile";
        String profileImageUrl = "http://test.png";

        Boolean isAdmin = true;
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setDescription(description);
        customer.setProfileImageUrl(profileImageUrl);
        customer.setIsAdmin(isAdmin);
        userRepository.save(customer);

        Order order = new Order();
        order.setArtwork(artwork);
        order.setCustomer(customer);
        order.setOrderDate(orderDate);
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.Delivered);
        order.setOrderTime(orderTime);
        order.setPayment(payment);
        order.setShipment(shipment);
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        order = null;

        order = orderRepository.findOrderByOrderId(orderId);
        assertNotNull(order);
        assertEquals(artwork.getArtworkId(), order.getArtwork().getArtworkId());
        assertEquals(customer.getUsername(), order.getCustomer().getUsername());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(orderId, order.getOrderId());
        assertEquals(orderTime, order.getOrderTime());
        assertEquals(payment.getPaymentId(), order.getPayment().getPaymentId());
        assertEquals(shipment.getShipmentId(), order.getShipment().getShipmentId());
        assertEquals(totalAmount, order.getTotalAmount());

    }

    //some helper methods to get example objects:
    public Address getRandAddress() {
        String streetAddress = "1001 Test Rd";
        String streetAddress2 = "101";
        String postalCode = "S7NGT5";
        String city = "Test";
        String province = "TS";
        String country = "Canada";
        int addressId = 101;
        Address address = new Address();
        address.setAddressId(addressId);
        address.setStreetAddress(streetAddress);
        address.setStreetAddress2(streetAddress2);
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setProvince(province);
        address.setCountry(country);
        return address;
    }



}

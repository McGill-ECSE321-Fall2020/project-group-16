package ca.mcgill.ecse321.artgalleryapplication.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.artgalleryapplication.model.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestArtGalleryAppPersistence {

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
	
	@AfterEach
	public void clearDatabase() {
		galleryEventRepository.deleteAll();
		orderRepository.deleteAll();
		userRepository.deleteAll();
		artworkRepository.deleteAll();
		shipmentRepository.deleteAll();
		paymentRepository.deleteAll();
		addressRepository.deleteAll();
		artGalleryApplicationRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadAddress() {
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
		addressRepository.save(address);

		address = null;

		address = addressRepository.findAddressByAddressId(addressId);
		assertNotNull(address);
		assertEquals(addressId, address.getAddressId());
		assertEquals(streetAddress, address.getStreetAddress());
		assertEquals(streetAddress2, address.getStreetAddress2());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(city, address.getCity());
		assertEquals(province, address.getProvince());
		assertEquals(country, address.getCountry());
	}
	
	@Test
	public void testPersistAndLoadShipment() {	
		int returnAddressId = 101;
		String returnStreetAddress = "1001 Test Rd";
		String returnStreetAddress2 = "101";
		String returnPostalCode = "S7N3T5";
		String returnCity = "Test";
		String returnProvince = "TS";
		String returnCountry = "Canada";
		Address returnAddress = new Address();
		returnAddress.setAddressId(returnAddressId);
		returnAddress.setStreetAddress(returnStreetAddress);
		returnAddress.setStreetAddress2(returnStreetAddress2);
		returnAddress.setPostalCode(returnPostalCode);
		returnAddress.setCity(returnCity);
		returnAddress.setProvince(returnProvince);
		returnAddress.setCountry(returnCountry);
		addressRepository.save(returnAddress);
		
		int deliveryAddressId = 100;
		String deliveryStreetAddress = "100 Test Blv";
		String deliveryStreetAddress2 = "100";
		String deliveryPostalCode = "S7N5H5";
		String deliveryCity = "Test1";
		String deliveryProvince = "TE";
		String deliveryCountry = "Canada";
		Address deliveryAddress = new Address();
		deliveryAddress.setAddressId(deliveryAddressId);
		deliveryAddress.setStreetAddress(deliveryStreetAddress);
		deliveryAddress.setStreetAddress2(deliveryStreetAddress2);
		deliveryAddress.setPostalCode(deliveryPostalCode);
		deliveryAddress.setCity(deliveryCity);
		deliveryAddress.setProvince(deliveryProvince);
		deliveryAddress.setCountry(deliveryCountry);
		addressRepository.save(deliveryAddress);
		
		
		int shipmentId = 1;
		Boolean toGallery = false;
		Date estimatedArrivalDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time estimatedArrivalTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Shipment shipment = new Shipment();
		shipment.setShipmentId(shipmentId);
		shipment.setToGallery(toGallery);
		shipment.setEstimatedArrivalDate(estimatedArrivalDate);
		shipment.setEstimatedArrivalTime(estimatedArrivalTime);
		shipment.setReturnAddress(returnAddress);
		shipment.setDestination(deliveryAddress);
		shipmentRepository.save(shipment);
			
		shipment = null;
		
		shipment = shipmentRepository.findShipmentByShipmentId(shipmentId);
		assertNotNull(shipment);
		assertEquals(shipmentId,shipment.getShipmentId());
		assertEquals(toGallery,shipment.getToGallery());
		assertEquals(estimatedArrivalDate,shipment.getEstimatedArrivalDate());
		assertEquals(estimatedArrivalTime,shipment.getEstimatedArrivalTime());
		assertEquals(returnAddressId,shipment.getReturnAddress().getAddressId());
		assertEquals(deliveryAddressId,shipment.getDestination().getAddressId());
	}

	@Test
	public void testPersistAndLoadUserProfile() {
		String username = "tester1";
		String password = "testPass1";
		String firstName = "Fname";
		String lastName = "Lname";
		String description = "test case for user profile";
		String profileImageUrl = "http://test.png";
		Boolean isAdmin = true;
		UserProfile user = new UserProfile();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setDescription(description);
		user.setProfileImageUrl(profileImageUrl);
		user.setIsAdmin(isAdmin);
		
		userRepository.save(user);

		user = null;

		user = userRepository.findUserProfileByUsername(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
	}
	

	@Test
	public void testPersistAndLoadArtwork() {
		//creating a UserProfile for the artist of artwork
		String username = "test123";
		UserProfile artist = new UserProfile();			
		artist.setUsername(username);
		userRepository.save(artist);

		Set<UserProfile> artists = new HashSet<UserProfile>();
		artists.add(artist);
		
		//creating test Artwork
		int artworkId = 123;
		String title = "Mona Lisa";
		String description = "da vinky?";
		Date creationDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.OCTOBER, 7));
		String dimensions = "10 feet x 10 feet";
		String medium = "oil paint";
		String collection = "classics";
		String imageUrl = "monalisa.png";
		double price = 1000000000;
		ArtworkStatus artworkStatus = ArtworkStatus.ForSale;
		
		Artwork artwork = new Artwork();

		//setting Artwork attributes
		artwork.setArtist(artists);

		artwork.setArtworkId(artworkId);
		artwork.setTitle(title);
		artwork.setDescription(description);
		artwork.setCreationDate(creationDate);
		artwork.setDimensions(dimensions);
		artwork.setMedium(medium);
		artwork.setCollection(collection);
		artwork.setImageUrl(imageUrl);
		artwork.setPrice(price);
		artwork.setArtworkStatus(artworkStatus);	
		
		artworkRepository.save(artwork);
		
		//checking everything
		artwork=null;
		artwork = artworkRepository.findArtworkByArtworkId(artworkId);
		assertNotNull(artwork);

		//assertTrue(artwork.getArtist().equals(artists));
		
		assertEquals(artworkId, artwork.getArtworkId());
		assertEquals(title, artwork.getTitle());
		assertEquals(description, artwork.getDescription());
		assertEquals(creationDate, artwork.getCreationDate());
		assertEquals(dimensions, artwork.getDimensions());
		assertEquals(medium, artwork.getMedium());
		assertEquals(collection, artwork.getCollection());
		assertEquals(imageUrl, artwork.getImageUrl());
		assertEquals(price, artwork.getPrice());
		assertEquals(artworkStatus, artwork.getArtworkStatus());
		
	}
	
	@Test
	public void testPersistAndLoadGalleryEvent() {
		String eventName = "learn2Paint";
		String eventDescription = "Bring your prushes and paint, and we'll teach you something new!";
		String eventImageUrl = "abcdefg";
		int eventId = 12345;
		Date eventDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19));
		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(12, 35));
		
		
		UserProfile participant = new UserProfile();
		String username = "tester1";
		String password = "testPass1";
		String firstName = "Fname";
		String lastName = "Lname";
		String description = "test case for user profile";
		String profileImageUrl = "http://test.png";
		Boolean isAdmin = true;
		participant.setUsername(username);
		participant.setPassword(password);
		participant.setFirstName(firstName);
		participant.setLastName(lastName);
		participant.setDescription(description);
		participant.setProfileImageUrl(profileImageUrl);
		participant.setIsAdmin(isAdmin);
		
		Set<UserProfile> participants = new HashSet<UserProfile>();
		participants.add(participant);
		userRepository.save(participant);
		
		GalleryEvent galleryEvent = new GalleryEvent();
		galleryEvent.setEndTime(endTime);
		galleryEvent.setEventDate(eventDate);
		galleryEvent.setEventDescription(eventDescription);	
		galleryEvent.setEventId(eventId);
		galleryEvent.setEventImageUrl(eventImageUrl);
		galleryEvent.setEventName(eventName);
		galleryEvent.setStartTime(startTime);
		galleryEvent.setParticipants(participants);
		
		galleryEventRepository.save(galleryEvent);
		
		galleryEvent = null;
		galleryEvent = galleryEventRepository.findGalleryEventByEventId(eventId);
		assertNotNull(galleryEvent);
		assertEquals(eventId, galleryEvent.getEventId());
		assertEquals(eventName, galleryEvent.getEventName());
		assertEquals(eventDescription, galleryEvent.getEventDescription());
		assertEquals(eventImageUrl, galleryEvent.getEventImageUrl());
		assertEquals(eventDate, galleryEvent.getEventDate());
		assertEquals(startTime, galleryEvent.getStartTime());
		assertEquals(endTime, galleryEvent.getEndTime());
		//Object[] p = participants.toArray();
		//Object[] geP = galleryEvent.getParticipants().toArray();
		//assertTrue(p[0].equals(geP[0]));
	}
	
	@Test
	public void testPersistAndLoadOrder() {
		double totalAmount = 1000.00;
		Date orderDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19));
		int orderId = 123;
		Time orderTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Payment payment = new Payment();
		payment.setCardNumber(1234);
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
		
		customer = null;
		customer = userRepository.findUserProfileByUsername(username);
		
		
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
		assertTrue(customer.getUsername().equals(order.getCustomer().getUsername()));
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
	
	@Test
	public void testPersistAndLoadPayment() {
		Date paymentDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.NOVEMBER, 19));
		long cardNumber = (long) 1234567891234567L;
		Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
		int cvv = 123;
		int paymentId = 12345;
		Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		
		Payment payment = new Payment();
		payment.setPaymentDate(paymentDate);
		payment.setCardNumber(cardNumber);
		payment.setExpirationDate(expirationDate);
		payment.setCvv(cvv);
		payment.setPaymentId(paymentId);
		payment.setPaymentTime(paymentTime);
		
		paymentRepository.save(payment);
		
		payment = null;
		
		payment = paymentRepository.findPaymentByPaymentId(paymentId);
		
		assertNotNull(payment);
		assertEquals(paymentId, payment.getPaymentId());
		assertEquals(paymentDate, payment.getPaymentDate());
		assertEquals(cardNumber, payment.getCardNumber());
		assertEquals(expirationDate, payment.getExpirationDate());
		assertEquals(cvv, payment.getCvv());
		assertEquals(paymentTime, payment.getPaymentTime());
		
	}

	@Test
	public void testPersistAndLoadArtGalleryApplication() {
		int applicationId=1234;
		
		ArtGalleryApplication artGalleryApp = new ArtGalleryApplication();
		artGalleryApp.setApplicationId(applicationId);

		artGalleryApplicationRepository.save(artGalleryApp);
		
	}
	
}

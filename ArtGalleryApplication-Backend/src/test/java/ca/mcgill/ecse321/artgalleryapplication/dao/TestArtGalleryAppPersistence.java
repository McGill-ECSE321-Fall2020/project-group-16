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

import org.apache.catalina.User;
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

	/**
	 *	Contributors: Alena Midgen, Dina Shoham, Evan Wechsler, Arian Omidi, Rodolphe Baladi
	 *
	 * 	Tests:
	 * 		Persistence of Objects.
	 * 		Persistence of Attributes.
	 * 		Persistence of References.
	 *
	 *
	 */

	@Test
	public void testPersistAndLoadAddress() {
		String streetAddress = "1001 Test Rd";
		String streetAddress2 = "101";
		String postalCode = "S7NGT5";
		String city = "Test";
		String province = "TS";
		String country = "Canada";
		//int addressId = 101;
		Address address = new Address();
		address.setStreetAddress(streetAddress);
		address.setStreetAddress2(streetAddress2);
		address.setPostalCode(postalCode);
		address.setCity(city);
		address.setProvince(province);
		address.setCountry(country);
		addressRepository.save(address);
		int addressId = address.getAddressId();

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
		//int returnAddressId = 101;
		String returnStreetAddress = "1001 Test Rd";
		String returnStreetAddress2 = "101";
		String returnPostalCode = "S7N3T5";
		String returnCity = "Test";
		String returnProvince = "TS";
		String returnCountry = "Canada";
		Address returnAddress = new Address();
		//returnAddress.setAddressId(returnAddressId);
		returnAddress.setStreetAddress(returnStreetAddress);
		returnAddress.setStreetAddress2(returnStreetAddress2);
		returnAddress.setPostalCode(returnPostalCode);
		returnAddress.setCity(returnCity);
		returnAddress.setProvince(returnProvince);
		returnAddress.setCountry(returnCountry);
		addressRepository.save(returnAddress);
		int returnAddressId = returnAddress.getAddressId();

		//int deliveryAddressId = 100;
		String deliveryStreetAddress = "100 Test Blv";
		String deliveryStreetAddress2 = "100";
		String deliveryPostalCode = "S7N5H5";
		String deliveryCity = "Test1";
		String deliveryProvince = "TE";
		String deliveryCountry = "Canada";
		Address deliveryAddress = new Address();
		//deliveryAddress.setAddressId(deliveryAddressId);
		deliveryAddress.setStreetAddress(deliveryStreetAddress);
		deliveryAddress.setStreetAddress2(deliveryStreetAddress2);
		deliveryAddress.setPostalCode(deliveryPostalCode);
		deliveryAddress.setCity(deliveryCity);
		deliveryAddress.setProvince(deliveryProvince);
		deliveryAddress.setCountry(deliveryCountry);
		addressRepository.save(deliveryAddress);
		int deliveryAddressId = deliveryAddress.getAddressId();

		//int shipmentId = 1;
		Boolean toGallery = false;
		Date estimatedArrivalDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time estimatedArrivalTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Shipment shipment = new Shipment();
		//shipment.setShipmentId(shipmentId);
		shipment.setToGallery(toGallery);
		shipment.setEstimatedArrivalDate(estimatedArrivalDate);
		shipment.setEstimatedArrivalTime(estimatedArrivalTime);
		shipment.setReturnAddress(returnAddress);
		shipment.setDestination(deliveryAddress);
		shipmentRepository.save(shipment);
		int shipmentId  = shipment.getShipmentId();

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

		//user
		UserProfile user = new UserProfile();
		String username = "zeNewRodolpheTest";
		String password = "testPass1";
		String firstName = "Fname";
		String lastName = "Lname";
		String description = "test case for user profile";
		String profileImageUrl = "http://test.png";

		Boolean isAdmin = true;
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setDescription(description);
		user.setProfileImageUrl(profileImageUrl);
		user.setIsAdmin(isAdmin);

		//Order order = new Order();
		//user.setCurrentOrder(order);
		//int orderId = 1234;
		//order.setCustomer(user);
		//order.setOrderId(orderId);

		//Artwork artwork = new Artwork();
		//artwork.setArtist();
		//order.setArtwork(artwork);

		//HashSet<Artwork> artworks = new HashSet<>();
		//artworks.add(artwork);
		//user.setArtwork(artworks);

		//artworkRepository.save(artwork);
		userRepository.save(user);
		//orderRepository.save(order);
		//int orderId = order.getOrderId();

		user = null;
		user = userRepository.findByUsername(username);

		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(firstName, user.getFirstName());
		//assertEquals(orderId, user.getCurrentOrder().getOrderId());
	}


	@Test
	public void testPersistAndLoadArtwork() {
		UserProfile artist = new UserProfile();
		Artwork artwork = new Artwork();

		String title = "Mona Lisa";
		artwork.setTitle(title);
		String username = "tester1";
		String firstName = "Fname";
		artist.setUsername(username);
		artist.setFirstName(firstName);

		Set<UserProfile> artists = new HashSet<UserProfile>();
		artists.add(artist);
		artwork.setArtist(artists);

		HashSet<Artwork> artworks = new HashSet<Artwork>();
		artworks.add(artwork);
		artist.setArtwork(artworks);

		artworkRepository.save(artwork);
		int artworkId = artwork.getArtworkId();

		artwork = null;

		artwork = artworkRepository.findArtworkByArtworkId(artworkId);
		assertNotNull(artwork);
		assertEquals(title, artwork.getTitle());
	}

	@Test
	public void testPersistAndLoadGalleryEvent() {
		UserProfile participant = new UserProfile();
		GalleryEvent galleryEvent = new GalleryEvent();

		String eventName = "learn2Paint";
		galleryEvent.setEventName(eventName);
		String username = "zeAllnewTester";
		String firstName = "Fname";
		participant.setUsername(username);
		participant.setFirstName(firstName);


		Set<UserProfile> participants = new HashSet<UserProfile>();
		participants.add(participant);
		Set<GalleryEvent> events = new HashSet<>();
		events.add(galleryEvent);
		participant.setGalleryEvent(events);
		galleryEvent.setParticipants(participants);

		userRepository.save(participant);
		galleryEventRepository.save(galleryEvent);
		int eventId = galleryEvent.getEventId();

		galleryEvent = null;

		galleryEvent = galleryEventRepository.findGalleryEventByEventId(eventId);
		assertNotNull(galleryEvent);
		assertEquals(eventId, galleryEvent.getEventId());
		assertEquals(eventName, galleryEvent.getEventName());


		participant = null;

		participant = userRepository.findByUsername(username);
		assertNotNull(participant);
	}

	@Test
	public void testPersistAndLoadOrder() {
		String title = "zeArtTitle";
		String username = "zeBrandNewUserRODO";
		String firstName = "ZeREUREU";

		Artwork artwork = new Artwork();
		UserProfile customer = new UserProfile();
		UserProfile artist = new UserProfile();

		artist.setUsername("zeUNIQUEartist");
		HashSet<Artwork> artworks = new HashSet<>();
		artworks.add(artwork);
		artist.setArtwork(artworks);
		artwork.setTitle(title);

		HashSet<UserProfile> artists = new HashSet<>();
		artists.add(artist);
		artwork.setArtist(artists);
		customer.setUsername(username);
		customer.setFirstName(firstName);

		OrderStatus orderStatus = OrderStatus.Delivered;

		Order order = new Order();
		order.setArtwork(artwork);
		order.setCustomer(customer);
		order.setOrderStatus(orderStatus);

		artworkRepository.save(artwork);
		userRepository.save(customer);

//		customer.setCurrentOrder(order);

		orderRepository.save(order);
		int orderId = order.getOrderId();

		order = null;

		order = orderRepository.findOrderByOrderId(orderId);
		assertNotNull(order);
		assertEquals(artwork.getArtworkId(), order.getArtwork().getArtworkId());
		assertTrue(customer.getUsername().equals(order.getCustomer().getUsername()));
		assertEquals(orderId, order.getOrderId());

		customer = null;

		customer = userRepository.findByUsername(username);
		assertNotNull(customer);
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
		String cardNumber = "1234567891234567";
		Date expirationDate = java.sql.Date.valueOf(LocalDate.of(2024, Month.NOVEMBER, 19));
		int cvv = 123;
		//int paymentId = 12345;
		Time paymentTime = java.sql.Time.valueOf(LocalTime.of(11, 35));

		Payment payment = new Payment();
		payment.setPaymentDate(paymentDate);
		payment.setCardNumber(cardNumber);
		payment.setExpirationDate(expirationDate);
		payment.setCvv(cvv);
		//payment.setPaymentId(paymentId);
		payment.setPaymentTime(paymentTime);

		paymentRepository.save(payment);
		int paymentId = payment.getPaymentId();

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
		//int applicationId=1234;

		ArtGalleryApplication artGalleryApp = new ArtGalleryApplication();
		//artGalleryApp.setApplicationId(applicationId);

		artGalleryApplicationRepository.save(artGalleryApp);
		int applicationId = artGalleryApp.getApplicationId();
		artGalleryApp = null;

		artGalleryApp = artGalleryApplicationRepository.findApplicationByApplicationId(applicationId);
		assertNotNull(artGalleryApp);
		assertEquals(artGalleryApp.getApplicationId(), applicationId);
	}

}

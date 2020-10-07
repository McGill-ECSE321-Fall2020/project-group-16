package ca.mcgill.ecse321.artgalleryapplication.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestArtGalleryAppPersistence {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ShipmentRepository shipmentRepository;
	
	@AfterEach
	public void clearDatabase() {
		userRepository.deleteAll();
		shipmentRepository.deleteAll();
		addressRepository.deleteAll();
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
	
}

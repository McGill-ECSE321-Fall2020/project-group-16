package ca.mcgill.ecse321.artgalleryapplication.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestArtGalleryAppPersistence {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	
	@AfterEach
	public void clearDatabase() {
		addressRepository.deleteAll();
		userRepository.deleteAll();
		// Then we can clear the other tables
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
		// First example for object save/load
		Address address = new Address();
		// First example for attribute save/load
		address.setAddressId(addressId);
		address.setStreetAddress(streetAddress);
		address.setStreetAddress2(streetAddress2);
		address.setPostalCode(postalCode);
		address.setCity(city);
		address.setProvince(province);
		address.setCountry(country);
	
		System.out.println(address == null);
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
	public void testPersistAndLoadUserProfile() {
		String username = "tester1";
		String password = "testPass1";
		String firstName = "Fname";
		String lastName = "Lname";
		String description = "test case for user profile";
		String profileImageUrl = "http://test.png";
		Boolean isAdmin = true;
		// First example for object save/load
		UserProfile user = new UserProfile();
		// First example for attribute save/load
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

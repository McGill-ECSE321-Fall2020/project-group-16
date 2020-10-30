package ca.mcgill.ecse321.artgalleryapplication.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import  ca.mcgill.ecse321.artgalleryapplication.model.*;

import javax.swing.*;

@ExtendWith(MockitoExtension.class)
public class TestUserProfileService {

    @Mock
    private UserRepository userDao;

    @Mock
    private ArtworkRepository artworkDao;

    @Mock
    private AddressRepository addressDao;

    @Mock
    private OrderRepository orderDao;

    @InjectMocks
    private UserProfileService userService;

    private static final String USERNAME = "johndoe";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String PASSWORD = "JohnDoe123$";
    private static final String EMAIL = "john.doe.gmail.com";

    @BeforeEach
    public void setMockOutput() {
        // Mock for findByUsername
        lenient().when(userDao.findByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0) == USERNAME) {
                UserProfile user = new UserProfile();
                user.setUsername(USERNAME);
                user.setEmail(EMAIL);
                user.setPassword(PASSWORD);
                user.setFirstName(FIRST_NAME);
                user.setLastName(LAST_NAME);
                return user;
            } else {
                return null;
            }
        });

        // Mock for finaAll
        lenient().when(userDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            UserProfile user = new UserProfile();
            user.setFirstName(FIRST_NAME);
            user.setLastName(LAST_NAME);
            user.setUsername(USERNAME);
            user.setPassword(PASSWORD);
            user.setEmail(EMAIL);

            List<UserProfile> list = new ArrayList<UserProfile>();
            list.add(user);
            return list;
        });

        // Mock for existsByUsername
        lenient().when(userDao.existsByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0) == USERNAME) {
                return true;
            } else {
                return false;
            }
        });

        Answer<?> returnParameterAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        // Mock for save
        lenient().when(userDao.save(any(UserProfile.class))).thenAnswer(returnParameterAnswer);

    }

    @AfterEach
    public void clearDataBase(){
        userDao.deleteAll();
    }

    @Test
    public void createRegularUserTest() {
        assertEquals(0, userService.getAllUsers().size());

        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(false, user.getIsAdmin());
    }

    @Test
    public void createAdminTest() {
        assertEquals(0, userService.getAllUsers().size());
        UserProfile user = null;

        try {
            user = userService.createAdminProfile(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(true, user.getIsAdmin());
    }

    @Test
    public void createEmptyUserTest() {
        String error = "";
        String expected = "The username must be at least 5 characters long.\n" +
                "The email cannot me empty.\n" +
                "The password must be at least 8 characters long.\n" +
                "The first name must contain at least 2 characters.\n" +
                "The last name must contain at least 2 characters.\n" +
                "The first name must only contain letters.\n" +
                "The last name must only contain letters.\n";
        String username = "";
        String firstName = "";
        String lastName = "";
        String email = "";
        String password = "";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals(expected, error);
    }

    @Test
    public void inValidUsernameTest() {
        String error = "";
        String username = "john";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(FIRST_NAME, LAST_NAME, username, EMAIL, PASSWORD);
        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals("The username must be at least 5 characters long.\n", error);

    }

    @Test
    public void invalidEmailTest() {
        String error = "";
        String email = "john.doe.gmail.com";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(FIRST_NAME, LAST_NAME, USERNAME, email, PASSWORD);
        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals("The email entered is not a valid email address.\n", error);
    }

    @Test
    public void invalidPasswordTest() {
        String error = "";
        String password = "JohnDoe123";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, password);
        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals("The password must contain at least one lowercase letter, one uppercase letter, one number and one special character.\n", error);
    }

    @Test
    public void invalidNameTest() {
        String error = "";
        String expected = "The first name must only contain letters.\n" + "The last name must only contain letters.\n";
        String firstName = "123";
        String lastName = "1234";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, USERNAME, EMAIL, PASSWORD);

        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals(expected, error);
    }

    @Test
    public void updateEmailTest() {
        String newEmail = "doe.john@gmail.com";
        UserProfile user = null;

        try {
            user = userService.updateEmail(USERNAME, newEmail);
            System.out.println(user.getEmail());
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newEmail, user.getEmail());
    }

    @Test
    public void updateUsernameTest() {
        String newUsername = "doejohn";
        UserProfile user = null;
//
//        try {
//            user = userService.createRegularUserProfile(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
//        } catch (Exception e) {
//            fail(e);
//        }

        try {
            user = userService.updateUsername(USERNAME, newUsername);
            System.out.println(user.getUsername());
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void updateNameTest() {
        String newFirstName = "Sarah";
        String newLastName = "Smith";
        UserProfile user = null;

        try {
            user = userService.updateName(USERNAME, newFirstName, newLastName);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newFirstName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
    }

    @Test
    public void updatePasswordTest() {
        String newPassword = "JohnDoe321&";
        UserProfile user = null;

        try {
            user = userService.updatePassword(USERNAME, PASSWORD, newPassword);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newPassword, user.getPassword());
    }

    @Test
    public void updateAdminStatusTest() {
        UserProfile user = null;

        try {
            user = userService.updateAdminStatus(USERNAME, true);
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(user.getIsAdmin());
    }

    @Test
    public void updateAddressTest() {
        UserProfile user = null;

        String streetAddress = "McGill";
        String streetAddress2 = "2";
        String postalCode = "H2V6B5";
        String city = "Montreal";
        String province = "Quebec";
        String country = "Canada";

        String newStreetAddress = "Aylmer";
        String newStreetAddress2 = "4";
        String newPostalCode = "H2V6C5";
        String newCity = "Montreal";
        String newProvince = "Quebec";
        String newCountry = "Canada";

        lenient().when(addressDao.save(any(Address.class))).thenAnswer(new Answer() {

            private Address setAddress(String streetAddress, String streetAddress2, String postalCode, String city, String province, String country){
                Address address = new Address();
                address.setAddressId(1);
                address.setStreetAddress(streetAddress);
                address.setStreetAddress2(streetAddress2);
                address.setPostalCode(postalCode);
                address.setCity(city);
                address.setProvince(province);
                address.setCountry(country);
                return address;
            }

            private int count = 0;

            public Object answer(InvocationOnMock invocation) {
                if (count == 0) {
                    count ++;
                    return setAddress(streetAddress, streetAddress2, postalCode, city, province, country);
                } else {
                    return setAddress(newStreetAddress, newStreetAddress2, newPostalCode, newCity, newProvince, newCountry);
                }
            }
        });

        try {
            user = userService.updateAddress(USERNAME, streetAddress, streetAddress2, postalCode, city, province, country);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(streetAddress, user.getAddress().getStreetAddress());

        try {
            user = userService.updateAddress(USERNAME, newStreetAddress, newStreetAddress2, newPostalCode, newCity, newProvince, newCountry);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newStreetAddress, user.getAddress().getStreetAddress());

        addressDao.deleteAll();
    }

    @Test
    public void updateCurrentOrderTest() {
        UserProfile user = null;

        lenient().when(artworkDao.findArtworkByArtworkId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            Artwork artwork = new Artwork();
            artwork.setArtworkId(1);
            return artwork;
        });

        lenient().when(orderDao.save(any(Order.class))).thenAnswer((InvocationOnMock invocation) -> {
            Order order = new Order();
            order.setOrderId(1);
            return order;
        });

        try {
            user = userService.updateCurrentOrder(USERNAME, 1);
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(user.getCurrentOrder().getOrderId() == 1);

        artworkDao.deleteAll();
        orderDao.deleteAll();
    }

    @Test
    public void removeCurrentOrderTest() {

        UserProfile user = null;

        lenient().when(artworkDao.findArtworkByArtworkId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            Artwork artwork = new Artwork();
            artwork.setArtworkId(1);
            return artwork;
        });

        lenient().when(orderDao.save(any(Order.class))).thenAnswer((InvocationOnMock invocation) -> {
            Order order = new Order();
            order.setOrderId(1);
            return order;
        });

        try {
            user = userService.updateCurrentOrder(USERNAME, 1);
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(user.getCurrentOrder().getOrderId() == 1);

        try {
            user = userService.removeCurrentOrder(USERNAME);
        } catch (Exception e) {
            fail(e);
        }

        assertNull(user.getCurrentOrder());

        artworkDao.deleteAll();
        orderDao.deleteAll();
    }

    @Test
    public void updateDescriptionTest() {
        String description = "Test description";
        UserProfile user = null;

        try {
            user = userService.updateDescription(USERNAME, description);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(description, user.getDescription());

    }

    @Test
    public void updateProfileImageUrlTest() {
        String url = "test/url";
        UserProfile user = null;

        try {
            user = userService.updateProfileImageUrl(USERNAME, url);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(url, user.getProfileImageUrl());

    // TODO Test deletion


    }
}

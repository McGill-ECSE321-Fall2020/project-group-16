package ca.mcgill.ecse321.artgalleryapplication.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.apache.catalina.User;
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
public class TestUserProfileService {

    @Mock
    private UserRepository userDao;

    @InjectMocks
    private UserProfileService userService;

    @InjectMocks
    private OrderService orderService;

    @InjectMocks
    private ArtworkService artworkService;

    private static final String USER_KEY = "johndoe";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(userDao.findByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String args = invocation.getArgument(0);
            UserProfile user = new UserProfile();
            user.setUsername(args);
            return user;
//            if (invocation.getArgument(0).equals(USER_KEY)) {
//                UserProfile user = new UserProfile();
//                user.setUsername(USER_KEY);
//                return user;
//            } else {
//                return null;
//            }
        });

        Answer<?> returnParameterAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(userDao.save(any(UserProfile.class))).thenAnswer(returnParameterAnswer);

    }

    @Test
    public void createRegularUserTest() {
        assertEquals(0, userService.getAllUsers().size());

        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(false, user.getIsAdmin());
    }

    @Test
    public void createAdminTest() {
        assertEquals(0, userService.getAllUsers().size());

        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        UserProfile user = null;

        try {
            user = userService.createAdminProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
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
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals("The username must be at least 5 characters long.\n", error);

    }

    @Test
    public void invalidEmailTest() {
        String error = "";
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe.gmail.com";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals("The email entered is not a valid email address.\n", error);
    }

    @Test
    public void invalidPasswordTest() {
        String error = "";
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123";
        String email = "john.doe@gmail.com";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals("The password must contain at least one lowercase letter, one uppercase letter, one number and one special character.\n", error);
    }

    @Test
    public void invalidNameTest() {
        String error = "";
        String expected = "The first name must only contain letters.\n" + "The last name must only contain letters.\n";
        String username = "johndoe";
        String firstName = "123";
        String lastName = "1234";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);

        } catch (Exception e) {
            error += e.getMessage();
        }

        assertEquals(expected, error);
    }

    @Test
    public void updateEmailTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        String newEmail = "doe.john@gmail.com";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }


        try {
            user = userService.updateEmail(username, newEmail);
            System.out.println(user.getEmail());
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newEmail, user.getEmail());
    }

    @Test
    public void updateUsernameTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        String newUsername = "doejohn";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updateUsername(username, newUsername);
            System.out.println(user.getUsername());
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void updateNameTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        String newFirstName = "Sarah";
        String newLastName = "Smith";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updateName(username, newFirstName, newLastName);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newFirstName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
    }

    @Test
    public void updatePasswordTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        String newPassword = "JohnDoe321&";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updatePassword(username, password, newPassword);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newPassword, user.getPassword());
    }

    @Test
    public void updateAdminStatusTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updateAdminStatus(username, true);
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(user.getIsAdmin());
    }

    @Test
    public void updateAddressTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
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


        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updateAddress(username, streetAddress, streetAddress2, postalCode, city, province, country);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(streetAddress, user.getAddress().getStreetAddress());

        try {
            user = userService.updateAddress(username, newStreetAddress, newStreetAddress2, newPostalCode, newCity, newProvince, newCountry);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(newStreetAddress, user.getAddress().getStreetAddress());
    }

    @Test
    public void updateCurrentOrderTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";


        UserProfile user = null;
        Artwork artwork = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            artwork = artworkService.createArtwork(1, "Test", "Test description", new java.sql.Date(2020, 01, 01), "paint", "image", 10.00, ArtworkStatus.ForSale, "10x10", "Test collection");
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updateCurrentOrder(username, artwork.getArtworkId());
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(user.getCurrentOrder().getArtwork().getArtworkId() == 1);
    }

    @Test
    public void removeCurrentOrderTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";


        UserProfile user = null;
        Artwork artwork = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            artwork = artworkService.createArtwork(1, "Test", "Test description", new java.sql.Date(2020, 01, 01), "paint", "image", 10.00, ArtworkStatus.ForSale, "10x10", "Test collection");
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updateCurrentOrder(username, artwork.getArtworkId());
        } catch (Exception e) {
            fail(e);
        }

        assertTrue(user.getCurrentOrder().getArtwork().getArtworkId() == 1);

        try {
            user = userService.removeCurrentOrder(username);
        } catch (Exception e) {
            fail(e);
        }

        assertNull(user.getCurrentOrder());
    }

    @Test
    public void updateDescriptionTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        String description = "Test description";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updateDescription(username, description);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(description, user.getDescription());

    }

    @Test
    public void updateProfileImageUrlTest() {
        String username = "johndoe";
        String firstName = "John";
        String lastName = "Doe";
        String password = "JohnDoe123$";
        String email = "john.doe@gmail.com";
        String url = "test/url";
        UserProfile user = null;

        try {
            user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        } catch (Exception e) {
            fail(e);
        }

        try {
            user = userService.updateProfileImageUrl(username, url);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(url, user.getProfileImageUrl());

    // TODO Test deletion


    }
}

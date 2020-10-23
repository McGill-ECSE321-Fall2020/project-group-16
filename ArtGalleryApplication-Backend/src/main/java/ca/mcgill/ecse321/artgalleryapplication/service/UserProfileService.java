package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import com.sun.tools.javac.comp.Todo;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.*;

@Service
public class UserProfileService {

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



    //create the Transactional methods
    @Transactional
    public UserProfile createUserProfile(String firstName, String lastName, String username, String email, String password, boolean isAdmin) throws IllegalArgumentException {
        String error = "";

        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();
        email = email.toLowerCase();

        //Validate inputs
        if (username == null || username.trim().length() < 5) {
            error += "The username must be at least 5 characters long.\n";
        }

        error += validateEmail(email);

        error += validatePassword(password);

        String nameError = validateName(firstName, lastName);
        error += nameError;

        if (nameError.length() == 0) {
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        }

        //Check if user has been taken already
        if (userRepository.userProfileExistsByEmail(email)) {
            error += "This email has already been taken.\n";
        }
        if (userRepository.userProfileExistsByUsername(username)) {
            error += "This username has already been taken.\n";
        }

        if (error.length() > 0){
            throw new IllegalArgumentException(error);
        }

        //Creat new user
        UserProfile user = new UserProfile();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setIsAdmin(isAdmin);

        userRepository.save(user);

        return user;
    }

    @Transactional
    public UserProfile createAdminProfile(String firstName, String lastName, String username, String email, String password) {
        return createUserProfile(firstName, lastName, username, email, password, true);
    }

    @Transactional
    public UserProfile createRegularUserProfile(String firstName, String lastName, String username, String email, String password) {
        return createUserProfile(firstName, lastName, username, email, password, false);
    }

    //TODO update email

    //TODO update username

    //TODO update first name
    //TODO update last name

    //TODO update password

    //TODO update admin status






    //helper methods

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    private static String validateEmail(String email) {

        if (email == null || email.trim().length() == 0) {
            return "The email cannot me empty.\n";}

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            return "The email entered is not a valid email address.\n";
        }

        return "";

    }

    private static String validatePassword(String password){

        if (password == null || password.trim().length() < 8) {
            return "The password must be at least 8 characters long.\n";
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+])(?=.*\\d).*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            return "The password must contain at least one lowercase letter, one uppercase letter, one number and one special character.\n";
        }

        return "";
    }

    private static String validateName(String firstName, String lastName){
        String error = "";

        String regex = "[a-z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher firstNameMatcher = pattern.matcher(firstName);
        Matcher lastNameMatcher = pattern.matcher(lastName);

        if (firstName == null || firstName.trim().length() < 2) {
            error += "The first name must contain at least 2 characters.\n";
        }
        if (lastName == null || lastName.trim().length() < 2) {
            error += "The last name must contain at least 2 characters.\n";
        }

        if (error.length() > 0){
            return error;
        }

        if (!firstNameMatcher.matches()) {
            error += "The first name must only contain letters.\n";
        }

        if (!lastNameMatcher.matches()) {
            error += "The last name must only contain letters.\n";
        }

        return error;

    }

    public static void main(String[] args) {
//        ArrayList<String> passwords = new ArrayList<String>();
//        passwords.add("Redhead123$");
//        passwords.add("redhead123");
//        passwords.add("readhead123%");
//        passwords.add("Redhead123");
//        passwords.add("");
//        passwords.add("REDHEAD123$");
//        passwords.add("1234556");
//        passwords.add("!@#$%^&*");
//        passwords.add("Jinjaboy18&");
//
//        for (String password : passwords){
//            System.out.println(validatePassword(password));
//        }

//        ArrayList<String> names = new ArrayList<String>();
//        names.add("evan");
//        names.add("evan1");
//        names.add("123");
//
//        for (String name : names){
//            System.out.println(validateName(name));
//        }
    }
}

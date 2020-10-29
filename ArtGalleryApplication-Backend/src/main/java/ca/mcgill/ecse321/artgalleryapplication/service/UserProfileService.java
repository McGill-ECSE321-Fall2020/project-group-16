package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import net.bytebuddy.pool.TypePool;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.orm.ObjectRetrievalFailureException;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.Set;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    private AddressService addressService;
    private OrderService orderService;


    //create the Transactional methods

    // ----- Creation methods -----
    @Transactional
    public UserProfile createUserProfile(String firstName, String lastName, String username, String email, String password, boolean isAdmin) throws IllegalArgumentException {
        String error = "";


        //Validate inputs
        if (username == null || username.trim().length() < 5) {
            error += "The username must be at least 5 characters long.\n";
        }

        email = email.toLowerCase();

        try {
            validateEmail(email);
        } catch (IllegalArgumentException e) {
            error += e.getMessage();
        }

        try {
            validatePassword(password);
        } catch (IllegalArgumentException e) {
            error += e.getMessage();
        }

        try {
            String[] name = formatName(firstName, lastName);
            firstName = name[0];
            lastName = name[1];
        } catch (IllegalArgumentException e) {
            error += e.getMessage();
        }

        //Check if user has been taken already
        if (userRepository.existsByEmail(email)) {
            error += "This email has already been taken.\n";
        }
        if (userRepository.existsByUsername(username)) {
            error += "This username has already been taken.\n";
        }

        if (error.length() > 0){
            throw new IllegalArgumentException(error);
        }

        //Create new user
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
    public UserProfile createAdminProfile(String firstName, String lastName, String username, String email, String password) throws IllegalArgumentException{
        return createUserProfile(firstName, lastName, username, email, password, true);
    }

    @Transactional
    public UserProfile createRegularUserProfile(String firstName, String lastName, String username, String email, String password) throws IllegalArgumentException{
        return createUserProfile(firstName, lastName, username, email, password, false);
    }

    // ----- Update methods -----
    @Transactional
    public UserProfile updateEmail(String username, String password, String newEmail) throws IllegalArgumentException, DataAccessException {
        newEmail = newEmail.toLowerCase();

        UserProfile user = getUserProfileByUsername(username, password);

        String error = "";

        try {
            validateEmail(newEmail);
        } catch (IllegalArgumentException e) {
            error += e.getMessage();
        }

        if (error.length()>0) {
            throw new IllegalArgumentException(error);
        } else {
                user.setEmail(newEmail);
                userRepository.save(user);
        }

        return user;

    }

    @Transactional
    public UserProfile updateUsername(String username, String password, String newUsername) throws IllegalArgumentException, DataAccessException {

        UserProfile user = getUserProfileByUsername(username, password);

        if (newUsername == null || newUsername.trim().length() < 5) {
            throw new IllegalArgumentException("The new username must be at least 5 characters long.");
        } else {
                user.setEmail(newUsername);
                userRepository.save(user);
        }

        return user;

    }

    @Transactional
    public UserProfile updateName(String username, String password, String newFirstName, String newLastName) throws IllegalArgumentException, DataAccessException{
        UserProfile user = getUserProfileByUsername(username, password);
            String[] newName = formatName(newFirstName, newLastName);
            newFirstName = newName[0];
            newLastName = newName[1];

            user.setFirstName(newFirstName);
            user.setLastName(newLastName);
            userRepository.save(user);

        return user;

    }

    @Transactional
    public UserProfile updatePassword(String username, String password, String newPassword) throws DataAccessException, IllegalArgumentException{
        UserProfile user = getUserProfileByUsername(username, password);

        validatePassword(newPassword);

            user.setPassword(newPassword);
            userRepository.save(user);

        return user;
    }

    @Transactional
    public UserProfile updateAdminStatus(String username, String password, boolean isAdmin) throws DataAccessException{
        UserProfile user = getUserProfileByUsername(username, password);

            user.setIsAdmin(isAdmin);
            userRepository.save(user);

        return user;
    }

    @Transactional
    public UserProfile updateAddress(String username, String password, String streetAddress, String streetAddress2, String postalCode, String city, String province, String country) throws DataAccessException {
        UserProfile user = getUserProfileByUsername(username, password);
        Address address = addressService.createAddress(streetAddress, streetAddress2, postalCode, city, province, country);
        user.setAddress(address);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public UserProfile updateCurrentOrder(String username, int artworkId) throws DataAccessException {
        UserProfile user = getUserProfileByUsername(username);
        Order order = orderService.placeOrder(artworkId, username);
        user.setCurrentOrder(order);
        userRepository.save(user);

        return user;

    }

    @Transactional
    public UserProfile removeCurrentOrder(String username) throws DataAccessException {
        UserProfile user = getUserProfileByUsername(username);
        user.setCurrentOrder(null);
        userRepository.save(user);
        return user;
    }

    // ----- Deletion methods -----
    @Transactional
    public void deleteUserProfile(String username) throws DataAccessException {
        if(username == null || username.trim().length() == 0) throw new IllegalArgumentException("requested username is null or length 0. Please enter valid username.");
        UserProfile user = getUserProfileByUsername(username);
        if(user == null) throw new IllegalArgumentException("requested user " + username + " does not exist in the system.");
        //if(user.getGalleryEvent().size() != 0) throw new IllegalArgumentException("Cannot delete this user, because it is register to a galleryEvent!");

        for(GalleryEvent g : user.getGalleryEvent()) {
            eventService.unregisterUserToEvent(user, g);
        }
        userRepository.deleteUserProfileByUsername(username);
    }

    // ----- Get methods -----
    @Transactional
    public UserProfile getUserProfileByUsername(String username, String password) throws DataAccessException{
        UserProfile user = null;

        try {
            user = userRepository.findByUsername(username);
        } catch (DataAccessException e) {
            throw new ObjectRetrievalFailureException("There was an error when retrieving the user.\n",e);
        }

        if (user == null) {
            throw new ObjectRetrievalFailureException(UserProfile.class, username);
        }

        if (!password.equals(user.getPassword())) {
            throw new PermissionDeniedDataAccessException("The entered password does not match the password of the user profile.\n", new IllegalAccessError());
        }

        return user;

    }

    @Transactional
    public UserProfile getUserProfileByUsername(String username) throws DataAccessException{
        UserProfile user = null;

        try {
            user = userRepository.findByUsername(username);
        } catch (DataAccessException e) {
            throw new ObjectRetrievalFailureException("There was an error when retrieving the user.\n",e);
        }

        if (user == null) {
            throw new ObjectRetrievalFailureException(UserProfile.class, username);
        }

        return user;

    }

    @Transactional
    public UserProfile getUserProfileByEmail(String email, String password) throws DataAccessException{
        UserProfile user = null;

        try {
            user = userRepository.findByEmail(email);
        } catch (DataAccessException e) {
            throw new ObjectRetrievalFailureException("There was an error when retrieving the user.\n",e);
        }

        if (user == null) {
            throw new ObjectRetrievalFailureException(UserProfile.class, email);
        }

        if (!password.equals(user.getPassword())) {
            throw new PermissionDeniedDataAccessException("The entered password does not match the password of the user profile.\n", new IllegalAccessError());
        }

        return user;

    }

    @Transactional
    public UserProfile getUserProfileByEmail(String email) throws DataAccessException{
        UserProfile user = null;

        try {
            user = userRepository.findByEmail(email);
        } catch (DataAccessException e) {
            throw new ObjectRetrievalFailureException("There was an error when retrieving the user.\n",e);
        }

        if (user == null) {
            throw new ObjectRetrievalFailureException(UserProfile.class, email);
        }

        return user;

    }



    @Transactional
    public List<UserProfile> getAllUsers() {
        return toList(userRepository.findAll());
    }




    // ----- Helper methods -----

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    private static boolean validateEmail(String email) throws IllegalArgumentException{

        if (isEmpty(email)) {
            throw new IllegalArgumentException("The email cannot me empty.\n");
        }

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("The email entered is not a valid email address.\n");
        }

        return true;

    }

    private static boolean validatePassword(String password) throws IllegalArgumentException{

        if (password == null || password.trim().length() < 8) {
            throw new IllegalArgumentException("The password must be at least 8 characters long.\n");
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+])(?=.*\\d).*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("The password must contain at least one lowercase letter, one uppercase letter, one number and one special character.\n");
        }

        return true;
    }

    private static boolean validateName(String firstName, String lastName) throws IllegalArgumentException{
        String error = "";

        String regex = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher firstNameMatcher = pattern.matcher(firstName);
        Matcher lastNameMatcher = pattern.matcher(lastName);

        if (firstName == null || firstName.trim().length() < 2) {
            error += "The first name must contain at least 2 characters.\n";
        }
        if (lastName == null || lastName.trim().length() < 2) {
            error += "The last name must contain at least 2 characters.\n";
        }


        if (!firstNameMatcher.matches()) {
            error += "The first name must only contain letters.\n";
        }

        if (!lastNameMatcher.matches()) {
            error += "The last name must only contain letters.\n";
        }

        if (error.length() > 0){
            throw new IllegalArgumentException(error);
        }
        return true;

    }

    private static String[] formatName(String firstName, String lastName) throws IllegalArgumentException{

        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();
        String nameError = "";

        try {
            validateName(firstName, lastName);
        } catch (IllegalArgumentException e) {
            nameError += e.getMessage();
        }

        if (nameError.length() == 0) {
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        } else {
            throw new IllegalArgumentException(nameError);
        }

        return new String[]{firstName, lastName};
    }

    private static boolean isEmpty(String s) {
        if(s == null || s.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}

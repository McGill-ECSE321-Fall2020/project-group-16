package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.OrderDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.UserProfileDto;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import ca.mcgill.ecse321.artgalleryapplication.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.stream.Collectors;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;


@CrossOrigin(origins = "*")
@RestController
public class UserProfileController {

    @Autowired
    private UserProfileService userService;
    private OrderService orderService;

    //Mappings + mappings methods

    // Post methods
    @PostMapping(value = {"/users/{username}", "/users/{username}/"})
    public UserProfileDto createRegularUserProfile(@PathVariable("username") String username,
                                           @RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String email,
                                           @RequestParam String password) throws IllegalArgumentException{
        UserProfile user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        return convertToDto(user);
    }

    @PostMapping(value = {"/users/admin/{username}", "/users/admin/{username}/"})
    public UserProfileDto createAdminProfile(@PathVariable("username") String username,
                                                   @RequestParam String firstName,
                                                   @RequestParam String lastName,
                                                   @RequestParam String email,
                                                   @RequestParam String password) throws IllegalArgumentException{
        UserProfile user = userService.createAdminProfile(firstName, lastName, username, email, password);
        return convertToDto(user);
    }

    // Get methods
    @GetMapping(value = {"/users", "/users/"})
    public List<UserProfileDto> getAllUsers() {
        return userService.getAllUsers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/users/{username}", "/users/{username}/"})
    public UserProfileDto getUserProfileByUsernameAndPassword(@PathVariable("username") String username, @RequestParam String password) throws DataAccessException {
        return convertToDto(userService.getUserProfileByUsername(username, password));
    }

    @GetMapping(value = {"/users/{email}", "/users/{email}/"})
    public UserProfileDto getUserProfileByEmailAndPassword(@PathVariable("email") String email, @RequestParam String password) throws DataAccessException {
        return convertToDto(userService.getUserProfileByEmail(email, password));
    }

    @GetMapping(value = {"/users/{username}/get-current-order", "users/{username}/get-current-order/"})
    public OrderDto getCurrentOrder(@PathVariable("username") String username) throws DataAccessException {
        UserProfile user = userService.getUserProfileByUsername(username);
        int orderId = user.getCurrentOrder().getOrderId();
        return convertToDto(orderService.getOrderById(orderId));
    }

    @GetMapping(value = {"/users/{username}/get-all-orders", "users/{username}/get-all-orders/"})
    public List<OrderDto> getAllOrders(@PathVariable("username") String username) throws DataAccessException {
        return orderService.getOrdersByUser(username).stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }


    // Update methods
    @PutMapping(value = {"/users/{username}/update-email", "/users/{username}/update-email/"})
    public UserProfileDto updateEmail(@PathVariable("username") String username,
                                      @RequestParam String password,
                                      @RequestParam String newEmail) throws DataAccessException {
        return convertToDto(userService.updateEmail(username, password, newEmail));
    }

    @PutMapping(value = {"/users/{username}/update-username", "/users/{username}/update-username/"})
    public UserProfileDto updateUsername(@PathVariable("username") String username,
                                      @RequestParam String password,
                                      @RequestParam String newUsername) throws DataAccessException {
        return convertToDto(userService.updateUsername(username, password, newUsername));
    }

    @PutMapping(value = {"users/{username}/update-address", "users/{username}/update-address/"})
    public UserProfileDto updateAddress(@PathVariable("username") String username,
                                        @RequestParam String password,
                                        @RequestParam String streetAddress,
                                        @RequestParam String streetAddress2,
                                        @RequestParam String postalCode,
                                        @RequestParam String city,
                                        @RequestParam String province,
                                        @RequestParam String country) throws DataAccessException {
        return convertToDto(userService.updateAddress(username, password, streetAddress, streetAddress2, postalCode, city, province, country));
    }

    @PutMapping(value = {"users/{username}/update-password", "users/{username}/update-password/"})
    public UserProfileDto updatePassword(@PathVariable("username") String username,
                                         @RequestParam String password,
                                         @RequestParam String newPassword) throws DataAccessException {
        return convertToDto(userService.updatePassword(username, password, newPassword));
    }

    @PutMapping(value = {"users/{username}/update-admin-status", "users/{username}/update-admin-status/"})
    public UserProfileDto updateAdminStatus(@PathVariable("username") String username,
                                            @RequestParam String password,
                                            @RequestParam boolean isAdmin) throws DataAccessException {
        return convertToDto(userService.updateAdminStatus(username, password, isAdmin));
    }

    @PutMapping(value = {"users/{username}/update-name", "users/{username}/update-name/"})
    public UserProfileDto updateName(@PathVariable("username") String username,
                                     @RequestParam String password,
                                     @RequestParam String newFirstName,
                                     @RequestParam String newLastName) throws DataAccessException {
        return convertToDto(userService.updateName(username, password, newFirstName, newLastName));
    }

    @PutMapping(value = {"users/{username}/update-current-order", "users/{username}/update-current-order/"})
    public UserProfileDto updateCurrentOrder(@PathVariable("username") String username,
                                             @RequestParam int artworkId) throws DataAccessException {
        return convertToDto(userService.updateCurrentOrder(username, artworkId));
    }

    @PutMapping(value = {"users/{username}/remove-current-order", "users/{username}/remove-current-order/"})
    public UserProfileDto removeCurrentOrder(@PathVariable("username") String username) throws DataAccessException {
        return convertToDto((userService.removeCurrentOrder(username)));
    }

    // ----- Deletion methods -----
    @DeleteMapping(value = {"users/{username}/delete", "users/{username}/delete/"})
    public UserProfileDto deleteUser(@PathVariable("username") String username) throws DataAccessException {
        return convertToDto(userService.deleteUserProfile(username));
    }




}

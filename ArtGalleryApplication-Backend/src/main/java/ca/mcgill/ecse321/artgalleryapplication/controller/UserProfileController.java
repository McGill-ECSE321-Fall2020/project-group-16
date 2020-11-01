package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.OrderDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.UserProfileDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Artwork;
import ca.mcgill.ecse321.artgalleryapplication.model.GalleryEvent;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private ArtworkService artworkService;

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

//    @GetMapping(value = {"/users/{username}", "/users/{username}/"})
//    public UserProfileDto getUserProfileByUsernameAndPassword(@PathVariable("username") String username, @RequestParam String password) throws DataAccessException {
//        return convertToDto(userService.getUserProfileByUsername(username, password));
//    }

    @GetMapping(value = {"/users/{username}", "/users/{username}/"})
    public UserProfileDto getUserProfileByUsername(@PathVariable("username") String username) throws DataAccessException {
        return convertToDto(userService.getUserProfileByUsername(username));
    }

//    @GetMapping(value = {"/users/{email}", "/users/{email}/"})
//    public UserProfileDto getUserProfileByEmailAndPassword(@PathVariable("email") String email, @RequestParam String password) throws DataAccessException {
//        return convertToDto(userService.getUserProfileByEmail(email, password));
//    }


    // Update methods
    @PutMapping(value = {"/users/{username}/update-email", "/users/{username}/update-email/"})
    public UserProfileDto updateEmail(@PathVariable("username") String username,
                                      @RequestParam String newEmail) throws DataAccessException {
        return convertToDto(userService.updateEmail(username, newEmail));
    }

    @PutMapping(value = {"users/{username}/update-address", "users/{username}/update-address/"})
    public UserProfileDto updateAddress(@PathVariable("username") String username,
                                        @RequestParam int addressId) throws DataAccessException {
        return convertToDto(userService.updateAddress(username, addressId));
    }

    @PutMapping(value = {"users/{username}/update-password", "users/{username}/update-password/"})
    public UserProfileDto updatePassword(@PathVariable("username") String username,
                                         @RequestParam String password,
                                         @RequestParam String newPassword) throws DataAccessException {
        return convertToDto(userService.updatePassword(username, password, newPassword));
    }

    @PutMapping(value = {"users/{username}/update-admin-status", "users/{username}/update-admin-status/"})
    public UserProfileDto updateAdminStatus(@PathVariable("username") String username,
                                            @RequestParam boolean isAdmin) throws DataAccessException {
        return convertToDto(userService.updateAdminStatus(username, isAdmin));
    }

    @PutMapping(value = {"users/{username}/update-name", "users/{username}/update-name/"})
    public UserProfileDto updateName(@PathVariable("username") String username,
                                     @RequestParam String newFirstName,
                                     @RequestParam String newLastName) throws DataAccessException {
        return convertToDto(userService.updateName(username, newFirstName, newLastName));
    }

    @PutMapping(value = {"users/{username}/update-description", "users/{username}/update-description/"})
    public UserProfileDto updateDescription(@PathVariable("username") String username, @RequestParam String description) throws DataAccessException {
        return convertToDto(userService.updateDescription(username, description));
    }

    @PutMapping(value = {"users/{username}/update-profile-image-url", "users/{username}/update-profile-image-url/"})
    public UserProfileDto updateProfileImageUrl(@PathVariable("username") String username, @RequestParam String imageUrl) throws DataAccessException {
        return convertToDto(userService.updateDescription(username, imageUrl));
    }

    // ----- Deletion methods -----
    @DeleteMapping(value = {"users/{username}", "users/{username}/"})
    public void deleteUser(@PathVariable("username") String username) throws DataAccessException {
        userService.deleteUserProfile(username);
    }
}

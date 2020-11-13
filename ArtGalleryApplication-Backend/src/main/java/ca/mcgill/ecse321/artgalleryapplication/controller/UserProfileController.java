package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.OrderDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.UserProfileDto;
import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
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

    //Mappings + mappings methods

    // Post methods

    /**
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/users/{username}", "/users/{username}/"})
    public UserProfileDto createRegularUserProfile(@PathVariable("username") String username,
                                           @RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String email,
                                           @RequestParam String password){
        UserProfile user = userService.createRegularUserProfile(firstName, lastName, username, email, password);
        return convertToDto(user);
    }

    /**
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/users/admin/{username}", "/users/admin/{username}/"})
    public UserProfileDto createAdminProfile(@PathVariable("username") String username,
                                                   @RequestParam String firstName,
                                                   @RequestParam String lastName,
                                                   @RequestParam String email,
                                                   @RequestParam String password){
        UserProfile user = userService.createAdminProfile(firstName, lastName, username, email, password);
        return convertToDto(user);
    }

    /**
     * @return
     */
    // Get methods
    @GetMapping(value = {"/users", "/users/"})
    public List<UserProfileDto> getAllUsers() {
        return userService.getAllUsers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    /**
     * @param username
     * @return
     * @throws DataAccessException
     */
    @GetMapping(value = {"/users/{username}", "/users/{username}/"})
    public UserProfileDto getUserProfileByUsername(@PathVariable("username") String username) throws DataAccessException {
        return convertToDto(userService.getUserProfileByUsername(username));
    }

    @GetMapping(value = {"/login/{username}/{password}", "/users/{username}/{password}/"})
    public boolean getLoginStatus(@PathVariable("username") String username, @PathVariable("password") String password) throws DataAccessException {
        try{
            userService.getUserProfileByUsername(username, password);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @param username
     * @param newEmail
     * @return
     * @throws DataAccessException
     */
    // Update methods
    @PutMapping(value = {"/users/{username}/update-email", "/users/{username}/update-email/"})
    public UserProfileDto updateEmail(@PathVariable("username") String username,
                                      @RequestParam String newEmail) throws DataAccessException {
        return convertToDto(userService.updateEmail(username, newEmail));
    }

    /**
     * @param username
     * @param addressId
     * @return
     * @throws DataAccessException
     */
    @PutMapping(value = {"users/{username}/update-address", "users/{username}/update-address/"})
    public UserProfileDto updateAddress(@PathVariable("username") String username,
                                        @RequestParam int addressId) throws DataAccessException {
        return convertToDto(userService.updateAddress(username, addressId));
    }

    /**
     * @param username
     * @param password
     * @param newPassword
     * @return
     * @throws DataAccessException
     */
    @PutMapping(value = {"users/{username}/update-password", "users/{username}/update-password/"})
    public UserProfileDto updatePassword(@PathVariable("username") String username,
                                         @RequestParam String password,
                                         @RequestParam String newPassword) throws DataAccessException {
        return convertToDto(userService.updatePassword(username, password, newPassword));
    }

    /**
     * @param username
     * @param isAdmin
     * @return
     * @throws DataAccessException
     */
    @PutMapping(value = {"users/{username}/update-admin-status", "users/{username}/update-admin-status/"})
    public UserProfileDto updateAdminStatus(@PathVariable("username") String username,
                                            @RequestParam boolean isAdmin) throws DataAccessException {
        return convertToDto(userService.updateAdminStatus(username, isAdmin));
    }

    /**
     * @param username
     * @param newFirstName
     * @param newLastName
     * @return
     * @throws DataAccessException
     */
    @PutMapping(value = {"users/{username}/update-name", "users/{username}/update-name/"})
    public UserProfileDto updateName(@PathVariable("username") String username,
                                     @RequestParam String newFirstName,
                                     @RequestParam String newLastName) throws DataAccessException {
        return convertToDto(userService.updateName(username, newFirstName, newLastName));
    }

    /**
     * @param username
     * @param description
     * @return
     * @throws DataAccessException
     */
    @PutMapping(value = {"users/{username}/update-description", "users/{username}/update-description/"})
    public UserProfileDto updateDescription(@PathVariable("username") String username, @RequestParam String description) throws DataAccessException {
        return convertToDto(userService.updateDescription(username, description));
    }

    /**
     * @param username
     * @param imageUrl
     * @return
     * @throws DataAccessException
     */
    @PutMapping(value = {"users/{username}/update-profile-image-url", "users/{username}/update-profile-image-url/"})
    public UserProfileDto updateProfileImageUrl(@PathVariable("username") String username, @RequestParam String imageUrl) throws DataAccessException {
        return convertToDto(userService.updateDescription(username, imageUrl));
    }

    /**
     * @param username
     * @throws DataAccessException
     */
    // ----- Deletion methods -----
    @DeleteMapping(value = {"users/{username}", "users/{username}/"})
    public void deleteUser(@PathVariable("username") String username) throws DataAccessException {
        userService.deleteUserProfile(username);
    }
}

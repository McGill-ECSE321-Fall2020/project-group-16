package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.UserProfileDto;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import ca.mcgill.ecse321.artgalleryapplication.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;


@CrossOrigin(origins = "*")
@RestController
public class UserProfileController {

    @Autowired
    private UserProfileService service;

    //Mappings + mappings methods
    @GetMapping(value = {"/users", "/users/"})
    public List<UserProfileDto> getAllUsers() {
        return service.getAllUsers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    @PostMapping(value = {"/users/{username}", "/users/{username}/"})
    public UserProfileDto creatUserProfile(@PathVariable("username") String username,
                                           @RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String email,
                                           @RequestParam String password) throws IllegalArgumentException{
        UserProfile user = service.createRegularUserProfile(firstName, lastName, username, email, password);
        return convertToDto(user);
    }

//    @GetMapping(value = {"/users/{username}", "/users/{username}/"})
//    public UserProfileDto getUserProfileByUsername(@PathVariable("username") String username) throws DataAccessException {
//        return convertToDto(service.getUserProfileByUsername(username));
//    }

    @GetMapping(value = {"/users/{username}", "/users/{username}/"})
    public UserProfileDto getUserProfileByUsernameAndPassword(@PathVariable("username") String username, @RequestParam String password) throws DataAccessException {
        return convertToDto(service.getUserProfileByUsername(username, password));
    }



//    //convertToDto methods
//    public UserProfileDto convertToDto(UserProfile user) throws IllegalArgumentException{
//        if (user == null) {
//            throw new IllegalArgumentException("The user cannot be null");
//        }
//
//        return new UserProfileDto(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getIsAdmin());
//
//    }

}

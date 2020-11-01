package ca.mcgill.ecse321.artgalleryapplication.dto;

import java.util.List;
import java.util.Set;

public class UserProfileDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String description;
    private String profileImageUrl;
    private boolean isAdmin;
    private AddressDto address;

    public UserProfileDto(String firstName, String lastName, String username, String email, String password) {
        this(firstName, lastName, username, email, password, false);
    }

    public UserProfileDto(String firstName, String lastName, String username, String email, String password, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public UserProfileDto(String firstName, String lastName, String username, String email, String password, boolean isAdmin, String description, String profileImageUrl , AddressDto address) {
        this(firstName, lastName, username, email, password, isAdmin);
        this.setDescription(description);
        this.setProfileImageUrl(profileImageUrl);
        this.setAddress(address);
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getAdmin() {
        return isAdmin;
    }
}

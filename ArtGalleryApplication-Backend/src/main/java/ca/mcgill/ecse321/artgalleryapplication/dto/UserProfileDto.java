package ca.mcgill.ecse321.artgalleryapplication.dto;

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
    private OrderDto currentOrder;
    private Set<OrderDto> pastOrders;
    private AddressDto address;
    //private Set<EventDto> events;
    private Set<ArtworkDto> artworks;

    public UserProfileDto(String firstName, String lastName, String username, String email, String password){
        this(firstName, lastName, username, email, password, false);
    }

    public UserProfileDto(String firstName, String lastName, String username, String email, String password, boolean isAdmin){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AddressDto getAddress() {
        return address;
    }

    public OrderDto getCurrentOrder() {
        return currentOrder;
    }

    public Set<OrderDto> getPastOrders() {
        return pastOrders;
    }
}
package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.ArtworkDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.OrderDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.UserProfileDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Order;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

public class ConvertToDto {

    public static OrderDto convertToDto(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Order does not exist");

        UserProfileDto userDto = convertToDto(order.getCustomer());
        ArtworkDto artworkDto = convertToDto(order.getArtwork());
//        OrderDto orderDto = new OrderDto(order.getOrderId(), userDto, artworkDto, order.getOrderDate(), order.getOrderTime());
        return new OrderDto(order.getOrderId(), userDto, new ArtworkDto(), order.getOrderDate(), order.getOrderTime());
    }

    //convertToDto methods
    public static UserProfileDto convertToDto(UserProfile user) throws IllegalArgumentException{
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }

        return new UserProfileDto(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getIsAdmin());

    }
}

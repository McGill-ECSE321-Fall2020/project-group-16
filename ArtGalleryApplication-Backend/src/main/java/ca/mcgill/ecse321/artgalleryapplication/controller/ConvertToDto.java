package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import java.util.ArrayList;
import java.util.List;

public class ConvertToDto {

    public static OrderDto convertToDto(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Order does not exist");

        UserProfileDto userDto = convertToDto(order.getCustomer());
        ArtworkDto artworkDto = convertToDto(order.getArtwork());
        return new OrderDto(order.getOrderId(), userDto, artworkDto, order.getOrderDate(), order.getOrderTime());
    }

    public static UserProfileDto convertToDto(UserProfile user) throws IllegalArgumentException{
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }

        return new UserProfileDto(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getIsAdmin());

    }

    public static ArtworkDto convertToDto(Artwork a) throws IllegalArgumentException {
        if(a == null) {
            throw new IllegalArgumentException("Artwork cannot be null");
        }

        List<UserProfileDto> userProfileDtos = new ArrayList<>();
        for(UserProfile u : a.getArtist()) {
            userProfileDtos.add(ConvertToDto.convertToDto(u));
        }

        ArtworkDto artworkDto = new ArtworkDto(a.getArtworkId(), a.getTitle(), a.getDescription(), a.getCreationDate(),
                a.getMedium(), a.getImageUrl(), a.getPrice(), a.getArtworkStatus(),
                a.getDimensions(), a.getCollection(), userProfileDtos);
        return artworkDto;
    }

    public static GalleryEventDto convertToDto(GalleryEvent e) {
        if (e == null) {
            throw new IllegalArgumentException("Event is null");
        }
        List<UserProfileDto> userProfileDtos = new ArrayList<>();
        for(UserProfile u : e.getParticipants()) {
            userProfileDtos.add(ConvertToDto.convertToDto(u));
        }

        GalleryEventDto eventDto = new GalleryEventDto(e.getEventId(), e.getEventName(),e.getEventDescription(), e.getEventImageUrl(), e.getEventDate(), e.getStartTime(),e.getEndTime(), userProfileDtos);
        return eventDto;
    }

    public static AddressDto convertToDto(Address a) {
        if(a == null) throw new IllegalArgumentException("Address is null");
        AddressDto addressDto = new AddressDto(a.getAddressId(), a.getStreetAddress(), a.getStreetAddress2(), a.getPostalCode(), a.getCity(), a.getProvince(), a.getCountry());
        return addressDto;
    }

    private static <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

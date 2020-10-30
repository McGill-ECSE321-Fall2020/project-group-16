package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertToDto {

    public static OrderDto convertToDto(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Order does not exist");

        UserProfileDto userDto = convertToDto(order.getCustomer());
        ArtworkDto artworkDto = convertToDto(order.getArtwork());
        PaymentDto paymentDto = convertToDto(order.getPayment());
        ShipmentDto shipmentDto = convertToDto(order.getShipment());

        return new OrderDto(order.getOrderId(), userDto, artworkDto, order.getTotalAmount(), order.getOrderStatus(),
                order.getOrderDate(), order.getOrderTime(), paymentDto, shipmentDto);
    }

    public static UserProfileDto convertToDto(UserProfile user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }

        return new UserProfileDto(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getIsAdmin(), user.getDescription(), user.getProfileImageUrl(), convertToDto(user.getCurrentOrder()), user.getPastOrders().stream().map(o -> convertToDto(o)).collect(Collectors.toSet()), convertToDto(user.getAddress()), user.getGalleryEvent().stream().map(e -> convertToDto(e)).collect(Collectors.toSet()), user.getArtwork().stream().map(a -> convertToDto(a)).collect(Collectors.toSet()));
    }

    public static ArtworkDto convertToDto(Artwork a) throws IllegalArgumentException {
        if(a == null) {
            throw new IllegalArgumentException("Artwork cannot be null");
        }

        List<UserProfileDto> userProfileDtos = new ArrayList<>();
        for(UserProfile u : a.getArtist()) {
            userProfileDtos.add(ConvertToDto.convertToDto(u));
        }

        return new ArtworkDto(a.getArtworkId(), a.getTitle(), a.getDescription(), a.getCreationDate(),
                a.getMedium(), a.getImageUrl(), a.getPrice(), a.getArtworkStatus(),
                a.getDimensions(), a.getCollection(), userProfileDtos);
    }

    public static GalleryEventDto convertToDto(GalleryEvent e) {
        if (e == null) {
            throw new IllegalArgumentException("Event is null");
        }
        List<UserProfileDto> userProfileDtos = new ArrayList<>();
        for(UserProfile u : e.getParticipants()) {
            userProfileDtos.add(ConvertToDto.convertToDto(u));
        }

        return new GalleryEventDto(e.getEventId(), e.getEventName(),e.getEventDescription(), e.getEventImageUrl(),
                e.getEventDate(), e.getStartTime(),e.getEndTime(), userProfileDtos);
    }

    public static AddressDto convertToDto(Address a) {
        if(a == null) throw new IllegalArgumentException("Address is null");
        return new AddressDto(a.getAddressId(), a.getStreetAddress(), a.getStreetAddress2(), a.getPostalCode(),
                a.getCity(), a.getProvince(), a.getCountry());
    }

    // Can return null for order to function
    public static PaymentDto convertToDto(Payment p) {
    	if(p == null) {
    		return null;
    	}
        return new PaymentDto(p.getPaymentForm(), p.getPaymentDate(), p.getCardNumber(), p.getExpirationDate(), p.getCvv(), p.getPaymentId(), p.getPaymentTime());
    }

    // Can return null for order to function
    public static ShipmentDto convertToDto(Shipment s) {
    	if(s == null) {
            return null;
    	}
        return new ShipmentDto(s.getToGallery(), s.getEstimatedArrivalTime(), s.getShipmentId(), s.getEstimatedArrivalDate(), s.getReturnAddress(), s.getDestination());
    }
    
    private static <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

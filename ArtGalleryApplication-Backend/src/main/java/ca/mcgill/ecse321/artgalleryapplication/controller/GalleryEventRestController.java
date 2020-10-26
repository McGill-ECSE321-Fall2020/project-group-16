package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.AddressDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.GalleryEventDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.UserProfileDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.GalleryEvent;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import ca.mcgill.ecse321.artgalleryapplication.service.ArtGalleryApplicationService;
import ca.mcgill.ecse321.artgalleryapplication.service.*;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class GalleryEventRestController {

    @Autowired
    private ArtGalleryApplicationService applicationService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private EventService eventService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private UserProfileService userProfileService;


    //Mappings + mappings methods

    /**
     * Get all events
     * @return
     */
    @GetMapping(value = { "/events", "/events/" })
    public List<GalleryEventDto> getAllEvents() {
        List<GalleryEventDto> galleryEventDtos = new ArrayList<>();
        for (GalleryEvent g : eventService.getAllEvents()) {
            galleryEventDtos.add(convertEventToDto(g));
        }
        return galleryEventDtos;
    }

    /**
     * Get specific event by ID
     * @param id
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/events/{id}", "/events/{id}/" })
    public GalleryEventDto getEvent(@PathVariable("id") int id) throws IllegalArgumentException {
         GalleryEvent event = eventService.getEventById(id);
         return convertEventToDto(event);
    }

    /**
     * Get participants of event by ID
     * @param id
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/events/{id}/participants", "/events/{id}/participants/" })
    public List<UserProfileDto> getParticipantsOfEvent(@PathVariable("id") int id) throws IllegalArgumentException {
        GalleryEvent event = eventService.getEventById(id);
        List<UserProfileDto> userProfileDtos = new ArrayList<>();
        for(UserProfile u : eventService.getParticipantsOfEvent(event)) {
            userProfileDtos.add(convertUserToDto(u));
        }
        return userProfileDtos;
    }



    /**
     * Create one event
     * @param name
     * @param date
     * @param startTime
     * @param endTime
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/events/{name}", "/events/{name}/" })
    public GalleryEventDto createEvent(
            @PathVariable("name") String name,
            @RequestParam String description,
            @RequestParam String imageUrl,
            @RequestParam Date date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime)
            throws IllegalArgumentException {

        GalleryEvent event = eventService.createEvent(name, description, imageUrl, date, Time.valueOf(startTime), Time.valueOf(endTime));
        return convertEventToDto(event);
    }

    /**
     * Update event and User by adding user to list of participants
     * @param eId
     * @param uName
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/events/register", "/events/register/"})
    public void registerUserToEvent(
            @RequestParam(name = "event") int eId,
            @RequestParam(name = "user") String uName)
            throws IllegalArgumentException {

        //UserProfile p = userService.getUserByName(uName);
        GalleryEvent e = eventService.getEventById(eId);
        //eventService.registerUserToEvent(p, e);
    }

    /**
     * Delete Event by ID
     * @param id
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = { "/events/{id}", "/events/{id}/" })
    public void deleteEvent(@PathVariable("id") Integer id) throws IllegalArgumentException {
        eventService.deleteEvent(id);
    }


    /////// ------------ ADDRESS STUFF ------------ ///////
    /**
     * Get address by ID
     * @param id
     * @return
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/address/{id}", "/address/{id}/" })
    public AddressDto getAddress(@PathVariable("id") int id) throws IllegalArgumentException {
        Address address = addressService.getAddressById(id);
        return convertAddressToDto(address);
    }

    /**
     * Create an address
     * @param streetAddress
     * @param streetAddress2
     * @param postalCode
     * @param city
     * @param province
     * @param country
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/address", "/address/" })
    public AddressDto createAddress(
            @RequestParam String streetAddress,
            @RequestParam String streetAddress2,
            @RequestParam String postalCode,
            @RequestParam String city,
            @RequestParam String province,
            @RequestParam String country)
            throws IllegalArgumentException {

        Address address = addressService.createAddress(streetAddress, streetAddress2, postalCode, city, province, country);
        return convertAddressToDto(address);
    }

    @DeleteMapping(value = { "/address/{id}", "/address/{id}/" })
    public void deleteAddress(@PathVariable("id") int id) throws IllegalArgumentException {
        addressService.deleteAddress(id);
    }


    //convertToDto methods

    /**
     *
     * @param e
     * @return
     */
    private GalleryEventDto convertEventToDto(GalleryEvent e) {
        if (e == null) {
            throw new IllegalArgumentException("Event is null");
        }
        GalleryEventDto eventDto = new GalleryEventDto(e.getEventName(),e.getEventDescription(), e.getEventImageUrl(), e.getEventDate(), e.getStartTime(),e.getEndTime());
        return eventDto;
    }

    /**
     *
     * @param a
     * @return
     */
    private AddressDto convertAddressToDto(Address a) {
        if(a == null) throw new IllegalArgumentException("Address is null");
        AddressDto addressDto = new AddressDto(a.getAddressId(), a.getStreetAddress(), a.getStreetAddress2(), a.getPostalCode(), a.getCity(), a.getProvince(), a.getCountry());
        return addressDto;
    }


    private UserProfileDto convertUserToDto(UserProfile user) throws IllegalArgumentException{
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }

        return new UserProfileDto(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getIsAdmin());

    }

}

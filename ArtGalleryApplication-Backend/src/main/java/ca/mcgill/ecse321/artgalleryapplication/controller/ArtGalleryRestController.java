package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;
import ca.mcgill.ecse321.artgalleryapplication.service.ArtGalleryApplicationService;
import ca.mcgill.ecse321.artgalleryapplication.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*")
@RestController
public class ArtGalleryRestController {

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
    private UserService userService;


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
    public void deleteEvent(@PathVariable("id") int id) throws IllegalArgumentException {
        eventService.deleteEvent(id);
    }



    //convertToDto methods

    /**
     *
     * @param e
     * @return
     */
    private GalleryEventDto convertEventToDto(GalleryEvent e) {
        if (e == null) {
            throw new IllegalArgumentException("There is no such Event!");
        }
        GalleryEventDto eventDto = new GalleryEventDto(e.getEventName(),e.getEventDescription(), e.getEventImageUrl(), e.getEventDate(), e.getStartTime(),e.getEndTime());
        return eventDto;
    }

    private UserProfileDto convertUserToDto(UserProfile user) throws IllegalArgumentException{
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }

        return new UserProfileDto(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getIsAdmin());

    }

}

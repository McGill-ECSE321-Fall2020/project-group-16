package ca.mcgill.ecse321.artgalleryapplication.controller;

import ca.mcgill.ecse321.artgalleryapplication.dto.GalleryEventDto;
import ca.mcgill.ecse321.artgalleryapplication.model.GalleryEvent;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import ca.mcgill.ecse321.artgalleryapplication.service.*;

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
    private EventService eventService;

    @Autowired
    private UserProfileService userProfileService;


    /**
     * Get all events
     * @return
     */
    @GetMapping(value = { "/events", "/events/" })
    public List<GalleryEventDto> getAllEvents() {
        List<GalleryEventDto> galleryEventDtos = new ArrayList<>();
        for (GalleryEvent g : eventService.getAllEvents()) {
            galleryEventDtos.add(ConvertToDto.convertToDto(g));
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
         return ConvertToDto.convertToDto(event);
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
        return ConvertToDto.convertToDto(event);
    }

    /**
     * Update event by adding user to list of participants (User is not updated)
     * @param eId
     * @param uName
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/events/register", "/events/register/"})
    public void registerUserToEvent(
            @RequestParam(name = "eventId") int eId,
            @RequestParam(name = "username") String uName)
            throws IllegalArgumentException {

        GalleryEvent e = eventService.getEventById(eId);
        UserProfile p = userProfileService.getUserProfileByUsername(uName);
        eventService.registerUserToEvent(p, e);
    }

    /**
     * Update event by removing user from list of participants (User is not updated, because it didn't hold info in the first place)
     * @param eId
     * @param uName
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/events/unregister", "/events/unregister/"})
    public void unregisterUserToEvent(
            @RequestParam(name = "eventId") int eId,
            @RequestParam(name = "username") String uName)
            throws IllegalArgumentException {

        GalleryEvent e = eventService.getEventById(eId);
        UserProfile p = userProfileService.getUserProfileByUsername(uName);
        eventService.unregisterUserToEvent(p, e);
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
}

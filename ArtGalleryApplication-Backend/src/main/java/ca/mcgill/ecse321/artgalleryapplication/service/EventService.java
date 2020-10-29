package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author rodolphebaladi
 * Service class for GalleryEvent
 */

@Service
public class EventService {

    @Autowired
    private GalleryEventRepository galleryEventRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     * Service Method to create an event
     * @param name
     * @param description
     * @param imageUrl
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */
    @Transactional
    public GalleryEvent createEvent(String name, String description, String imageUrl,  Date date, Time startTime, Time endTime) {
        if(name == null || name.trim().length() == 0) throw new IllegalArgumentException("Name is currently null or length 0. Please enter a valid name.");
        if(description == null || description.trim().length() == 0) throw new IllegalArgumentException("Description is currently null or length 0. Please enter a valid description.");
        if(date == null) throw new IllegalArgumentException("Date is currently null. Please enter a valid date");
        if(startTime == null) throw new IllegalArgumentException("Start time is currently null. Please enter a valid start time");
        if(endTime == null) throw new IllegalArgumentException("End time is currently null. Please enter a valid end time");
        if(imageUrl == null || imageUrl.trim().length() == 0) throw new IllegalArgumentException("Image is currently null or length 0. Please enter a valid image url string.");

        GalleryEvent event = new GalleryEvent();
        event.setEventName(name);
        event.setEventDescription(description);
        event.setEventDate(date);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setEventImageUrl(imageUrl);
        galleryEventRepository.save(event);
        return event;
    }

    /**
     * Service Method to add a user to event's participants
     * @param user
     * @param event
     */
    @Transactional
    public void registerUserToEvent(UserProfile user, GalleryEvent event) {
        if(event == null) throw new IllegalArgumentException("Event to register to is null");
        if(getEventById(event.getEventId()) == null) throw new IllegalArgumentException("Event does not exist");
        GalleryEvent eventInSystem = galleryEventRepository.findGalleryEventByEventId(event.getEventId());
        if(eventInSystem == null) throw new NullPointerException("Unable to retrieve event in system");
        if(user == null) throw new IllegalArgumentException("User to register to event is null");
        UserProfile userInSystem = userRepository.findByUsername(user.getUsername());
        if(userInSystem == null) throw new IllegalArgumentException("User " + user + " does not exist in system");

        eventInSystem.getParticipants().add(userInSystem);
        galleryEventRepository.save(eventInSystem);
    }

    @Transactional
    public void unregisterUserToEvent(UserProfile user, GalleryEvent event) {
        if(event == null) throw new IllegalArgumentException("Event to register to is null");
        if(getEventById(event.getEventId()) == null) throw new IllegalArgumentException("Event does not exist");
        GalleryEvent eventInSystem = galleryEventRepository.findGalleryEventByEventId(event.getEventId());
        if(eventInSystem == null) throw new NullPointerException("Unable to retrieve event in system");
        if(user == null) throw new IllegalArgumentException("User to register to event is null");
        UserProfile userInSystem = userRepository.findByUsername(user.getUsername());
        if(userInSystem == null) throw new IllegalArgumentException("User " + user + " does not exist in system");

        eventInSystem.getParticipants().remove(userInSystem);
        galleryEventRepository.save(eventInSystem);
    }

    /**
     * Service Method to delete an event
     * @param eventId
     */
    @Transactional
    public void deleteEvent(Integer eventId) {
        if(eventId == null) throw new IllegalArgumentException("Event ID to be deleted is null");
        GalleryEvent event = galleryEventRepository.findGalleryEventByEventId(eventId);
        if(event == null) throw new NullPointerException("There is no Event with this ID");
        galleryEventRepository.deleteGalleryEventByEventId(eventId);
    }

    /**
     * Service Method to return all events
     * @return
     */
    @Transactional
    public List<GalleryEvent> getAllEvents() {
        return toList(galleryEventRepository.findAll());
    }

    /**
     * Service Method to get event by id
     * @param eventId
     * @return
     */
    @Transactional
    public GalleryEvent getEventById(Integer eventId) {
        if(eventId == null) throw new IllegalArgumentException("Event ID to be deleted is null");
        GalleryEvent event = galleryEventRepository.findGalleryEventByEventId(eventId);
        if(event == null) throw new NullPointerException("There is no Event with this ID");
        return event;
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

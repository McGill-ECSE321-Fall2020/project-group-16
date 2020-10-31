package ca.mcgill.ecse321.artgalleryapplication.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import  ca.mcgill.ecse321.artgalleryapplication.model.*;
import org.mockito.stubbing.OngoingStubbing;

@ExtendWith(MockitoExtension.class)
public class TestEventService {

    @Mock
    private UserRepository userRepository;
    @Mock
    private GalleryEventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    // Correct Event info
    private static final String CORRECTEVENTNAME = "TestName";
    private static final String CORRECTEVENTDESCRIPTION = "TestDescription";
    private static final String CORRECTIMAGEURL = "666";
    private static final Date CORRECTDATE = Date.valueOf(LocalDate.now());
    private static final Time CORRECTSTARTTIME = Time.valueOf("12:00:00");
    private static final Time CORRECTENDTIME = Time.valueOf("14:00:00");

    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 3;
    private static final Integer NULL_ID = null;

    // Correct User info
    private static final String VALID_USERNAME = "testUsername";
    private static final String INVALID_USERNAME = "blablabla";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(eventRepository.findGalleryEventByEventId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_ID)) {
                return createEvent();
            } else {
                return null;
            }
        });

        lenient().when(userRepository.findByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(VALID_USERNAME)) {
                return createUser();
            } else {
                return null;
            }
        });

        lenient().when(eventRepository.findAll()).thenReturn(createEventsList());

        // Whenever event is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventRepository.save(any(GalleryEvent.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void createCorrectEvent() {
        GalleryEvent event = null;
        try {
            event = eventService.createEvent(CORRECTEVENTNAME, CORRECTEVENTDESCRIPTION, CORRECTIMAGEURL, CORRECTDATE, CORRECTSTARTTIME, CORRECTENDTIME);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(event);
        assertEquals(CORRECTEVENTNAME, event.getEventName());
        assertEquals(CORRECTEVENTDESCRIPTION, event.getEventDescription());
        assertEquals(CORRECTIMAGEURL, event.getEventImageUrl());
        assertEquals(CORRECTDATE, event.getEventDate());
        assertEquals(CORRECTSTARTTIME, event.getStartTime());
        assertEquals(CORRECTENDTIME, event.getEndTime());
    }

    @Test
    public void testNameNull() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.createEvent(null, CORRECTEVENTDESCRIPTION, CORRECTIMAGEURL, CORRECTDATE, CORRECTSTARTTIME, CORRECTENDTIME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "Name is currently null or length 0. Please enter a valid name.");
    }

    @Test
    public void testNameLength0() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.createEvent("", CORRECTEVENTDESCRIPTION, CORRECTIMAGEURL, CORRECTDATE, CORRECTSTARTTIME, CORRECTENDTIME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "Name is currently null or length 0. Please enter a valid name.");
    }

    @Test
    public void testDescriptionNull() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.createEvent(CORRECTEVENTNAME, null, CORRECTIMAGEURL, CORRECTDATE, CORRECTSTARTTIME, CORRECTENDTIME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "Description is currently null or length 0. Please enter a valid description.");
    }

    @Test
    public void testDescriptionLength0() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.createEvent(CORRECTEVENTNAME, "", CORRECTIMAGEURL, CORRECTDATE, CORRECTSTARTTIME, CORRECTENDTIME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "Description is currently null or length 0. Please enter a valid description.");
    }

    @Test
    public void testDateNull() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.createEvent(CORRECTEVENTNAME, CORRECTEVENTDESCRIPTION, CORRECTIMAGEURL, null, CORRECTSTARTTIME, CORRECTENDTIME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "Date is currently null. Please enter a valid date");
    }

    @Test
    public void testStartTimeNull() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.createEvent(CORRECTEVENTNAME, CORRECTEVENTDESCRIPTION, CORRECTIMAGEURL, CORRECTDATE, null, CORRECTENDTIME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "Start time is currently null. Please enter a valid start time");
    }

    @Test
    public void testEndTimeNull() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.createEvent(CORRECTEVENTNAME, CORRECTEVENTDESCRIPTION, CORRECTIMAGEURL, CORRECTDATE, CORRECTSTARTTIME, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "End time is currently null. Please enter a valid end time");
    }

    @Test
    public void invalidStartAndEndTime() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.createEvent(CORRECTEVENTNAME, CORRECTEVENTDESCRIPTION, CORRECTIMAGEURL, CORRECTDATE, Time.valueOf("12:00:00"), Time.valueOf("10:00:00"));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "Start time is after the End time");
    }

    @Test
    public void testGetCorrectEvent() {
        GalleryEvent event = null;

        try {
            event = eventService.getEventById(VALID_ID);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(event);
        assertEquals(CORRECTEVENTNAME, event.getEventName());
    }

    @Test
    public void testGetIdNull() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.getEventById(NULL_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "Event ID is null");
    }

    @Test
    public void testGetIdInvalid() {
        GalleryEvent event = null;
        String error = "";

        try {
            event = eventService.getEventById(INVALID_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(event);
        assertEquals(error, "There is no Event with this ID");
    }

    @Test
    public void testGetAllEvents() {
        List<GalleryEvent> events = null;

        try {
            events = eventService.getAllEvents();
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(events);
        assertNotEquals(events.size(), 0);
    }

    @Test
    public void testDeleteEvent() {
        try {
             eventService.deleteEvent(VALID_ID);
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testDeleteIdNull() {
        String error = "";

        try {
            eventService.deleteEvent(NULL_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Event ID to be deleted is null");
    }

    @Test
    public void testDeleteIdInvalid() {
        String error = "";

        try {
            eventService.deleteEvent(INVALID_ID);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "There is no Event with this ID");
    }

    @Test
    public void testCorrectRegister() {
        GalleryEvent event = createEvent();
        UserProfile user = createUser();

        assertTrue(event.getParticipants().size() == 0);

        try {
             event = eventService.registerUserToEvent(user, event);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(event);
        assertTrue(event.getParticipants().size() == 1);
    }

    @Test
    public void testCorrectUnregister() {
        GalleryEvent event = createEvent();
        UserProfile user = createUser();

        event.getParticipants().add(user);
        assertTrue(event.getParticipants().size() == 1);

        try {
            event = eventService.unregisterUserToEvent(user, event);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(event);
        assertTrue(event.getParticipants().size() == 0);
    }




    private GalleryEvent createEvent() {
        GalleryEvent event = new GalleryEvent();

        // Setting the Event info
        event.setEventId(VALID_ID);
        event.setEventName(CORRECTEVENTNAME);
        event.setEventDescription(CORRECTEVENTDESCRIPTION);
        event.setEventImageUrl(CORRECTIMAGEURL);
        event.setEventDate(CORRECTDATE);
        event.setStartTime(CORRECTSTARTTIME);
        event.setEndTime(CORRECTENDTIME);

        return event;
    }

    private List<GalleryEvent> createEventsList() {
        List<GalleryEvent> list = new ArrayList<>();
        list.add(createEvent());
        return list;
    }

    private UserProfile createUser() {
        UserProfile user = new UserProfile();

        // Setting the User info
        user.setUsername(VALID_USERNAME);

        return user;
    }
}

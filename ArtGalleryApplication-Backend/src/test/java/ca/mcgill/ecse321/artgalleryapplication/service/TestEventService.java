package ca.mcgill.ecse321.artgalleryapplication.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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

@ExtendWith(MockitoExtension.class)
public class TestEventService {

    @Mock
    private UserRepository userDao;
    @Mock
    private GalleryEventRepository eventDao;

    @InjectMocks
    private EventService service;

    private static final String PERSON_KEY = "TestPerson";
    private static final String NONEXISTING_KEY = "NotAPerson";

    @BeforeEach
    public void setMockOutput() {
//        // Whenever anything is saved, just return the parameter object
//        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
//            return invocation.getArgument(0);
//        };
//        lenient().when(userDao.save(any(UserProfile.class))).thenAnswer(returnParameterAsAnswer);
//        lenient().when(eventDao.save(any(GalleryEvent.class))).thenAnswer(returnParameterAsAnswer);
        }

    @Test
    public void testCreateEvent() {
        String name = "Soccer Game";
        String description = "the art of the soccer game";
        String imageUrl = "noImageUrl";

        Calendar c = Calendar.getInstance();
        c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
        Date eventDate = new Date(c.getTimeInMillis());
        LocalTime startTime = LocalTime.parse("09:00");
        c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
        LocalTime endTime = LocalTime.parse("10:30");
        GalleryEvent event = null;
        try {
            event = service.createEvent(name, description, imageUrl, eventDate, Time.valueOf(startTime), Time.valueOf(endTime));
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(event);
        checkResultEvent(event, description, imageUrl, name, eventDate, startTime, endTime);
    }

    private void checkResultEvent(GalleryEvent event, String description, String imageUrl, String name, Date eventDate, LocalTime startTime, LocalTime endTime) {
        assertNotNull(event);
        assertEquals(name, event.getEventName());
        assertEquals(description, event.getEventDescription());
        assertEquals(imageUrl, event.getEventImageUrl());
        assertEquals(eventDate.toString(), event.getEventDate().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        assertEquals(startTime.format(formatter).toString(), event.getStartTime().toString());
        assertEquals(endTime.format(formatter).toString(), event.getEndTime().toString());
    }
}

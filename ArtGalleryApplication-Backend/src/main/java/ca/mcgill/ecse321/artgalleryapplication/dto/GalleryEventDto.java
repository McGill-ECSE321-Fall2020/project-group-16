package ca.mcgill.ecse321.artgalleryapplication.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class GalleryEventDto {

    private String eventName;
    private String eventDescription;
    private String eventImageUrl;
    private Date eventDate;
    private Time startTime;
    private Time endTime;
    private List<UserProfileDto> participants;

    public GalleryEventDto() {
    }

    public GalleryEventDto(String name) {
        this(name, "Empty Description", "No image", Date.valueOf("1971-01-01"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"));
    }

    public GalleryEventDto(String name, String eventDescription, String eventImageUrl, Date eventDate, Time startTime, Time endTime) {
        this.eventName = name;
        this.eventDescription = eventDescription;
        this.eventImageUrl = eventImageUrl;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public List<UserProfileDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserProfileDto> participants) {
        this.participants = participants;
    }
}

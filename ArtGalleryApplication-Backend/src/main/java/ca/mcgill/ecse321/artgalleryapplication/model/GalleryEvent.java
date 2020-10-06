package ca.mcgill.ecse321.artgalleryapplication.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class GalleryEvent{
private String eventName;
   
   public void setEventName(String value) {
this.eventName = value;
    }
public String getEventName() {
return this.eventName;
    }
private String eventDescription;

public void setEventDescription(String value) {
this.eventDescription = value;
    }
public String getEventDescription() {
return this.eventDescription;
    }
private String eventImageUrl;

public void setEventImageUrl(String value) {
this.eventImageUrl = value;
    }
public String getEventImageUrl() {
return this.eventImageUrl;
    }
private int eventId;

public void setEventId(int value) {
this.eventId = value;
    }
@Id
public int getEventId() {
return this.eventId;
    }
private Date eventDate;

public void setEventDate(Date value) {
this.eventDate = value;
    }
public Date getEventDate() {
return this.eventDate;
    }
private Time startTime;

public void setStartTime(Time value) {
this.startTime = value;
    }
public Time getStartTime() {
return this.startTime;
    }
private Time endTime;

public void setEndTime(Time value) {
this.endTime = value;
    }
public Time getEndTime() {
return this.endTime;
    }
private Set<UserProfile> participants;

@ManyToMany
public Set<UserProfile> getParticipants() {
   return this.participants;
}

public void setParticipants(Set<UserProfile> participantss) {
   this.participants = participantss;
}

}

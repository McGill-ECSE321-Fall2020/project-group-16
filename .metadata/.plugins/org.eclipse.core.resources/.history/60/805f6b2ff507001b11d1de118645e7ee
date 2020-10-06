package ca.mcgill.ecse321.eventregistration.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Registration{
private Integer id;
   
   public void setId(Integer value) {
this.id = value;
    }
@Id
public Integer getId() {
return this.id;
    }
private Person participant;

@ManyToOne(optional=false)
public Person getParticipant() {
   return this.participant;
}

public void setParticipant(Person participant) {
   this.participant = participant;
}

private Event event;

@ManyToOne(optional=false)
public Event getEvent() {
   return this.event;
}

public void setEvent(Event event) {
   this.event = event;
}

}

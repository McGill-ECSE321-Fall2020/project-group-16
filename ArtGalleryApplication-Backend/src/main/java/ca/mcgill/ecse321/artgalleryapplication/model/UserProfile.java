package ca.mcgill.ecse321.artgalleryapplication.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserProfile{
private String username;
   
   public void setUsername(String value) {
this.username = value;
    }
@Id
public String getUsername() {
return this.username;
    }
private String password;

public void setPassword(String value) {
this.password = value;
    }
public String getPassword() {
return this.password;
    }
private String email;

public void setEmail(String value) {
this.email = value;
    }
public String getEmail() {
return this.email;
    }
private String lastName;

public void setLastName(String value) {
this.lastName = value;
    }
public String getLastName() {
return this.lastName;
    }
private String description;

public void setDescription(String value) {
this.description = value;
    }
public String getDescription() {
return this.description;
    }
private String profileImageUrl;

public void setProfileImageUrl(String value) {
this.profileImageUrl = value;
    }
public String getProfileImageUrl() {
return this.profileImageUrl;
    }
private Boolean isAdmin;

public void setIsAdmin(Boolean value) {
this.isAdmin = value;
    }
public Boolean getIsAdmin() {
return this.isAdmin;
    }
private String firstName;

public void setFirstName(String value) {
this.firstName = value;
    }
public String getFirstName() {
return this.firstName;
    }


private Address address;

@ManyToOne
public Address getAddress() {
   return this.address;
}

public void setAddress(Address address) {
   this.address = address;
}

private Set<GalleryEvent> galleryEvents = new HashSet<>();

    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
public Set<GalleryEvent> getGalleryEvent() {
   return this.galleryEvents;
}

public void setGalleryEvent(Set<GalleryEvent> galleryEvents) {
   this.galleryEvents = galleryEvents;
}

public void addEvent(GalleryEvent event) {
    galleryEvents.add(event);
    event.getParticipants().add(this);
}



private Set<Artwork> artworks = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_profile_artwork",
            joinColumns = {@JoinColumn(name = "artist_username")},
            inverseJoinColumns = {@JoinColumn(name = "artwork_artwork_id")}
    )
    public Set<Artwork> getArtwork() {
   return this.artworks;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artworks = artworks;
}
}

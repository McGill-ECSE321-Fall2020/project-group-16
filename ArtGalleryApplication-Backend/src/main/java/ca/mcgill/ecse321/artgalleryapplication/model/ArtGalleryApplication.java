package ca.mcgill.ecse321.artgalleryapplication.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class ArtGalleryApplication{
private int applicationId;
   
   public void setApplicationId(int value) {
this.applicationId = value;
    }
@Id
public int getApplicationId() {
return this.applicationId;
    }
private Set<Artwork> artwork;

@OneToMany(cascade={CascadeType.ALL})
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

private Set<UserProfile> userProfile;

@OneToMany(cascade={CascadeType.ALL})
public Set<UserProfile> getUserProfile() {
   return this.userProfile;
}

public void setUserProfile(Set<UserProfile> userProfiles) {
   this.userProfile = userProfiles;
}

private Set<GalleryEvent> galleryEvent;

@OneToMany(cascade={CascadeType.ALL})
public Set<GalleryEvent> getGalleryEvent() {
   return this.galleryEvent;
}

public void setGalleryEvent(Set<GalleryEvent> galleryEvents) {
   this.galleryEvent = galleryEvents;
}

private Set<Address> address;

@OneToMany(cascade={CascadeType.ALL})
public Set<Address> getAddress() {
   return this.address;
}

public void setAddress(Set<Address> addresss) {
   this.address = addresss;
}

}

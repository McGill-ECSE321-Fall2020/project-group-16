package ca.mcgill.ecse321.artgalleryapplication.model;

import org.apache.catalina.User;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Artwork{
private int artworkId;
   
   public void setArtworkId(int value) {
this.artworkId = value;
    }
@Id
@GeneratedValue(strategy= GenerationType.AUTO)
public int getArtworkId() {
return this.artworkId;
    }
private String title;

public void setTitle(String value) {
this.title = value;
    }
public String getTitle() {
return this.title;
    }
private String description;

public void setDescription(String value) {
this.description = value;
    }
public String getDescription() {
return this.description;
    }
private Date creationDate;

public void setCreationDate(Date value) {
this.creationDate = value;
    }
public Date getCreationDate() {
return this.creationDate;
    }
private String medium;

public void setMedium(String value) {
this.medium = value;
    }
public String getMedium() {
return this.medium;
    }
private String imageUrl;

public void setImageUrl(String value) {
this.imageUrl = value;
    }
public String getImageUrl() {
return this.imageUrl;
    }
private double price;

public void setPrice(double value) {
this.price = value;
    }
public double getPrice() {
return this.price;
    }

@Enumerated
private ArtworkStatus artworkStatus;

public void setArtworkStatus(ArtworkStatus value) {
this.artworkStatus = value;
    }
public ArtworkStatus getArtworkStatus() {
return this.artworkStatus;
    }
private String dimensions;

public void setDimensions(String value) {
this.dimensions = value;
    }
public String getDimensions() {
return this.dimensions;
    }
private String collection;

public void setCollection(String value) {
this.collection = value;
    }
public String getCollection() {
return this.collection;
    }

    private Set<UserProfile> artists = new HashSet<>();

    //@ManyToMany(mappedBy = "artwork", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToMany(mappedBy = "artwork", fetch = FetchType.EAGER)
public Set<UserProfile> getArtist() {
   return this.artists;
}

public void setArtist(Set<UserProfile> artists) {
   this.artists = artists;
}

public void addArtist(UserProfile artist) {
        artists.add(artist);
        artist.getArtwork().add(this);
}
}

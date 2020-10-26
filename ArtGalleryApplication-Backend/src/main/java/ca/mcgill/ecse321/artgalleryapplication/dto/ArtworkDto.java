package ca.mcgill.ecse321.artgalleryapplication.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.artgalleryapplication.model.ArtworkStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

public class ArtworkDto {
	private int artworkId;
	private String title;
	private String description;
	private Date creationDate;
	private String medium;
	private String imageUrl;
	private double price;
	private ArtworkStatus artworkStatus;
	private String dimensions;
	private String collection;
	private List<UserProfileDto> artists;

	public ArtworkDto() {
	}
	
	public ArtworkDto(int id, String title,  String description,  Date creationDate, 
					  String medium, String imageUrl, Double price, ArtworkStatus status,
					  String dimensions, String collection) {
		this.artworkId = id;
    	this.title = title;
    	this.description = description;
    	this.creationDate = creationDate;
    	this.medium = medium;
    	this.imageUrl = imageUrl;
    	this.price = price;
    	this.artworkStatus = status;
    	this.dimensions = dimensions;
    	this.collection = collection;		
	}
	
	public int getArtworkId() {
		return artworkId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public String getMedium() {
		return medium;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public double getPrice() {
		return price;
	}
	
	public ArtworkStatus getArtworkStatus() {
		return artworkStatus;
	}
	
	public String getDimensions() {
		return dimensions;
	}
	
	public String getCollection() {
		return collection;
	}
	
	public List<UserProfileDto> getArtists() {
		return toList(artists);
	}
	
	//@TODO idk if i need this/need to write more setAttribute methods
	public void setArtists() {
		this.artists = artists;
	}
	
	//helper methods
    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

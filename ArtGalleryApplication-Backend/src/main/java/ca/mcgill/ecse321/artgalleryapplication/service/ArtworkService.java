package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ArtworkService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtworkRepository artworkRepository;



    //CREATE METHODS

	/**
	 *
	 * @param title
	 * @param description
	 * @param creationDate
	 * @param medium
	 * @param imageUrl
	 * @param price
	 * @param status
	 * @param dimensions
	 * @param collection
	 * @return
	 */
    @Transactional
    public Artwork createArtwork(String title,  String description,  Date creationDate,
    								String medium, String imageUrl, Double price, ArtworkStatus status,
    								String dimensions, String collection) {

    	//title, price, and status are the only required attributes to create an artwork
		if(title == null || title.trim().length() == 0) throw new ApiRequestException("title is null or length 0. Please enter valid title.");
		if(description == null || description.trim().length() == 0) throw new ApiRequestException("description is null or length 0. Please enter valid description.");
		if(creationDate == null) throw new ApiRequestException("Creation Date is null. Please enter valid date.");
		if(medium == null || medium.trim().length() == 0) throw new ApiRequestException("medium is null or length 0. Please enter valid medium.");
		if(imageUrl == null || imageUrl.trim().length() == 0) throw new ApiRequestException("Image Url is null or length 0. Please enter valid url.");
		if(price == null) throw new ApiRequestException("Entered price is null. Please enter a valid price.");
		if(status == null) throw new ApiRequestException("Entered artwork status is null. Please enter a valid status.");	
		if(dimensions == null||dimensions.trim().length()==0) throw new ApiRequestException("dimensions are null or length 0. Please enter valid dimensions.");
		if(collection == null || collection.trim().length() == 0) throw new ApiRequestException("collection is null or length 0. Please enter valid collection.");
		
    	Artwork artwork = new Artwork();
    	artwork.setTitle(title);
    	artwork.setDescription(description);
    	artwork.setCreationDate(creationDate);
    	artwork.setMedium(medium);
    	artwork.setImageUrl(imageUrl);
    	artwork.setPrice(price);
    	artwork.setArtworkStatus(status);
    	artwork.setDimensions(dimensions);
    	artwork.setCollection(collection);

    	artworkRepository.save(artwork);
    	return artwork;
    }
 
    
    //UPDATE METHODS

	/**
	 *
	 * @param id
	 * @param newTitle
	 * @param newDescription
	 * @param newImageUrl
	 * @param newPrice
	 * @param newStatus
	 * @param newDimensions
	 * @param newCollection
	 * @return
	 */
	@Transactional
	public Artwork updateArtworkFields(int id, String newTitle, String newDescription, String newImageUrl, double newPrice, ArtworkStatus newStatus, String newDimensions, String newCollection) {

    	Artwork artwork = artworkRepository.findArtworkByArtworkId(id);
    	if(artwork == null) throw new ApiRequestException("No artwork with ID " + id + " in the system.");

		
    	if(newTitle != null && newTitle.trim().length() != 0) artwork.setTitle(newTitle);
    	if(newDescription != null && !newDescription.equals(artwork.getDescription())) artwork.setDescription(newDescription);
		if(newImageUrl != null && !newImageUrl.equals(artwork.getImageUrl())) artwork.setImageUrl(newImageUrl);
		if(newPrice != artwork.getPrice()) artwork.setPrice(newPrice);
		if(newStatus != artwork.getArtworkStatus()) artwork.setArtworkStatus(newStatus);
		if(newDimensions != artwork.getDimensions()) artwork.setDimensions(newDimensions);
		if(newCollection != artwork.getCollection()) artwork.setCollection(newCollection);
		
		artworkRepository.save(artwork);
		return artwork;
	}

	//GETTERS 

	/**
	 *
	 * @param id
	 * @return
	 */
    @Transactional
    public Artwork getArtwork(int id) {
    	Artwork artwork = artworkRepository.findArtworkByArtworkId(id);
    	if(artwork == null) throw new ApiRequestException("No artwork with ID " + id + " in the system.");
    	return artwork;
    }

	/**
	 *
	 * @return
	 */
	@Transactional
    public List<Artwork> getAllArtworks() {
    	List<Artwork> allArtworks = toList(artworkRepository.findAll());
    	return allArtworks;
    }

	/**
	 *
	 * @param n
	 * @return
	 */
	@Transactional
    //method that returns first n artworks instead of all of them
    public List<Artwork> getFirstNArtworks(int n) {
    	if(n <= 0) throw new ApiRequestException("n must be greater than 0");
    	
    	List<Artwork> firstNArtworks = new ArrayList<Artwork>();
    	
    	if(n <= toList(artworkRepository.findAll()).size()) {
    		n = toList(artworkRepository.findAll()).size();
    	}
    	
   		for(int i = 0; i < n; i++) {
   			firstNArtworks.add( (toList(artworkRepository.findAll())).get(i) );
   		}
   		return firstNArtworks;
    }

	/**
	 *
	 * @param username
	 * @return
	 */
	@Transactional
    public List<Artwork> getArtworkByArtist(String username) {
    	UserProfile artist = userRepository.findByUsername(username);
    	if (artist == null) {
    		throw new ApiRequestException("There was no user associated with this username.");
		}


    	return toList(artworkRepository.findAllArtworkByArtist(artist));
    }

	/**
	 *
	 * @param minPrice
	 * @param maxPrice
	 * @return
	 */
	@Transactional
    public List<Artwork> getArtworkByPrice(Double minPrice, Double maxPrice) {
    	List<Artwork> allArtwork = toList(artworkRepository.findAll());
    	List<Artwork> filteredArtwork = new ArrayList<>();
    	
    	//error handling
    	//users don't have to input a max or min price  	
    	if(minPrice == null || minPrice < 0) {
    		minPrice = 0.00;
    	}
    	
    	if(maxPrice == null || maxPrice < 0) {
    		maxPrice = 1000000.00;
    	}
    	
    	if(minPrice >= maxPrice) {
    		throw new ApiRequestException("Minimum price must be less than maximum price");
    	}
    	
    	//filtering results
    	for(Artwork a : allArtwork) {
    		if(a.getPrice() >= minPrice && a.getPrice() <= maxPrice) {
    			filteredArtwork.add(a);
    		}
    	}

    	return filteredArtwork;   	
    }

	/**
	 *
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
    @Transactional
    public List<Artwork> getArtworkByCreationDate(Date minDate, Date maxDate) {
    	List<Artwork> allArtwork = toList(artworkRepository.findAll());
    	List<Artwork> filteredArtwork = new ArrayList<>();
    	
    	//error handling
       	if (minDate != null && maxDate != null) {
       		if(minDate.compareTo(maxDate) > 0) { //if minDate is on or after maxDate
       			throw new ApiRequestException("Maximum date must be after minimum date");
       		}
       	}
    	
    	//users don't have to input a max or min price  	
    	if(minDate == null) {
    		String s = "1700-01-01";
    		minDate = Date.valueOf(s);
    	}
    	
    	if(maxDate == null) {
    		Date today = new Date(System.currentTimeMillis());
    		maxDate = today;
    	}
    	
    	//filtering results
    	for(Artwork a : allArtwork) {
    		if(a.getCreationDate().compareTo(minDate) >= 0 && a.getCreationDate().compareTo(maxDate) <= 0) {
    			filteredArtwork.add(a);
    		}
    	}

    	return filteredArtwork;   	
    }

	/**
	 *
	 * @param status
	 * @return
	 */
	@Transactional
    public List<Artwork> getArtworkByStatus(ArtworkStatus status) {
    	if(status == null) throw new ApiRequestException("Please enter a status");
    	return toList(artworkRepository.findAllArtworkByArtworkStatus(status));
    }

    //ADD ARTIST TO ARTWORK METHOD

	/**
	 *
	 * @param a
	 * @param artist
	 */
	@Transactional
	public void addArtistToArtwork(Artwork a, String artist) {
		if(a == null) throw new ApiRequestException("No artwork with this id in the system.");		
		Artwork artworkInSystem = artworkRepository.findArtworkByArtworkId(a.getArtworkId());

		UserProfile p = userRepository.findByUsername(artist);
		if(p == null) throw new ApiRequestException("No user in system associated to this username");

		Set<UserProfile> artists = new HashSet<>();
		artists.add(p);
		a.setArtist(artists);
		
		p.getArtwork().add(artworkInSystem);
		
		userRepository.save(p);
		artworkRepository.save(artworkInSystem);
	}

	//DELETE METHOD

	/**
	 *
	 * @param id
	 * @return
	 */
	@Transactional
    public boolean deleteArtwork(int id) {
	    Artwork a = artworkRepository.findArtworkByArtworkId(id);
        if (a == null) throw new ApiRequestException("No artwork with this id in the system.");

        for (UserProfile user: a.getArtist()){
        	user.setArtwork(new HashSet<>());
        	userRepository.save(user);
		}

		a.setArtist(new HashSet<>());
        artworkRepository.save(a);

	    artworkRepository.delete(a);
		return true;
	}
    
    //helper methods

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

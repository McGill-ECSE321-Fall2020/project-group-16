package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtworkService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private GalleryEventRepository galleryEventRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ArtGalleryApplicationRepository artGalleryApplicationRepository;



    /* @param id	 		artwork id
     * @param title	 		title of artwork
     * @param description	description of artwork
     * @param creationDate	date the artwork was completed
     * @param medium		i.e. oil painting, watercolour, charcoal, clay, etc.
     * @param imageUrl		will store image file as a url
     * @param price 		price of the art piece in CAD
     * @param status		either not for sale, for sale, or sold
     * @param dimensions	dimensions of the artwork
     * @param collection	optional attribute - if the artwork is part of a collection of pieces
     * @return the Artwork object with all attributes set
     * @TODO add error handling for inputs?
     */
    @Transactional
    public Artwork createArtwork(String title,  String description,  Date creationDate,
    							 String medium, String imageUrl, Double price, ArtworkStatus status,
    							 String dimensions, String collection) {

    	//title, price, and status are the only required attributes to create an artwork
		if(title == null || title.trim().length() == 0) throw new IllegalArgumentException("title is null or length 0. Please enter valid title.");
    	if(price == null) throw new IllegalArgumentException("Entered price is null. Please enter a valid price.");
		if(status == null) throw new IllegalArgumentException("Entered artwork status is null. Please enter a valid status.");	
		
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
 
	@Transactional
	public Artwork updateArtworkFields(int id, String newTitle, String newDescription, String newImageUrl, double newPrice, ArtworkStatus newStatus, String newDimensions, String newCollection) {

    	Artwork artwork = artworkRepository.findArtworkByArtworkId(id);
    	if(artwork == null) throw new IllegalArgumentException("No artwork with ID " + id + " in the system.");

		if(newTitle == null && newTitle.trim().length() != 0) artwork.setTitle(newTitle);
		if(newDescription != null && newDescription != artwork.getDescription()) artwork.setDescription(newDescription);
		if(newImageUrl != null && newImageUrl != artwork.getImageUrl()) artwork.setImageUrl(newImageUrl);
		if(newPrice != artwork.getPrice()) artwork.setPrice(newPrice);
		if(newStatus != artwork.getArtworkStatus()) artwork.setArtworkStatus(newStatus);
		if(newDimensions != artwork.getDimensions()) artwork.setDimensions(newDimensions);
		if(newCollection != artwork.getCollection()) artwork.setCollection(newCollection);
		
		artworkRepository.save(artwork);
		return artwork;
	}
     
    @Transactional
    public Artwork getArtwork(int id) {
    	Artwork artwork = artworkRepository.findArtworkByArtworkId(id);
    	if(artwork == null) throw new IllegalArgumentException("No artwork with ID " + id + " in the system.");
    	return artwork;
    }

    @Transactional
    public List<Artwork> getAllArtworks() {
    	List<Artwork> allArtworks = toList(artworkRepository.findAll());
    	return allArtworks;
    }

    @Transactional
    //method that returns first n artworks instead of all of them
    public List<Artwork> getFirstNArtworks(int n) {
    	if(n <= 0) throw new IllegalArgumentException("n must be greater than 0");
    	
    	List<Artwork> firstNArtworks = new ArrayList<Artwork>();
    	
    	if(n <= toList(artworkRepository.findAll()).size()) {
    		n = toList(artworkRepository.findAll()).size();
    	}
    	
   		for(int i = 0; i < n; i++) {
   			firstNArtworks.add( (toList(artworkRepository.findAll())).get(i) );
   		}
   		return firstNArtworks;
    }
    
    @Transactional
    public List<Artwork> getArtworkByArtist(String artist) {
    	if(artist == null || artist.length() == 0) throw new IllegalArgumentException("Please input an artist");
    	return toList(artworkRepository.findAllArtworkByArtist(artist));
    }
    
    @Transactional
    public List<Artwork> getArtworkByPrice(Double minPrice, Double maxPrice) {
    	List<Artwork> allArtwork = toList(artworkRepository.findAll());
    	List<Artwork> filteredArtwork = new ArrayList<Artwork>();
    	
    	//error handling
    	//users don't have to input a max or min price  	
    	if(minPrice == null || minPrice < 0) {
    		minPrice = 0.00;
    	}
    	
    	if(maxPrice == null || maxPrice < 0) {
    		maxPrice = 1000000.00;
    	}
    	
    	if(minPrice >= maxPrice) {
    		throw new IllegalArgumentException("Minimum price must be less than maximum price");
    	}
    	
    	//filtering results
    	for(Artwork a : allArtwork) {
    		if(a.getPrice() >= minPrice && a.getPrice() <= maxPrice) {
    			filteredArtwork.add(a);
    		}
    	}

    	return filteredArtwork;   	
    }

   
    @Transactional
    public List<Artwork> getArtworkByCreationDate(Date minDate, Date maxDate) {
    	List<Artwork> allArtwork = toList(artworkRepository.findAll());
    	List<Artwork> filteredArtwork = new ArrayList<Artwork>();
    	
    	//error handling
       	if (minDate != null && maxDate != null) {
       		if(minDate.compareTo(maxDate) > 0) { //if minDate is on or after maxDate
       			throw new IllegalArgumentException("Maximum date must be after minimum date");
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
    
    @Transactional
    public List<Artwork> getArtworkByStatus(ArtworkStatus status) {
    	if(status == null) throw new IllegalArgumentException("Please enter a status");
    	return toList(artworkRepository.findAllArtworkByArtworkStatus(status));
    }

	@Transactional
	public void addArtistToArtwork(Artwork a, UserProfile p) {
		Artwork artworkInSystem = artworkRepository.findArtworkByArtworkId(a.getArtworkId());
		if(artworkInSystem == null) throw new IllegalArgumentException("No artwork with this id in the system.");

		if(p == null) throw new IllegalArgumentException("null user entered as artist");
		UserProfile artist1 = userRepository.findByUsername(p.getUsername());
		if(artist1 == null) throw new IllegalArgumentException("No user in system associated to this username :" + p.getUsername());

		a.getArtist().add(p);
		p.getArtwork().add(artworkInSystem);
		userRepository.save(p);
		artworkRepository.save(artworkInSystem);
	}
	
	@Transactional
    public void deleteArtwork(int id) {
	    Artwork a = artworkRepository.findArtworkByArtworkId(id);
        if (a == null) throw new IllegalArgumentException("No artwork with this id in the system.");
	    artworkRepository.delete(a);
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

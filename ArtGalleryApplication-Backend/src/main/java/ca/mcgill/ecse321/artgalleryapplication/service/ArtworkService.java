package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
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
    public Artwork createArtwork(int id, String title,  String description,  Date creationDate, 
    							 String medium, String imageUrl, Double price, ArtworkStatus status,
    							 String dimensions, String collection) {
    	
    	Artwork artwork = new Artwork();
    	artwork.setArtworkId(id);
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
    public Artwork getArtwork(int id) {
    	Artwork artwork = artworkRepository.findArtworkByArtworkId(id);
    	return artwork;
    }

    @Transactional
    public List<Artwork> getAllArtworks() {
    	return toList(artworkRepository.findAll());
    }

    @Transactional
    //method that returns first n artworks instead of all of them
    public List<Artwork> getFirstNArtworks(int n) {
    	List<Artwork> firstNArtworks = new ArrayList<Artwork>();
    	for(int i = 0; i < n; i++) {
    		firstNArtworks.add( (toList(artworkRepository.findAll())).get(i) );
    	}
    	return firstNArtworks;
    }
    
    @Transactional
    public List<Artwork> getArtworkByArtist(UserProfile artist) {
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
    	//users don't have to input a max or min price  	
    	if(minDate == null) {
    		String s = "1900-01-01";
    		minDate = Date.valueOf(s);
    	}
    	
    	if(maxDate == null) {
    		Date today = new Date(System.currentTimeMillis());
    		maxDate = today;
    	}
    	
    	//@TODO might've gotten Date.compareTo backwards, need to double check
    	if(minDate.compareTo(maxDate) >= 0) { //if minDate is on or after maxDate
    		throw new IllegalArgumentException("Minimum price must be less than maximum price");
    	}
    	
    	//filtering results
    	for(Artwork a : allArtwork) {
    		if(a.getCreationDate().compareTo(minDate) <= 0 && a.getCreationDate().compareTo(maxDate) >= 0) {
    			filteredArtwork.add(a);
    		}
    	}

    	return filteredArtwork;   	
    }
    
    @Transactional
    public List<Artwork> getArtworkByStatus(ArtworkStatus status) {
    	return toList(artworkRepository.findAllArtworkByArtworkStatus(status));
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

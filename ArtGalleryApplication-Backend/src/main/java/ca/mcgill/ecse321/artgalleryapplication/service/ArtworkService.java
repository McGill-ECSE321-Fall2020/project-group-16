package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
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
import java.util.Set;
import java.util.stream.Collectors;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;

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



    //CREATE METHODS
    
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
	
    @Transactional
    public Artwork getArtwork(int id) {
    	Artwork artwork = artworkRepository.findArtworkByArtworkId(id);
    	if(artwork == null) throw new ApiRequestException("No artwork with ID " + id + " in the system.");
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
    
    @Transactional
    public List<Artwork> getArtworkByArtist(String username) {
    	UserProfile artist = userRepository.findByUsername(username);
    	if (artist == null) {
    		throw new ApiRequestException("There was no user associated with this username.");
		}


    	return toList(artworkRepository.findAllArtworkByArtist(artist));
    }
    
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
    
    @Transactional
    public List<Artwork> getArtworkByStatus(ArtworkStatus status) {
    	if(status == null) throw new ApiRequestException("Please enter a status");
    	return toList(artworkRepository.findAllArtworkByArtworkStatus(status));
    }

    //ADD ARTIST TO ARTWORK METHOD
    
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
	
	@Transactional
    public void deleteArtwork(int id) {
	    Artwork a = artworkRepository.findArtworkByArtworkId(id);
        if (a == null) throw new ApiRequestException("No artwork with this id in the system.");
	    artworkRepository.delete(a);
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

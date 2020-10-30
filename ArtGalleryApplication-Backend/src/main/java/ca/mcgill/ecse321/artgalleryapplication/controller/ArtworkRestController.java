package ca.mcgill.ecse321.artgalleryapplication.controller;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.artgalleryapplication.dto.ArtworkDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.OrderDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.UserProfileDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Artwork;
import ca.mcgill.ecse321.artgalleryapplication.model.ArtworkStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.Order;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import ca.mcgill.ecse321.artgalleryapplication.service.ArtworkService;
import ca.mcgill.ecse321.artgalleryapplication.service.UserProfileService;

@CrossOrigin(origins = "*")
@RestController
public class ArtworkRestController {
	
	@Autowired
	private ArtworkService artworkService;
	
	@Autowired
	private UserProfileService userService;
	
	
	@PostMapping(value = {"/artworks/{title}", "/artworks/{title}/"})
	public void createArtwork (
			@PathVariable("title") String title,
			@RequestParam String description,
			@RequestParam Date creationDate,
			@RequestParam String medium,
			@RequestParam String imageUrl,
			@RequestParam Double price,
			@RequestParam ArtworkStatus status,
			@RequestParam String dimensions,
			@RequestParam String collection )
			throws IllegalArgumentException{

		artworkService.createArtwork(title, description, creationDate,
				medium, imageUrl, price, status,
				dimensions, collection);
	}

	@GetMapping(value = {"/artworks/{id}", "/artworks/{id}/"})	
	public ArtworkDto getArtworkById(@PathVariable("id") int id) {
		Artwork a = artworkService.getArtwork(id);
		return ConvertToDto.convertToDto(a);
	} 
	
	
	@GetMapping(value = {"/artworks", "/artworks/"})
	public List<ArtworkDto> getAllArtworks() {
		List<ArtworkDto> artworks = new ArrayList<>();
	
		for(Artwork a : artworkService.getAllArtworks()) {
			artworks.add(ConvertToDto.convertToDto(a));
		}
		
		return artworks;
	}

	@PutMapping(value = {"/artworks/{id}/update", "/artworks/{id}/update/"})
	public void updateArtworkFields (
			@PathVariable("id") int id,
			@RequestParam String title,
			@RequestParam String newDescription,
			@RequestParam String newImageUrl,
			@RequestParam Double newPrice,
			@RequestParam ArtworkStatus newStatus,
			@RequestParam String newDimensions,
			@RequestParam String newCollection ) {

		artworkService.updateArtworkFields(id, title, newDescription, newImageUrl, newPrice, newStatus, newDimensions, newCollection);
	}
	

	@GetMapping(value = {"/artworks/byArtist", "/artworks/byArtist/"})
	public List<ArtworkDto> getAllArtworksByArtist(@RequestParam("artist") String artist) {
		List<ArtworkDto> artworks = new ArrayList<>();
		
		for(Artwork a : artworkService.getArtworkByArtist(artist)) {
			artworks.add(ConvertToDto.convertToDto(a));
		}
		
		return artworks;
	}
	
	
	@GetMapping(value = {"/artworks/byArtworkStatus", "/artworks/byArtworkStatus/"})
	public List<ArtworkDto> getAllArtworksByArtworkStatus(@RequestParam("status") ArtworkStatus status) {
		List<ArtworkDto> artworks = new ArrayList<>();
		
		for(Artwork a : artworkService.getArtworkByStatus(status)) {
			artworks.add(ConvertToDto.convertToDto(a));
		}
		
		return artworks;
	}

	@PutMapping(value = {"/artworks/{id}/add-artist/", "artworks/{id}/add-artist"})
	public void addArtworkToArtist(
			@PathVariable("id") int id,
			@RequestParam("username") String username)
			throws IllegalArgumentException {
		Artwork a = artworkService.getArtwork(id);
		UserProfile p = userService.getUserProfileByUsername(username);
		artworkService.addArtistToArtwork(a, p);
	}
}
 
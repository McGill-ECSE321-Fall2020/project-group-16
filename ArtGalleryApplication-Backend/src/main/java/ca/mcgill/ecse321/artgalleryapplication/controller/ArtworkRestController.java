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

public class ArtworkRestController {
	
	@Autowired
	private ArtworkService artworkService;
	
	@Autowired
	private UserProfileService userService;
	
	
	@PostMapping(value = {"/artworks", "/artworks/"}) 
	public ArtworkDto createArtwork (@RequestParam("artworkId") int artworkId,
									 @RequestParam("title") String title,
									 @RequestParam("description") String description,
									 @RequestParam("creationDate") Date creationDate,
									 @RequestParam("medium") String medium,
									 @RequestParam("imageUrl") String imageUrl,
									 @RequestParam("price") Double price,
									 @RequestParam("status") ArtworkStatus status,
									 @RequestParam("dimensions") String dimensions,
									 @RequestParam("collection") String collection )
									 throws IllegalArgumentException{
		
		Artwork artwork = artworkService.createArtwork(artworkId, title, description, creationDate, 
													   medium, imageUrl, price, status, 
													   dimensions, collection);
		
		return ConvertToDto.convertToDto(artwork);
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
	
}
 
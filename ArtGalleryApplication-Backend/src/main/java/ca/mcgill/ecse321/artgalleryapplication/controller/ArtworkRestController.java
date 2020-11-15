package ca.mcgill.ecse321.artgalleryapplication.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.artgalleryapplication.dto.ArtworkDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Artwork;
import ca.mcgill.ecse321.artgalleryapplication.model.ArtworkStatus;
import ca.mcgill.ecse321.artgalleryapplication.service.ArtworkService;

import static ca.mcgill.ecse321.artgalleryapplication.controller.ConvertToDto.convertToDto;

@CrossOrigin(origins = "*")
@RestController
public class ArtworkRestController {
	
	@Autowired
	private ArtworkService artworkService;

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
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = {"/artworks/{title}", "/artworks/{title}/"})
	public ArtworkDto createArtwork (
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

		return convertToDto(artworkService.createArtwork(title, description, creationDate,
				medium, imageUrl, price, status,
				dimensions, collection));
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = {"/artworks/{id}", "/artworks/{id}/"})	
	public ArtworkDto getArtworkById(@PathVariable("id") int id) {
		Artwork a = artworkService.getArtwork(id);
		return ConvertToDto.convertToDto(a);
	}

	/**
	 *
	 * @return
	 */
	@GetMapping(value = {"/artworks", "/artworks/"})
	public List<ArtworkDto> getAllArtworks() {
		List<ArtworkDto> artworks = new ArrayList<>();
	
		for(Artwork a : artworkService.getAllArtworks()) {
			artworks.add(ConvertToDto.convertToDto(a));
		}
		
		return artworks;
	}

	/**
	 *
	 * @param id
	 * @param title
	 * @param newDescription
	 * @param newImageUrl
	 * @param newPrice
	 * @param newStatus
	 * @param newDimensions
	 * @param newCollection
	 * @return
	 */
	@PutMapping(value = {"/artworks/{id}/update", "/artworks/{id}/update/"})
	public ArtworkDto updateArtworkFields (
			@PathVariable("id") int id,
			@RequestParam String title,
			@RequestParam String newDescription,
			@RequestParam String newImageUrl,
			@RequestParam Double newPrice,
			@RequestParam ArtworkStatus newStatus,
			@RequestParam String newDimensions,
			@RequestParam String newCollection ) {

		Artwork a = artworkService.updateArtworkFields(id, title, newDescription, newImageUrl, newPrice, newStatus, newDimensions, newCollection);
		return ConvertToDto.convertToDto(a);
	}


	/**
	 *
	 * @param artist
	 * @return
	 */
	@GetMapping(value = {"/artworks/byArtist", "/artworks/byArtist/"})
	public List<ArtworkDto> getAllArtworksByArtist(@RequestParam("artist") String artist) {
		List<ArtworkDto> artworks = new ArrayList<>();
		
		for(Artwork a : artworkService.getArtworkByArtist(artist)) {
			artworks.add(ConvertToDto.convertToDto(a));
		}
		
		return artworks;
	}

	/**
	 *
	 * @param status
	 * @return
	 */
	@GetMapping(value = {"/artworks/byArtworkStatus", "/artworks/byArtworkStatus/"})
	public List<ArtworkDto> getAllArtworksByArtworkStatus(@RequestParam("status") ArtworkStatus status) {
		List<ArtworkDto> artworks = new ArrayList<>();
		
		for(Artwork a : artworkService.getArtworkByStatus(status)) {
			artworks.add(ConvertToDto.convertToDto(a));
		}
		
		return artworks;
	}

	/**
	 *
	 * @param id
	 * @param username
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = {"/artworks/{id}/add-artist/", "artworks/{id}/add-artist"})
	public ArtworkDto addArtworkToArtist(
			@PathVariable("id") int id,
			@RequestParam("username") String username)
			throws IllegalArgumentException {
		Artwork a = artworkService.getArtwork(id);
		artworkService.addArtistToArtwork(a, username);
		return ConvertToDto.convertToDto(a);
	}

	@DeleteMapping(value = { "/artworks/{id}", "/artworks/{id}/" })
	public boolean deleteOrder(@PathVariable("id") int id) throws ApiRequestException {
		return artworkService.deleteArtwork(id);
	}
}
 
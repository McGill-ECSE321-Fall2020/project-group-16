package ca.mcgill.ecse321.artgalleryapplication.controller;

import java.sql.Date;

import ca.mcgill.ecse321.artgalleryapplication.dto.ArtworkDto;
import ca.mcgill.ecse321.artgalleryapplication.dto.UserProfileDto;
import ca.mcgill.ecse321.artgalleryapplication.model.Artwork;
import ca.mcgill.ecse321.artgalleryapplication.model.ArtworkStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

public class ArtworkRestController {
	
	public static ArtworkDto convertToDto(Artwork a) throws IllegalArgumentException {
		
		if(a == null) {
			throw new IllegalArgumentException("Artwork cannot be null");
		}
		
		return new ArtworkDto(a.getArtworkId(), a.getTitle(), a.getDescription(), a.getCreationDate(), 
							  a.getMedium(), a.getImageUrl(), a.getPrice(), a.getArtworkStatus(), 
							  a.getDimensions(), a.getCollection());}
}
 
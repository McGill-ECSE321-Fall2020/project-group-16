package ca.mcgill.ecse321.artgalleryapplication.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Artwork;
import ca.mcgill.ecse321.artgalleryapplication.model.ArtworkStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "artwork_data", path = "artwork_data")
public interface ArtworkRepository extends CrudRepository<Artwork, String>{

	Artwork findArtworkByArtworkId(int id);
	List<Artwork> findAllArtworkByArtist(UserProfile artist);
	List<Artwork> findAllArtworkByArtworkStatus(ArtworkStatus status);
	

}

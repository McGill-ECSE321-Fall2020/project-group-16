package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Artwork;

public interface ArtworkRepository extends CrudRepository<Artwork, String>{

	Artwork findArtworkByArtworkId(int addressId);

}

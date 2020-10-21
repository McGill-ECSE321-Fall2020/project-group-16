package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Artwork;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "artwork_data", path = "artwork_data")
public interface ArtworkRepository extends CrudRepository<Artwork, String>{

	Artwork findArtworkByArtworkId(int addressId);

}

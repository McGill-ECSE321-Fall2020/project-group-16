package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Address;

public interface GalleryEventRepository extends CrudRepository<GalleryEventRepository, String>{

	Address findGalleryEventByEventId(int eventId);

}

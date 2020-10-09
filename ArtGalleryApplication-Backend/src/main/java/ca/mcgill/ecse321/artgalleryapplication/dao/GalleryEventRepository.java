package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.GalleryEvent;

public interface GalleryEventRepository extends CrudRepository<GalleryEvent, String>{

	GalleryEvent findGalleryEventByEventId(int eventId);

}

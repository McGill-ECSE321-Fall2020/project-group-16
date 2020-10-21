package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.GalleryEvent;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "event_data", path = "event_data")
public interface GalleryEventRepository extends CrudRepository<GalleryEvent, String>{

	GalleryEvent findGalleryEventByEventId(int eventId);

}

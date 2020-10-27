package ca.mcgill.ecse321.artgalleryapplication.dao;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.GalleryEvent;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "event_data", path = "event_data")
public interface GalleryEventRepository extends CrudRepository<GalleryEvent, String>{

	GalleryEvent findGalleryEventByEventId(int eventId);

	void deleteGalleryEventByEventId(int eventId);

}

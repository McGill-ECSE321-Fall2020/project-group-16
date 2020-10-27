package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "user_data", path = "user_data")
public interface UserRepository extends CrudRepository<UserProfile, String>{

	UserProfile findByUsername(String username);
	UserProfile findByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	List<UserProfile> findByGalleryEvent(int eventId);

}
package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "user_data", path = "user_data")
public interface UserRepository extends CrudRepository<UserProfile, String>{

	UserProfile findUserProfileByUsername(String username);
	UserProfile findUserProfileByEmail(String email);
	boolean userProfileExistsByUsername(String username);
	boolean userProfileExistsByEmail(String email);

}
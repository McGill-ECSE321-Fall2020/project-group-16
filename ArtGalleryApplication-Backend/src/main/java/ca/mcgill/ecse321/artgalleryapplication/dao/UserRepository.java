package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;

public interface UserRepository extends CrudRepository<UserProfile, String>{

	UserProfile findUserProfileByUsername(String username);

}
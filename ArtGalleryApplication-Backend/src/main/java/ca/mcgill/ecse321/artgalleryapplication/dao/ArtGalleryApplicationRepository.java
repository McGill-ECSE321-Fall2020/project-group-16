package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.ArtGalleryApplication;

public interface ArtGalleryApplicationRepository extends CrudRepository<ArtGalleryApplication, String>{

	ArtGalleryApplication findApplicationByApplicationId(int applicationId);

}
package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.ArtGalleryApplication;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "artGalleryApp_data", path = "artGalleryApp_data")
public interface ArtGalleryApplicationRepository extends CrudRepository<ArtGalleryApplication, String>{

	ArtGalleryApplication findApplicationByApplicationId(int applicationId);

}
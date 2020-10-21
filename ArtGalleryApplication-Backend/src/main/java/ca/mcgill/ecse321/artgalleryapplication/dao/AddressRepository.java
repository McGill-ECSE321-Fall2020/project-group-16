package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "address_data", path = "address_data")
public interface AddressRepository extends CrudRepository<Address, String>{

	Address findAddressByAddressId(int addressId);

}

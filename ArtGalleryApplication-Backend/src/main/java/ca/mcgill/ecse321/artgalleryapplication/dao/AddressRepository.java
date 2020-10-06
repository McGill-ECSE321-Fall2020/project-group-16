package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Address;

public interface AddressRepository extends CrudRepository<Address, String>{

	Address findAddressByAddressId(int addressId);

}

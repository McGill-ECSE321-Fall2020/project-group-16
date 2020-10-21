package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "shipment_data", path = "shipment_data")
public interface ShipmentRepository extends CrudRepository<Shipment, String>{

	Shipment findShipmentByShipmentId(int shipmentId);

}

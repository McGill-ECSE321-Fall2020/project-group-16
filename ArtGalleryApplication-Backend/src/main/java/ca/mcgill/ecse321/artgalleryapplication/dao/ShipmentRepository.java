package ca.mcgill.ecse321.artgalleryapplication.dao;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Address;
import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "shipment_data", path = "shipment_data")
public interface ShipmentRepository extends CrudRepository<Shipment, String>{

	Shipment findShipmentByShipmentId(int shipmentId);
	List<Shipment> findShipmentByEstimatedArrivalDate(Date arrivalDate);
	List<Shipment> findShipmentByReturnAddress(Address returnAddress);
	List<Shipment> findShipmentByDestination(Address destination);
}

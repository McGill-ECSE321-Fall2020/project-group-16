package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Shipment;

public interface ShipmentRepository extends CrudRepository<Shipment, String>{

	Shipment findShipmentByShipmentId(int shipmentId);

}

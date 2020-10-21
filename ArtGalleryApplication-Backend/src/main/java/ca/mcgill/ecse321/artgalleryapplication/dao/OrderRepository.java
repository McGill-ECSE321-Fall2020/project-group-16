package ca.mcgill.ecse321.artgalleryapplication.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Order;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "order_data", path = "order_data")
public interface OrderRepository extends CrudRepository<Order, String>{

	Order findOrderByOrderId(int orderId);
	
}

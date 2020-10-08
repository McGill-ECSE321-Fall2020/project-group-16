package ca.mcgill.ecse321.artgalleryapplication.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Order;

public interface OrderRepository extends CrudRepository<Order, String>{

	Order findOrderByOrderId(int orderId);
	
}

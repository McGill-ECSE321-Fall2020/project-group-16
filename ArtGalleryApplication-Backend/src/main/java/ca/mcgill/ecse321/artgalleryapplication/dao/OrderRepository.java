package ca.mcgill.ecse321.artgalleryapplication.dao;

import ca.mcgill.ecse321.artgalleryapplication.model.OrderStatus;
import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import ca.mcgill.ecse321.artgalleryapplication.model.UserProfile;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Order;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "order_data", path = "order_data")
public interface OrderRepository extends CrudRepository<Order, String>{

	Order findOrderByOrderId(int orderId);

	List<Order> findByCustomer(UserProfile user);

	Order findByPayment(Payment payment);

	List<Order> findByOrderStatus(OrderStatus orderStatus);

	List<Order> findOrdersByCustomerAndOrderStatus(UserProfile username, OrderStatus orderStatus);



	void deleteByOrderId(int orderId);

}

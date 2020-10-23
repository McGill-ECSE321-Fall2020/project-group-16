package ca.mcgill.ecse321.artgalleryapplication.dao;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Payment;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "payment_data", path = "payment_data")
public interface PaymentRepository extends CrudRepository<Payment, String>{

	Payment findPaymentByPaymentId(int paymentId);
	List<Payment> findAllPaymentByCardNumber(String cardNumber);
	List<Payment> findAllPaymentByPaymentDate(Date pd);
	List<Payment> findAllPaymentByPaymentTime(Time pt);
	
}

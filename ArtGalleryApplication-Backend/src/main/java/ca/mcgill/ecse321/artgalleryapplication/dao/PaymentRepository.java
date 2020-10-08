package ca.mcgill.ecse321.artgalleryapplication.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgalleryapplication.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, String>{

	Payment findPaymentByPaymentId(int paymentId);

}

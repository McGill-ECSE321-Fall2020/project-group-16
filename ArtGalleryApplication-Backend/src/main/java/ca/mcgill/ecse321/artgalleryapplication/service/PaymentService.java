package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.dto.*;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private GalleryEventRepository galleryEventRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ArtGalleryApplicationRepository artGalleryApplicationRepository;



    //create the Transactional methods
    
    @Transactional
    public Payment createPayment(PaymentForm paymentForm, Date paymentDate, String cardNumber, Date expirationDate, int cvv, int paymentId, Time paymentTime){
    	//check if anything is null that shouldn't be
    	ArrayList<String> nulls = new ArrayList<String>();
    	if(paymentForm == null) {
    		nulls.add("paymentForm ");
    	}
    	if(paymentDate == null) {
    		nulls.add("paymentDate ");
    	}
    	if(cardNumber == null) {
    		nulls.add("cardNumber ");
    	}
    	if(expirationDate == null) {
    		nulls.add("expirationDate ");
    	}
    	if(paymentTime == null) {
    		nulls.add("paymentTime ");
    	}
    	if(cvv/100 > 9) {
    		throw new IllegalArgumentException("Your cvv must be 3 digits as most");
    	}
    	if(paymentRepository.findPaymentByPaymentId(paymentId)!=null) {
    		throw new IllegalArgumentException("A payment with this payment ID already exists");
    	}
    	if(nulls.size()>0) {
    		String errors = "";
    		for(String i:nulls) {
    			errors += i;
    		}
    		errors += "must not be null";
    		throw new IllegalArgumentException(errors);
    	}
    	if(expirationDate.before(paymentDate)) {
    		throw new IllegalArgumentException("card has expired");
    	}
    	if(cardNumber.trim().length() == 0) {
    		throw new IllegalArgumentException("cardNumber must not be empty");
    	}
    	
    	Payment payment = new Payment();
    	payment.setCardNumber(cardNumber);
    	payment.setPaymentForm(paymentForm);
    	payment.setPaymentDate(paymentDate);
    	payment.setExpirationDate(expirationDate);
    	payment.setCvv(cvv);
    	payment.setPaymentId(paymentId);
    	payment.setPaymentTime(paymentTime);
    	paymentRepository.save(payment);
    	
    	return payment;
    }
    
    @Transactional
    public List<Payment> getAllPayments(){
    	return toList(paymentRepository.findAll());
    }

    @Transactional
    public Payment getPayment(int paymentId) {
    	return paymentRepository.findPaymentByPaymentId(paymentId);
    }
    
    @Transactional
    public List<Payment> getAllPaymentsByCardNumber(String cardNumber){
    	if(cardNumber==null || cardNumber.trim() == null) {
    		throw new IllegalArgumentException("The card number must not be empty");
    	}
    	
    	List<Payment> payments = paymentRepository.findAllPaymentByCardNumber(cardNumber);
    	if (payments.isEmpty()) {
    		throw new IllegalArgumentException("No payments under this card number");
    	}
    	return payments;
    }
    
    @Transactional
    public List<Payment> getAllPaymentsByPaymentDate(Date pd){
    	if(pd==null) {
    		throw new IllegalArgumentException("The date must not be empty");
    	}
    	
    	List<Payment> payments = paymentRepository.findAllPaymentByPaymentDate(pd);
    	if (payments.isEmpty()) {
    		throw new IllegalArgumentException("No payments made on this date");
    	}
    	return payments;
    }
    
    @Transactional
    public List<Payment> getAllPaymentsByPaymentTime(Time pt){
    	if(pt==null) {
    		throw new IllegalArgumentException("The time must not be empty");
    	}
    	
    	List<Payment> payments = paymentRepository.findAllPaymentByPaymentTime(pt);
    	if (payments.isEmpty()) {
    		throw new IllegalArgumentException("No payments made at this time");
    	}
    	return payments;
    }
    
    @Transactional
    public Payment updatePayment(PaymentForm pf, Date pd, String cardNumber, Date expiration, int cvv, int paymentId, Time paymentTime){
    	if(paymentRepository.findPaymentByPaymentId(paymentId)==null) {
    		throw new IllegalArgumentException("must enter a payment id that is in the table");
    	}
    	
    	Payment p = paymentRepository.findPaymentByPaymentId(paymentId);
    	if(pf != null) {
    		p.setPaymentForm(pf);
    	} if (pd != null) {
    		p.setPaymentDate(pd);
    	} if (cardNumber != null && cardNumber.trim().length()>0) {
    		p.setCardNumber(cardNumber);
    	} if (expiration != null && expiration.after(pd)) {
    		p.setExpirationDate(expiration);
    	} if(paymentTime != null) {
    		p.setPaymentTime(paymentTime);
    	}				
    	if(cvv/100 <= 9) {
    		p.setCvv(cvv);
    	}
    	paymentRepository.save(p);
    	return p;
    }	
    //helper methods

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}

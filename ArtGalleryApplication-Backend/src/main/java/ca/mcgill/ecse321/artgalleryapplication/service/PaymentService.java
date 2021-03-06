package ca.mcgill.ecse321.artgalleryapplication.service;

import ca.mcgill.ecse321.artgalleryapplication.dao.*;
import ca.mcgill.ecse321.artgalleryapplication.exception.ApiRequestException;
import ca.mcgill.ecse321.artgalleryapplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Service
public class PaymentService {


    @Autowired
    private PaymentRepository paymentRepository;



    //create the Transactional methods

	/**
	 *
	 * @param paymentForm
	 * @param paymentDate
	 * @param cardNumber
	 * @param expirationDate
	 * @param cvv
	 * @param paymentTime
	 * @return
	 */
    @Transactional
    public Payment createPayment(PaymentForm paymentForm, Date paymentDate, String cardNumber, Date expirationDate, int cvv, Time paymentTime){
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
    		throw new ApiRequestException("Your cvv must be 3 digits as most");
    	}
    	if(nulls.size()>0) {
    		String errors = "";
    		for(String i:nulls) {
    			errors += i;
    		}
    		errors += "must not be null";
    		throw new ApiRequestException(errors);
    	}
    	if(expirationDate.before(paymentDate)) {
    		throw new ApiRequestException("card has expired");
    	}
    	if(cardNumber.trim().length() == 0) {
    		throw new ApiRequestException("cardNumber must not be empty");
    	}
    	
    	Payment payment = new Payment();
    	payment.setCardNumber(cardNumber);
    	payment.setPaymentForm(paymentForm);
    	payment.setPaymentDate(paymentDate);
    	payment.setExpirationDate(expirationDate);
    	payment.setCvv(cvv);
    	payment.setPaymentTime(paymentTime);
    	paymentRepository.save(payment);
    	
    	return payment;
    }

	/**
	 *
	 * @return
	 */
	@Transactional
    public List<Payment> getAllPayments(){
    	return toList(paymentRepository.findAll());
    }

	/**
	 *
	 * @param paymentId
	 * @return
	 */
	@Transactional
    public Payment getPayment(int paymentId) {
    	if(paymentRepository.findPaymentByPaymentId(paymentId) == null) {
    		throw new ApiRequestException("No payment with this ID");
    	}
    	return paymentRepository.findPaymentByPaymentId(paymentId);
    }

	/**
	 *
	 * @param cardNumber
	 * @return
	 */
	@Transactional
    public List<Payment> getAllPaymentsByCardNumber(String cardNumber){
    	if(cardNumber==null || cardNumber.trim().length() == 0) {
    		throw new ApiRequestException("The card number must not be empty");
    	}
    	
    	List<Payment> payments = paymentRepository.findAllPaymentByCardNumber(cardNumber);
    	if (payments.isEmpty()) {
    		throw new ApiRequestException("No payments under this card number");
    	}
    	return payments;
    }

	/**
	 *
	 * @param pd
	 * @return
	 */
	@Transactional
    public List<Payment> getAllPaymentsByPaymentDate(Date pd){
    	if(pd==null) {
    		throw new ApiRequestException("The date must not be empty");
    	}
    	
    	List<Payment> payments = paymentRepository.findAllPaymentByPaymentDate(pd);
    	if (payments.isEmpty()) {
    		throw new ApiRequestException("No payments made on this date");
    	}
    	return payments;
    }

	/**
	 *
	 * @param pt
	 * @return
	 */
	@Transactional
    public List<Payment> getAllPaymentsByPaymentTime(Time pt){
    	if(pt==null) {
    		throw new ApiRequestException("The time must not be empty");
    	}
    	
    	List<Payment> payments = paymentRepository.findAllPaymentByPaymentTime(pt);
    	if (payments.isEmpty()) {
    		throw new ApiRequestException("No payments made at this time");
    	}
    	return payments;
    }

	/**
	 *
	 * @param pf
	 * @param pd
	 * @param cardNumber
	 * @param expiration
	 * @param cvv
	 * @param paymentId
	 * @param paymentTime
	 * @return
	 */
    @Transactional
    public Payment updatePayment(PaymentForm pf, Date pd, String cardNumber, Date expiration, int cvv, int paymentId, Time paymentTime){
    	if(paymentRepository.findPaymentByPaymentId(paymentId)==null) {
    		throw new ApiRequestException("must enter a payment id that is in the table");
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
